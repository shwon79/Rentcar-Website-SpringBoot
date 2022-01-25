package kr.carz.savecar.controller.CampingCar;

import kr.carz.savecar.controller.Utils.HttpConnection;
import kr.carz.savecar.domain.*;
import kr.carz.savecar.dto.CampingCarReservationDTO;
import kr.carz.savecar.service.*;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class CalendarController {
    CalendarDateService calendarDateService;
    CalendarTimeService calendarTimeService;
    DateCampingService dateCampingService;
    CampingCarPriceService campingCarPriceService;
    CampingcarReservationService campingcarReservationService;
    ExplanationService explanationService;

    @Autowired
    public CalendarController(CalendarDateService calendarDateService,
                              CalendarTimeService calendarTimeService, DateCampingService dateCampingService,
                              CampingCarPriceService campingCarPriceService, CampingcarReservationService campingcarReservationService,
                              ExplanationService explanationService) {
        this.calendarDateService = calendarDateService;
        this.calendarTimeService = calendarTimeService;
        this.dateCampingService = dateCampingService;
        this.campingCarPriceService = campingCarPriceService;
        this.campingcarReservationService = campingcarReservationService;
        this.explanationService = explanationService;
    }

    private static final SimpleDateFormat std_data_format = new SimpleDateFormat("yyyyMMdd");

    @Value("${coolsms.api_key}")
    private String api_key;

    @Value("${coolsms.api_secret}")
    private String api_secret;

    @Value("${moren_url}")
    private String moren_url_except_date;

    @Value("${moren.expected_day}")
    private String expected_day;


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



    @GetMapping("/camping/{carType}")
    public String get_camping_carType(ModelMap model, @PathVariable("carType") String carType) {

        Optional<Explanation> explanation = explanationService.findById((long) 0);
        explanation.ifPresent(value -> model.put("explanation", value));

        return "rent_camping/" + carType + "_info";
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


        String moren_url = moren_url_except_date + DateTime.today_date_only() + "&END=" + DateTime.today_date_only() + "&EXPECTED_DAY=" + expected_day;

        HttpConnection http = new HttpConnection();
        JSONObject responseJson = http.sendGetRequest(moren_url);
        JSONArray list_json_array = (JSONArray) responseJson.get("list");
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        List<OrderStartEnd> primeClubReservedList = new ArrayList<>();


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

//            String calendar_date_string = calendarDate.getYear() + "-" + calendarDate.getMonth() + "-" + calendarDate.getDay() + " 00:00:00";
//            LocalDateTime calendar_date_date_time = LocalDateTime.parse(calendar_date_string, formatter);
//
//            // 차량명도 신경써야함
//            for(OrderStartEnd orderStartEnd : primeClubReservedList){
//                if(calendar_date_date_time.isAfter(orderStartEnd.startDate) && )
//            }

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


//        for(int i=0; i<list_json_array.length(); i++) {
//            JSONObject selectedObject = (JSONObject)list_json_array.get(i);
//            String carGubun = ((String)selectedObject.get("carGubun"));
//            if(carGubun.equals("캠핑카")){
//                String order_start = ((String)selectedObject.get("order_start"));
//                String order_end = ((String)selectedObject.get("order_end"));
//
////                LocalDateTime order_start_date_time = LocalDateTime.parse(order_start, formatter);
////                LocalDateTime order_end_date_time = LocalDateTime.parse(order_end, formatter);
////                primeClubReservedList.add(new OrderStartEnd(order_start_date_time, order_end_date_time));
//
//            }
//        }



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

        Optional<Explanation> explanation = explanationService.findById((long) 0);
        explanation.ifPresent(value -> model.put("explanation", value));

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
    public void get_campingcar_price(HttpServletResponse res, @PathVariable String carType, @PathVariable String season) throws IOException {

        if(season.equals("0")){
            CampingCarPrice campingCarPrice;

            campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(carType);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("deposit",campingCarPrice.getDeposit());
            jsonObject.put("yearmodel",campingCarPrice.getYearmodel());
            jsonObject.put("carName",campingCarPrice.getCarName());
            jsonObject.put("onedays",campingCarPrice.getOnedays());
            jsonObject.put("twodays",campingCarPrice.getTwodays());
            jsonObject.put("threedays",campingCarPrice.getThreedays());
            jsonObject.put("fourdays",campingCarPrice.getFourdays());
            jsonObject.put("fivedays",campingCarPrice.getFivedays());
            jsonObject.put("sixdays",campingCarPrice.getSixdays());
            jsonObject.put("sevendays",campingCarPrice.getSevendays());
            jsonObject.put("eightdays",campingCarPrice.getEightdays());
            jsonObject.put("ninedays",campingCarPrice.getNinedays());
            jsonObject.put("tendays",campingCarPrice.getTendays());
            jsonObject.put("elevendays",campingCarPrice.getElevendays());
            jsonObject.put("twelvedays",campingCarPrice.getTwelvedays());
            jsonObject.put("thirteendays",campingCarPrice.getThirteendays());
            jsonObject.put("fourteendays",campingCarPrice.getFourteendays());
            jsonObject.put("fifteendays",campingCarPrice.getFifteendays());
            jsonObject.put("sixteendays",campingCarPrice.getSixteendays());
            jsonObject.put("seventeendays",campingCarPrice.getSeventeendays());
            jsonObject.put("eighteendays",campingCarPrice.getEighteendays());
            jsonObject.put("ninetinedays",campingCarPrice.getNinetinedays());
            jsonObject.put("twentydays",campingCarPrice.getTwentydays());
            jsonObject.put("twentyonedays",campingCarPrice.getTwentyonedays());
            jsonObject.put("twentytwodays",campingCarPrice.getTwentytwodays());
            jsonObject.put("twentythreedays",campingCarPrice.getTwentythreedays());
            jsonObject.put("twentyfourdays",campingCarPrice.getTwentyfourdays());
            jsonObject.put("twentyfivedays",campingCarPrice.getTwentyfivedays());
            jsonObject.put("twentysixdays",campingCarPrice.getTwentysixdays());
            jsonObject.put("twentysevendays",campingCarPrice.getTwentysevendays());
            jsonObject.put("twentyeightdays",campingCarPrice.getTwentyeightdays());
            jsonObject.put("twentyninedays",campingCarPrice.getTwentyninedays());
            jsonObject.put("thirtydays",campingCarPrice.getThirtydays());

            PrintWriter pw = res.getWriter();
            pw.print(jsonObject);
            pw.flush();
            pw.close();
        } else if(season.equals("1")) {

            CampingCarPrice campingCarPrice;

            campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(carType+"_peak");

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("deposit",campingCarPrice.getDeposit());
            jsonObject.put("yearmodel",campingCarPrice.getYearmodel());
            jsonObject.put("carName",campingCarPrice.getCarName());
            jsonObject.put("onedays",campingCarPrice.getOnedays());
            jsonObject.put("twodays",campingCarPrice.getTwodays());
            jsonObject.put("threedays",campingCarPrice.getThreedays());
            jsonObject.put("fourdays",campingCarPrice.getFourdays());
            jsonObject.put("fivedays",campingCarPrice.getFivedays());
            jsonObject.put("sixdays",campingCarPrice.getSixdays());
            jsonObject.put("sevendays",campingCarPrice.getSevendays());
            jsonObject.put("eightdays",campingCarPrice.getEightdays());
            jsonObject.put("ninedays",campingCarPrice.getNinedays());
            jsonObject.put("tendays",campingCarPrice.getTendays());
            jsonObject.put("elevendays",campingCarPrice.getElevendays());
            jsonObject.put("twelvedays",campingCarPrice.getTwelvedays());
            jsonObject.put("thirteendays",campingCarPrice.getThirteendays());
            jsonObject.put("fourteendays",campingCarPrice.getFourteendays());
            jsonObject.put("fifteendays",campingCarPrice.getFifteendays());
            jsonObject.put("sixteendays",campingCarPrice.getSixteendays());
            jsonObject.put("seventeendays",campingCarPrice.getSeventeendays());
            jsonObject.put("eighteendays",campingCarPrice.getEighteendays());
            jsonObject.put("ninetinedays",campingCarPrice.getNinetinedays());
            jsonObject.put("twentydays",campingCarPrice.getTwentydays());
            jsonObject.put("twentyonedays",campingCarPrice.getTwentyonedays());
            jsonObject.put("twentytwodays",campingCarPrice.getTwentytwodays());
            jsonObject.put("twentythreedays",campingCarPrice.getTwentythreedays());
            jsonObject.put("twentyfourdays",campingCarPrice.getTwentyfourdays());
            jsonObject.put("twentyfivedays",campingCarPrice.getTwentyfivedays());
            jsonObject.put("twentysixdays",campingCarPrice.getTwentysixdays());
            jsonObject.put("twentysevendays",campingCarPrice.getTwentysevendays());
            jsonObject.put("twentyeightdays",campingCarPrice.getTwentyeightdays());
            jsonObject.put("twentyninedays",campingCarPrice.getTwentyninedays());
            jsonObject.put("thirtydays",campingCarPrice.getThirtydays());


            PrintWriter pw = res.getWriter();
            pw.print(jsonObject);
            pw.flush();
            pw.close();
        }

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
        params.put("to", "01058283328, 01033453328, 01052774113"); // 01033453328 추가
        params.put("from", "01052774113");
        params.put("type", "LMS");

        /* 고객에게 예약확인 문자 전송 */
        params2.put("to", dto.getPhone());
        params2.put("from", "01052774113");  // 16613331 테스트하기
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


}
