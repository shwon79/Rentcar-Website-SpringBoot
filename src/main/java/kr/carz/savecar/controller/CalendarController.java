package kr.carz.savecar.controller;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.dto.CampingCarReservationDTO;
import kr.carz.savecar.service.*;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import net.nurigo.java_sdk.api.Message;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class CalendarController {
    MonthlyRentService monthlyRentService;
    YearlyRentService yearlyRentService;
    ShortRentService shortRentService;
    CampingCarService campingCarService;
    CalendarDateService calendarDateService;
    CalendarTimeService calendarTimeService;
    DateCampingService dateCampingService;
    CampingCarPriceService campingCarPriceService;
    CampingcarReservationService campingcarReservationService;
    ExplanationService explanationService;

    @Autowired
    public CalendarController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService,
                              ShortRentService shortRentService, CampingCarService campingCarService, CalendarDateService calendarDateService,
                              CalendarTimeService calendarTimeService, DateCampingService dateCampingService,
                              CampingCarPriceService campingCarPriceService, CampingcarReservationService campingcarReservationService,
                              ExplanationService explanationService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.shortRentService = shortRentService;
        this.campingCarService = campingCarService;
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


    // 캠핑카 예약 수정하기 api
    @PutMapping(value = "/admin/campingcar/reservation/{carType}/{rentDate}/{rentTime}/{day}")
    @ResponseBody
    public void put_admin_campingcar_reservation(HttpServletResponse res, @PathVariable String carType, @PathVariable String rentDate, @PathVariable String rentTime, @PathVariable String day) throws Exception {

        JSONObject jsonObject = new JSONObject();
        String [] splitedRentDate = rentDate.split(".");

        if (splitedRentDate.length < 4){
            System.out.println("날짜 형식 오류");
            jsonObject.put("result", 0);
        } else {
            CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(carType);
            CalendarDate calendarStartDate = calendarDateService.findCalendarDateByMonthAndDayAndYear(splitedRentDate[1], splitedRentDate[2], splitedRentDate[0]);
            Long startDateId = calendarStartDate.getDateId();

            String startDate = splitedRentDate[0] + String.format("%02d", splitedRentDate[1]) + String.format("%02d", splitedRentDate[2]);
            String endDate = AddDate(startDate, 0, 0, Integer.valueOf(day));

            CalendarDate calendarEndDate = calendarDateService.findCalendarDateByMonthAndDayAndYear(splitedRentDate[1], splitedRentDate[2], splitedRentDate[0]);
            Long endDateId = calendarEndDate.getDateId();

//            CalendarTime calendarTime = calendarTimeService.findCalendarTimeByDateIdAndCarNameAndReserveTime(calendarDate, campingCarPrice, rentTime);
//
//            DateCamping dateCamping = dateCampingService.findByDateIdAndCarName(calendarDate, campingCarPrice);
//            dateCamping.setReserved("1");

        }


        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }



    @GetMapping("/camping/{carType}")
    public String get_camping_carType(ModelMap model, @PathVariable("carType") String carType) throws Exception {

        Optional<Explanation> explanation = explanationService.findById(Long.valueOf(0));
        model.put("explanation", explanation.get());

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

        int[] prevMonthDate;
        int[] nextMonthDate;

        if(thisYear == year && thisMonth == month){

            prevMonthDate = DateStringToInt(AddDate(TodayDateString(), 0, -1, 0));
            nextMonthDate = DateStringToInt(AddDate(TodayDateString(), 0, 1, 0));

            calendarDateList = calendarDateService.findCalendarDateByMonth(Integer.toString(thisMonth));

            Long firstDateId = calendarDateList.get(0).getDateId();
            for (int i = 1; i < thisDayOfWeek; i++) {
                calendarDateList.add(0, calendarDateService.findCalendarDateByDateId(firstDateId - i));
            }

            for (CalendarDate calendarDate : calendarDateList) { dateCampingList.add(dateCampingService.findByDateId(calendarDate)); }


        } else {

            calendarDateList = calendarDateService.findCalendarDateByMonth(Long.toString(month));

            Long firstDateId = calendarDateList.get(0).getDateId();
            for (int i = 1; i < thisDayOfWeek; i++) {
                calendarDateList.add(0, calendarDateService.findCalendarDateByDateId(firstDateId - i));
            }


            for (CalendarDate calendarDate : calendarDateList) { dateCampingList.add(dateCampingService.findByDateId(calendarDate)); }

            String date = year + String.format("%02d", month) + "01";

            prevMonthDate = DateStringToInt(AddDate(date, 0, -1, 0));
            nextMonthDate = DateStringToInt(AddDate(date, 0, 1, 0));
        }

        model.addAttribute("calendarDateList", calendarDateList);
        model.addAttribute("dateCampingList", dateCampingList);

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
        List<CalendarDate> calendarDateList = calendarDateService.findCalendarDateByMonth(Long.toString(clickedMonth));

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
        for (CalendarDate calendarDate : calendarDateList) { dateCampingList.add(dateCampingService.findByDateId(calendarDate)); }


        Optional<Explanation> explanation = explanationService.findById(Long.valueOf(0));
        model.put("explanation", explanation.get());

        model.addAttribute("calendarDateList", calendarDateList);
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

    @GetMapping("/camping/calendar/{carType}_reserve/reservation/{rent_date}/{rent_time}/{return_date}/{return_time}/{day}/{total}")
    public String handleRequest_reserve(ModelMap model, @PathVariable("carType") String carType,@PathVariable("rent_date") String rent_date, @PathVariable("rent_time") String rent_time, @PathVariable("return_date") String return_date, @PathVariable("return_time") String return_time, @PathVariable("day") String day, @PathVariable("total") String total) throws Exception {

        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName("limousine");
        model.put("campingCarPrice", campingCarPrice);

        model.put("rent_date", rent_date);
        model.put("rent_time", rent_time);
        model.put("return_date", return_date);
        model.put("return_time", return_time);
        model.put("day", day);
        model.put("total", total);
        model.put("carType", carType);


        return "rent_camping/reservation";
    }


    // 캠핑카 가격 구하는 api
    @RequestMapping(value = "/camping/calendar/{carType}/getprice/{season}", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_travel_price(HttpServletResponse res, @PathVariable String carType, @PathVariable String season) throws IOException {

        if(season.equals("0")){
            CampingCarPrice campingCarPrice;

            campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(carType);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("deposit",campingCarPrice.getDeposit());
            jsonObject.put("yearmodel",campingCarPrice.getYearmodel());
            jsonObject.put("carName",campingCarPrice.getCarName());
            jsonObject.put("onedays",campingCarPrice.getOnedays());
            System.out.println("oneoneoneone - "+campingCarPrice.getOnedays());
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
            System.out.println("oneoneoneone - "+campingCarPrice.getOnedays());
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


    // 캠핑카 예약 저장 api
    @PostMapping("/camping/calendar/reserve")
    @ResponseBody
    public void camping_calendar_reservation(HttpServletResponse res, @RequestBody CampingCarReservationDTO dto) throws IOException{

//        // 문자전송
//        Message coolsms = new Message(api_key, api_secret);
//        HashMap<String, String> params = new HashMap<>();
//        HashMap<String, String> params2 = new HashMap<>();
//
//
//        /* 세이브카에 예약확인 문자 전송 */
//        params.put("to", "01058283328, 01033453328, 01052774113"); // 01033453328 추가
//        params.put("from", "01052774113");
//        params.put("type", "LMS");
//
//
//        /* 고객에게 예약확인 문자 전송 */
//
//        params2.put("to", dto.getPhone());
//        params2.put("from", "01052774113");  // 16613331 테스트하기
//        params2.put("type", "LMS");
//
//        params.put("text", "[캠핑카 실시간 예약]\n"
//                + "성함: " + dto.getName() + "\n"
//                + "전화번호: " + dto.getPhone() + "\n"
//                + "차량명: " + dto.getCarType() + "\n"
//                + "입금자명: " + dto.getDepositor() + "\n"
//                + "대여날짜: " + dto.getRentDate() + "\n"
//                + "대여시간: " + dto.getRentTime() + "\n"
//                + "반납날짜: " + dto.getReturnDate() + "\n"
//                + "반납시간: " + dto.getReturnTime() + "\n"
//                + "이용날짜: " + dto.getDay() + "\n"
//                + "총금액: " + dto.getTotal() + "\n"
//                + "선결제금액: " + dto.getTotalHalf() + "\n"
//                + "요청사항: " + dto.getDetail() + "\n\n");
//
//        params2.put("text", "[캠핑카 예약 대기 신청이 완료되었습니다.]" + "\n"
//                + "성함: " + dto.getName() + "\n"
//                + "전화번호: " + dto.getPhone() + "\n"
//                + "차량명: " + dto.getCarType() + "\n"
//                + "대여날짜: " + dto.getRentDate() + "\n"
//                + "대여시간: " + dto.getRentTime() + "\n"
//                + "반납날짜: " + dto.getReturnDate() + "\n"
//                + "반납시간: " + dto.getReturnTime() + "\n"
//                + "입금자명: " + dto.getDepositor() + "\n"
//                + "이용날짜: " + dto.getDay() + "\n"
//                + "총금액: " + dto.getTotal() + "\n"
//                + "선결제금액: " + dto.getTotalHalf() + "\n"
//                + "요청사항: " + dto.getDetail() + "\n\n");
//
//
//        params.put("app_version", "test app 1.2");
//        params2.put("app_version", "test app 1.2");
//
//
//        /* 세이브카에게 문자 전송 */
//
//        try {
//            org.json.simple.JSONObject obj = coolsms.send(params);
//            System.out.println(obj.toString()); //전송 결과 출력
//        } catch (CoolsmsException e) {
//            System.out.println(e.getMessage());
//            System.out.println(e.getCode());
//        }
//
//        /* 고객에게 예약확인 문자 전송 */
//
//        try {
//            org.json.simple.JSONObject obj2 = coolsms.send(params2);
//            System.out.println(obj2.toString()); //전송 결과 출력
//        } catch (CoolsmsException e) {
//            System.out.println(e.getMessage());
//            System.out.println(e.getCode());
//        }

        campingcarReservationService.save_campingcar_reservation(dto);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }



//    // 캠핑카 예약 저장 api
//    @RequestMapping(value = "/camping/calendar/{carType}/sendrentdate/{year}/{month}/{day}", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
//    @ResponseBody
//    public void send_rent_date_travel(HttpServletResponse res, @PathVariable String carType, @PathVariable String year, @PathVariable String month, @PathVariable String day) throws IOException {
//
//
//        CalendarDate calendarDate = calendarDateService.findCalendarDateByMonthAndDayAndYear(month, day, year);
//
//
//        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(carType);
//
//        List<CalendarTime> calendarTimeList = calendarTimeService.findCalendarTimeByDateIdAndCarName(calendarDate,campingCarPrice);
//        List <String> categoryList2 = new ArrayList();
//
//        for (int i = 0; i < calendarTimeList.size(); i++) {
//            if (calendarTimeList.get(i).getReserveComplete().equals("0")){
//
//                categoryList2.add(calendarTimeList.get(i).getReserveTime());
//            }
//        }
//        JSONArray jsonArray = new JSONArray();
//
//        for (String c : categoryList2) {
//            jsonArray.put(c);
//        }
//
//        PrintWriter pw = res.getWriter();
//        pw.print(jsonArray.toString());
//        pw.flush();
//        pw.close();
//    }


}
