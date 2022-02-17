package kr.carz.savecar.controller.CampingCar;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.dto.CampingCarMainTextDTO;
import kr.carz.savecar.dto.CampingCarReservationDTO;
import kr.carz.savecar.dto.ReviewDTO;
import kr.carz.savecar.service.*;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class CalendarController {
    private final CalendarDateService calendarDateService;
    private final CalendarTimeService calendarTimeService;
    private final DateCampingService dateCampingService;
    private final CampingCarPriceService campingCarPriceService;
    private final CampingcarReservationService campingcarReservationService;
    private final CampingCarPriceRateService campingCarPriceRateService;
    private final ImagesService imagesService;
    private final CampingCarMainTextService campingCarMainTextService;
    private final S3Service s3Service;
    private final ReviewService reviewService;

    @Autowired
    public CalendarController(CalendarDateService calendarDateService,
                              CalendarTimeService calendarTimeService, DateCampingService dateCampingService,
                              CampingCarPriceService campingCarPriceService, CampingcarReservationService campingcarReservationService,
                              CampingCarPriceRateService campingCarPriceRateService, ImagesService imagesService,
                              CampingCarMainTextService campingCarMainTextService, S3Service s3Service,
                              ReviewService reviewService) {
        this.calendarDateService = calendarDateService;
        this.calendarTimeService = calendarTimeService;
        this.dateCampingService = dateCampingService;
        this.campingCarPriceService = campingCarPriceService;
        this.campingcarReservationService = campingcarReservationService;
        this.campingCarPriceRateService = campingCarPriceRateService;
        this.imagesService = imagesService;
        this.campingCarMainTextService = campingCarMainTextService;
        this.s3Service = s3Service;
        this.reviewService = reviewService;
    }

    private static final SimpleDateFormat std_data_format = new SimpleDateFormat("yyyyMMdd");

    @Value("${coolsms.api_key}")
    private String api_key;

    @Value("${coolsms.api_secret}")
    private String api_secret;

    @Value("${phone.admin1}")
    private String admin1;

    @Value("${phone.admin2}")
    private String admin2;

    @Value("${phone.admin3}")
    private String admin3;

    private String AddDate(String strDate, int year, int month, int day) throws Exception {
        Calendar cal = Calendar.getInstance();
        Date dt = std_data_format.parse(strDate);

        cal.setTime(dt);
        cal.add(Calendar.YEAR, year);
        cal.add(Calendar.MONTH, month);
        cal.add(Calendar.DATE, day);

        return std_data_format.format(cal.getTime());
    }

    private int[] DateStringToInt(String date){
        return new int []{Integer.parseInt(date.substring(0,4)),Integer.parseInt(date.substring(4,6)), Integer.parseInt(date.substring(6,8))};
    }

    private int[] TodayDateInt() {
        Calendar cal = Calendar.getInstance();
        String df_date = std_data_format.format(cal.getTime());
        return DateStringToInt(df_date);
    }

    private String TodayDateString() {
        Calendar cal = Calendar.getInstance();
        return std_data_format.format(cal.getTime());
    }

    public CampingCarPrice get_campingcar_by_index(int idx) {
        if(idx == 0){
            return campingCarPriceService.findCampingCarPriceByCarName("europe");
        } else if(idx == 1){
            return campingCarPriceService.findCampingCarPriceByCarName("limousine");
        } else {
            return campingCarPriceService.findCampingCarPriceByCarName("travel");
        }
    }


    @GetMapping("/camping/main")
    public ModelAndView get_camping_calendar_main() {

        ModelAndView mav = new ModelAndView();

        List<CampingCarPrice> campingCarList = campingCarPriceService.findAllCampingCarPrice();
        List<Images> imagesMainList = new ArrayList<>();

        for(CampingCarPrice campingCar : campingCarList){
            List<Images> mainImage = imagesService.findByCarNameAndIsMain(campingCar, "1");

            if(mainImage.size() == 0){
                imagesMainList.add(new Images((long) -1, campingCar, -1, "", "0", "1"));
            } else {
                imagesMainList.add(mainImage.get(0));
            }
        }

        mav.addObject("imagesMainList", imagesMainList);
        mav.addObject("campingCarList", campingCarList);

        mav.setViewName("rent_camping/main");

        return mav;
    }


    @GetMapping("/camping/calendar/{year}/{month}")
    public String camping_calendar_different_month(ModelMap model, @PathVariable("year") int year, @PathVariable("month") int month) throws Exception {

        int thisYear = TodayDateInt()[0];
        int thisMonth = TodayDateInt()[1];
        int thisDay = TodayDateInt()[2];

        // 저번달 날짜
        Calendar cal = Calendar.getInstance();
        cal.set(year, month-1, 1);
        int thisDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        List<CalendarDate> calendarDateList;
        List<List<DateCamping>> dateCampingList = new ArrayList();
        List<List<String>> calendarTimeList = new ArrayList<>();

        int[] prevMonthDate;
        int[] nextMonthDate;

        if(thisYear == year && thisMonth == month){

            calendarDateList = calendarDateService.findByYearAndMonth(Integer.toString(thisYear), Integer.toString(thisMonth));

            Long firstDateId = calendarDateList.get(0).getDateId();
            for (int i = 1; i < thisDayOfWeek; i++) {
                calendarDateList.add(0, calendarDateService.findCalendarDateByDateId(firstDateId - i));
            }

            prevMonthDate = DateStringToInt(AddDate(TodayDateString(), 0, -1, 0));
            nextMonthDate = DateStringToInt(AddDate(TodayDateString(), 0, 1, 0));
        } else {
            calendarDateList = calendarDateService.findByYearAndMonth(Long.toString(year), Long.toString(month));

            Long firstDateId = calendarDateList.get(0).getDateId();
            for (int i = 1; i < thisDayOfWeek; i++) {
                calendarDateList.add(0, calendarDateService.findCalendarDateByDateId(firstDateId - i));
            }

            String date = year + String.format("%02d", month) + "01";

            prevMonthDate = DateStringToInt(AddDate(date, 0, -1, 0));
            nextMonthDate = DateStringToInt(AddDate(date, 0, 1, 0));
        }

        for (CalendarDate calendarDate : calendarDateList) {
            List<DateCamping> dateCampingListByDateId = dateCampingService.findByDateId(calendarDate);
            dateCampingList.add(dateCampingListByDateId);

            List<String> calendarTimeListString = new ArrayList<>();
            for(int k=0; k<dateCampingListByDateId.size(); k++){

                if(dateCampingListByDateId.get(k).getReserved().equals("1")){
                    List<CalendarTime> calendarTimeListByDate = calendarTimeService.findCalendarTimeByDateIdAndCarName(calendarDate, get_campingcar_by_index(k));
                    String reserve_status = calendarTimeListByDate.get(0).getReserveComplete();
                    int m;
                    for(m=1; m<calendarTimeListByDate.size(); m++){
                        if(!calendarTimeListByDate.get(m).getReserveComplete().equals(reserve_status)){
                            break;
                        }
                    }
                    if(reserve_status.equals("1")){
                        calendarTimeListString.add(calendarTimeListByDate.get(m).getReserveTime() + "~");
                    } else {
                        calendarTimeListString.add("~" + calendarTimeListByDate.get(m-1).getReserveTime());
                    }
                } else {
                    calendarTimeListString.add("0");
                }
            }
            calendarTimeList.add(calendarTimeListString);
        }


        model.addAttribute("calendarDateList", calendarDateList);
        model.addAttribute("dateCampingList", dateCampingList);
        model.addAttribute("calendarTimeList", calendarTimeList);

        model.addAttribute("thisYear", thisYear);
        model.addAttribute("thisMonth", thisMonth);
        model.addAttribute("thisDay", thisDay);

        model.addAttribute("clickedYear", year);
        model.addAttribute("clickedMonth", month);

        model.addAttribute("prevMonth", prevMonthDate[1]);
        model.addAttribute("nextMonth", nextMonthDate[1]);


        return "rent_camping/calendar";
    }


    @GetMapping("/camping/calendar/{carType}_reserve/{clickedYear}/{clickedMonth}")
    public String camping_calendar_detail_different_month(ModelMap model, @PathVariable("carType") String carType, @PathVariable("clickedYear") int clickedYear, @PathVariable("clickedMonth") int clickedMonth) throws Exception {

        Calendar cal = Calendar.getInstance();
        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(carType);
        List<CalendarDate> calendarDateList = calendarDateService.findByYearAndMonth(Long.toString(clickedYear), Long.toString(clickedMonth));

        // 오늘 날짜
        int thisYear = TodayDateInt()[0];
        int thisMonth = TodayDateInt()[1];
        int thisDay = TodayDateInt()[2];

        // 클릭된 날짜
        String clickedDate = clickedYear + String.format("%02d", clickedMonth) + "01";
        int[] clickedPrevMonthDate = DateStringToInt(AddDate(clickedDate, 0, -1, 0));
        int[] clickedNextMonthDate = DateStringToInt(AddDate(clickedDate, 0, 1, 0));

        // 클릭된 저번달 날짜
        cal.set(clickedPrevMonthDate[0], clickedPrevMonthDate[1], 1);
        int thisDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        Long firstDateId = calendarDateList.get(0).getDateId();
        for (int i = 1; i < thisDayOfWeek; i++) {
            calendarDateList.add(0, calendarDateService.findCalendarDateByDateId(firstDateId - i));
        }

        // 날짜별 캠핑카
        List<List<DateCamping>> dateCampingList = new ArrayList();
        List<List<CalendarTime>> calendarTimeList = new ArrayList<>();
        for (CalendarDate calendarDate : calendarDateList) {
            dateCampingList.add(dateCampingService.findByDateId(calendarDate));
            calendarTimeList.add(calendarTimeService.findCalendarTimeByDateIdAndCarName(calendarDate,campingCarPrice));
        }

        CampingCarPrice explanation = campingCarPriceService.findCampingCarPriceByCarName(carType);


        List<Images> imagesListByCarNameMain = imagesService.findByCarNameAndIsMain(campingCarPrice, "1");
        List<Images> imagesList = imagesService.findByCarNameAndIsMain(campingCarPrice, "0");

        Collections.sort(imagesList);

        if(imagesListByCarNameMain.size() > 0) {
            imagesList.add(0, imagesListByCarNameMain.get(0));
        }

        List<CampingCarMainText> campingCarMainTextList = campingCarMainTextService.findImageByCarName(campingCarPrice);
        Collections.sort(campingCarMainTextList);

        List<Review> reviewList = reviewService.findImageByCarName(campingCarPrice);

        model.put("reviewList", reviewList);
        model.put("campingCarMainTextList", campingCarMainTextList);
        model.put("imagesList", imagesList);
        model.put("explanation", explanation);

        model.addAttribute("calendarDateList", calendarDateList);
        model.addAttribute("calendarTimeList", calendarTimeList);
        model.addAttribute("dateCampingList", dateCampingList);
        model.put("campingCarPrice", campingCarPrice);

        model.addAttribute("clickedPrevYear", clickedPrevMonthDate[0]);
        model.addAttribute("clickedPrevMonth", clickedPrevMonthDate[1]);
        model.addAttribute("clickedNextYear", clickedNextMonthDate[0]);
        model.addAttribute("clickedNextMonth", clickedNextMonthDate[1]);
        model.addAttribute("clickedYear", clickedYear);
        model.addAttribute("clickedMonth", clickedMonth);

        model.addAttribute("thisYear", thisYear);
        model.addAttribute("thisMonth", thisMonth);
        model.addAttribute("thisDay", thisDay);

        return "rent_camping/" + carType;
    }


    @GetMapping("/camping/calendar/{carType}_reserve/time_list/{dateId}")
    @ResponseBody
    public void get_campingcar_time_list(HttpServletResponse res, @PathVariable String carType, @PathVariable Long dateId) throws Exception {

        CalendarDate calendarDate = calendarDateService.findCalendarDateByDateId(dateId);
        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(carType);

        List<CalendarTime> calendarTimeList = calendarTimeService.findCalendarTimeByDateIdAndCarName(calendarDate,campingCarPrice);

        JSONArray jsonArray = new JSONArray();
        for (CalendarTime c : calendarTimeList) {
            jsonArray.put(c.getReserveComplete());
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray);
        pw.flush();
        pw.close();
    }


    @GetMapping("/camping/calendar/{carType}_reserve/reservation/{rent_date}/{rent_time}/{return_date}/{return_time}/{day}/{total}/{extraTime}")
    public String get_reservation_information(ModelMap model, @PathVariable String carType,@PathVariable String rent_date, @PathVariable String rent_time, @PathVariable String return_date, @PathVariable String return_time, @PathVariable String day, @PathVariable String total, @PathVariable String extraTime) {

        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName("limousine");
        model.put("campingCarPrice", campingCarPrice);

        model.put("rent_date", rent_date);
        model.put("rent_time", rent_time);
        model.put("return_date", return_date);
        model.put("return_time", return_time);
        model.put("day", day);
        model.put("total", total);
        model.put("carType", carType);
        model.put("extraTime", extraTime);

        return "rent_camping/reservation";
    }


    // 캠핑카 가격 구하는 api
    @RequestMapping(value = "/camping/calendar/{carType}/getprice/{season}", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_camping_car_price(HttpServletResponse res, @PathVariable String carType, @PathVariable String season) throws IOException {

        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(carType);
        CampingCarPriceRate campingCarPriceRate = campingCarPriceRateService.findByCarNameAndSeason(campingCarPrice, season);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("carName",campingCarPriceRate.getCarName().getCarName());
        jsonObject.put("deposit",campingCarPriceRate.getDeposit());
        jsonObject.put("season",campingCarPriceRate.getSeason());
        jsonObject.put("threeHours",campingCarPriceRate.getThreeHours());
        jsonObject.put("onedays",campingCarPriceRate.getOnedays());
        jsonObject.put("twodays",campingCarPriceRate.getTwodays());
        jsonObject.put("threedays",campingCarPriceRate.getThreedays());
        jsonObject.put("fourdays",campingCarPriceRate.getFourdays());
        jsonObject.put("fivedays",campingCarPriceRate.getFivedays());
        jsonObject.put("sixdays",campingCarPriceRate.getSixdays());
        jsonObject.put("sevendays",campingCarPriceRate.getSevendays());
        jsonObject.put("eightdays",campingCarPriceRate.getEightdays());
        jsonObject.put("ninedays",campingCarPriceRate.getNinedays());
        jsonObject.put("tendays",campingCarPriceRate.getTendays());
        jsonObject.put("elevendays",campingCarPriceRate.getElevendays());
        jsonObject.put("twelvedays",campingCarPriceRate.getTwelvedays());
        jsonObject.put("thirteendays",campingCarPriceRate.getThirteendays());
        jsonObject.put("fourteendays",campingCarPriceRate.getFourteendays());
        jsonObject.put("fifteendays",campingCarPriceRate.getFifteendays());
        jsonObject.put("sixteendays",campingCarPriceRate.getSixteendays());
        jsonObject.put("seventeendays",campingCarPriceRate.getSeventeendays());
        jsonObject.put("eighteendays",campingCarPriceRate.getEighteendays());
        jsonObject.put("ninetinedays",campingCarPriceRate.getNinetinedays());
        jsonObject.put("twentydays",campingCarPriceRate.getTwentydays());
        jsonObject.put("twentyonedays",campingCarPriceRate.getTwentyonedays());
        jsonObject.put("twentytwodays",campingCarPriceRate.getTwentytwodays());
        jsonObject.put("twentythreedays",campingCarPriceRate.getTwentythreedays());
        jsonObject.put("twentyfourdays",campingCarPriceRate.getTwentyfourdays());
        jsonObject.put("twentyfivedays",campingCarPriceRate.getTwentyfivedays());
        jsonObject.put("twentysixdays",campingCarPriceRate.getTwentysixdays());
        jsonObject.put("twentysevendays",campingCarPriceRate.getTwentysevendays());
        jsonObject.put("twentyeightdays",campingCarPriceRate.getTwentyeightdays());
        jsonObject.put("twentyninedays",campingCarPriceRate.getTwentyninedays());
        jsonObject.put("thirtydays",campingCarPriceRate.getThirtydays());

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    // 캠핑카 예약 대기 신청
//    @RequestMapping(value = "/camping/calendar/reserve", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @PostMapping("/camping/calendar/reserve")
    @ResponseBody
    public void camping_calendar_reservation(HttpServletResponse res, @RequestBody CampingCarReservationDTO dto) throws IOException{

        // 문자전송
        Message coolsms = new Message(api_key, api_secret);
        HashMap<String, String> params = new HashMap<>();
        HashMap<String, String> params2 = new HashMap<>();


        /* 세이브카에 예약확인 문자 전송 */
        params.put("to", admin1+", "+admin2+", "+admin3); // 01033453328 추가
        params.put("from", admin3);
        params.put("type", "LMS");

        /* 고객에게 예약확인 문자 전송 */
        params2.put("to", dto.getPhone());
        params2.put("from", admin3);  // 16613331 테스트하기
        params2.put("type", "LMS");

        String extraTimeDescription = "사용X";
        if(dto.getExtraTime() == 1){
            extraTimeDescription = "사용O";
        }

        params.put("text", "[캠핑카 캘린더 예약]\n"
                + "성함: " + dto.getName() + "\n"
                + "전화번호: " + dto.getPhone() + "\n"
                + "차량명: " + dto.getCarType() + "\n"
                + "입금자명: " + dto.getDepositor() + "\n"
                + "대여날짜: " + dto.getRentDate() + "\n"
                + "대여시간: " + dto.getRentTime() + "\n"
                + "반납날짜: " + dto.getReturnDate() + "\n"
                + "반납시간: " + dto.getReturnTime() + "\n"
                + "추가3시간권: " + extraTimeDescription + "\n"
                + "이용날짜: " + dto.getDay() + "\n"
                + "총금액: " + dto.getTotal() + "\n"
                + "선결제금액: " + dto.getTotalHalf() + "\n"
                + "요청사항: " + dto.getDetail() + "\n\n");

        params2.put("text", "[캠핑카 예약 대기 신청이 완료되었습니다.]" + "\n"
                + "성함: " + dto.getName() + "\n"
                + "전화번호: " + dto.getPhone() + "\n"
                + "차량명: " + dto.getCarType() + "\n"
                + "대여날짜: " + dto.getRentDate() + "\n"
                + "대여시간: " + dto.getRentTime() + "\n"
                + "반납날짜: " + dto.getReturnDate() + "\n"
                + "반납시간: " + dto.getReturnTime() + "\n"
                + "추가3시간권: " + extraTimeDescription + "\n"
                + "입금자명: " + dto.getDepositor() + "\n"
                + "이용날짜: " + dto.getDay() + "\n"
                + "총금액: " + dto.getTotal() + "\n"
                + "선결제금액: " + dto.getTotalHalf() + "\n"
                + "요청사항: " + dto.getDetail() + "\n\n");


        params.put("app_version", "test app 1.2");
        params2.put("app_version", "test app 1.2");


        /* 세이브카에게 문자 전송 */

        try {
            org.json.simple.JSONObject obj = coolsms.send(params);
            System.out.println(obj.toString()); //전송 결과 출력
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }

        /* 고객에게 예약확인 문자 전송 */

        try {
            org.json.simple.JSONObject obj2 = coolsms.send(params2);
            System.out.println(obj2.toString()); //전송 결과 출력
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }

        campingcarReservationService.save_campingcar_reservation(dto);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }



    // 예약 가능한 날짜, 추가시간 가져오기
    @GetMapping("/camping/calendar/possible/{carType}/{dateId}/{reserveTime}/{days}")
    @ResponseBody
    public void get_impossible_date(HttpServletResponse res, @PathVariable String carType, @PathVariable Long dateId, @PathVariable String reserveTime, @PathVariable Long days) throws Exception {

        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(carType);
        CalendarDate calendarStartDate = calendarDateService.findCalendarDateByDateId(dateId);

        String startDate = calendarStartDate.getYear() + String.format("%02d", Integer.parseInt(calendarStartDate.getMonth()) ) + String.format("%02d", Integer.parseInt(calendarStartDate.getDay()) );

        int [] nextMonthDate = DateStringToInt(AddDate(startDate, 0, 1, 0));

        CalendarDate calendarNextMonthDate = calendarDateService.findCalendarDateByMonthAndDayAndYear(Integer.toString(nextMonthDate[1]), Integer.toString(nextMonthDate[2]), Integer.toString(nextMonthDate[0]));

        int possible_days = 0;
        int extra_time = 0;
        int extra_time_flg = 0;

        for(Long i=dateId+1; i<=calendarNextMonthDate.getDateId(); i++){
            CalendarDate calendarDate = calendarDateService.findCalendarDateByDateId(i);
            CalendarTime calendarTime = calendarTimeService.findCalendarTimeByDateIdAndCarNameAndReserveTime(calendarDate, campingCarPrice, reserveTime);
            if(calendarTime.getReserveComplete().equals("0")){
                possible_days += 1;
            } else {
                break;
            }
        }

        Long lastDateId = dateId + days;
        CalendarDate calendarLastDate = calendarDateService.findCalendarDateByDateId(lastDateId);
        CalendarTime calendarLastTime = calendarTimeService.findCalendarTimeByDateIdAndCarNameAndReserveTime(calendarLastDate, campingCarPrice, reserveTime);

        if(reserveTime.compareTo("16시") < 0){
            for(long j=calendarLastTime.getTimeId()+1; j<=calendarLastTime.getTimeId()+3; j++){
                CalendarTime calendarExtraTime = calendarTimeService.findCalendarTimeByTimeId(j);
                if(calendarExtraTime.getReserveComplete().equals("0")){
                    extra_time += 1;
                } else {
                    break;
                }
            }
            if(extra_time == 3){
                extra_time_flg = 1;
            }
        }


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("possible_days", possible_days);
        jsonObject.put("extra_time_flg", extra_time_flg);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }



    @PostMapping(value="/camping/calendar/review", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public void postCampingCarReview(ReviewDTO dto) throws Exception  {

        List<MultipartFile> multipartFileList = dto.getImageList();
        String [] imageUrlList = new String[multipartFileList.size()];

        for(int i=0; i<multipartFileList.size(); i++){
            String imgPath = s3Service.upload(multipartFileList.get(i));
            imageUrlList[i] = imgPath;
        }

        List<MultipartFile> videoList = dto.getVideo();
        String videoURL = "";
        for(int i=0; i<videoList.size(); i++){
            if(i > 0){
                throw new Exception("You can only upload one video.");
            }
            videoURL = s3Service.upload(videoList.get(i));
        }

        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(dto.getCarName());
        reviewService.saveDTO(dto, campingCarPrice, imageUrlList, videoURL);
    }



}
