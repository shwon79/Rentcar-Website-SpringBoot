package kr.carz.savecar.controller;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
    MonthlyRentService monthlyRentService;
    YearlyRentService yearlyRentService;
    ShortRentService shortRentService;
    CampingCarService campingCarService;
    CalendarDateService calendarDateService;
    CalendarTimeService calendarTimeService;
    DateCampingService dateCampingService;
    CampingCarPriceService campingCarPriceService;
    CampingcarDateTimeService2 campingcarDateTimeService2;

    @Autowired
    public CalendarController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService,
                              ShortRentService shortRentService, CampingCarService campingCarService, CalendarDateService calendarDateService,
                              CalendarTimeService calendarTimeService, DateCampingService dateCampingService,
                              CampingCarPriceService campingCarPriceService, CampingcarDateTimeService2 campingcarDateTimeService2) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.shortRentService = shortRentService;
        this.campingCarService = campingCarService;
        this.calendarDateService = calendarDateService;
        this.calendarTimeService = calendarTimeService;
        this.dateCampingService = dateCampingService;
        this.campingCarPriceService = campingCarPriceService;
        this.campingcarDateTimeService2 = campingcarDateTimeService2;
    }

    private static final SimpleDateFormat std_data_format = new SimpleDateFormat("yyyyMMdd");

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


    @GetMapping("/camping/calendar/{year}/{month}")
    public String camping_calendar_different_month(ModelMap model, @PathVariable("year") int year, @PathVariable("month") int month) throws Exception {

        int thisDay = TodayDateInt()[2];
        int thisYear = TodayDateInt()[0];
        int thisMonth = TodayDateInt()[1];

        // 저번달 날짜
        Calendar cal = Calendar.getInstance();
        cal.set(year, month-1, 1);
        int thisDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        if(thisYear == year && thisMonth == month){

            int[] prevMonthDate = DateStringToInt(AddDate(TodayDateString(), 0, -1, 0));
            int[] nextMonthDate = DateStringToInt(AddDate(TodayDateString(), 0, 1, 0));

            List<CalendarDate> calendarDateList = calendarDateService.findCalendarDateByMonth(Integer.toString(thisMonth));
            ArrayList dateCampingList = new ArrayList();

            Long firstDateId = calendarDateList.get(0).getDateId();
            for (int i = 1; i < thisDayOfWeek; i++) {
                calendarDateList.add(0, calendarDateService.findCalendarDateByDateId(firstDateId - i));
            }
            System.out.println(thisDayOfWeek);

            for (CalendarDate calendarDate : calendarDateList) { dateCampingList.add(dateCampingService.findByDateId(calendarDate)); }

            model.addAttribute("calendarDateList", calendarDateList);
            model.addAttribute("dateCampingList", dateCampingList);

            model.addAttribute("thisMonth", thisMonth);
            model.addAttribute("thisYear", thisYear);
            model.addAttribute("thisDay", thisDay);
            model.addAttribute("prevMonth", prevMonthDate[1]);
            model.addAttribute("nextMonth", nextMonthDate[1]);

        } else {

            // 이번달 날짜
            List<CalendarDate> calendarDateList = calendarDateService.findCalendarDateByMonth(Long.toString(month));

            Long firstDateId = calendarDateList.get(0).getDateId();
            for (int i = 1; i < thisDayOfWeek; i++) {
                calendarDateList.add(0, calendarDateService.findCalendarDateByDateId(firstDateId - i));
            }

            // 날짜별 캠핑카
            List<List<DateCamping>> dateCampingList = new ArrayList();

            for (CalendarDate calendarDate : calendarDateList) { dateCampingList.add(dateCampingService.findByDateId(calendarDate)); }

            String date = year + String.format("%02d", month) + "01";

            int[] prevMonthDate = DateStringToInt(AddDate(date, 0, -1, 0));
            int[] nextMonthDate = DateStringToInt(AddDate(date, 0, 1, 0));


            model.addAttribute("calendarDateList", calendarDateList);
            model.addAttribute("dateCampingList", dateCampingList);

            model.addAttribute("thisMonth", month);
            model.addAttribute("thisYear", year);
            model.addAttribute("thisDay", 1);
            model.addAttribute("prevMonth", prevMonthDate[1]);
            model.addAttribute("nextMonth", nextMonthDate[1]);
        }


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
        model.addAttribute("today", thisDay);

        return "rentcamping/" + carType;
    }


    // 캠핑카 예약 저장 api
    @RequestMapping(value = "/camping/calendar/{carType}/sendrentdate/{year}/{month}/{day}", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void send_rent_date_travel(HttpServletResponse res, @PathVariable String carType, @PathVariable String year, @PathVariable String month, @PathVariable String day) throws IOException {


        CalendarDate calendarDate = calendarDateService.findCalendarDateByMonthAndDayAndYear(month, day, year);


        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(carType);

        List<CalendarTime> calendarTimeList = calendarTimeService.findCalendarTimeByDateIdAndCarName(calendarDate,campingCarPrice);
        List <String> categoryList2 = new ArrayList();

        for (int i = 0; i < calendarTimeList.size(); i++) {
            if (calendarTimeList.get(i).getReserveComplete().equals("0")){

                categoryList2.add(calendarTimeList.get(i).getReserveTime());
            }
        }
        JSONArray jsonArray = new JSONArray();

        for (String c : categoryList2) {
            jsonArray.put(c);
            System.out.println(c);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
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


    // 캠핑카 대여가능일자 구하는 api
    @RequestMapping(value = "/camping/calendar/{carType}/getrentdate/{year}/{month}/{day}", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_rent_date(HttpServletResponse res, @PathVariable String carType, @PathVariable String year, @PathVariable String month, @PathVariable String day) throws IOException {

        CalendarDate calendarDate = calendarDateService.findCalendarDateByMonthAndDayAndYear(month, day, year);
        CampingCarPrice campingCarPrice;
        campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(carType);


        Long date_start_id = calendarDate.getDateId();

        int possible_rent_date = 0;

        for(int i=1; i<=31; i++){
            if(date_start_id+i > 167){  // 12월 4일
                break;
            }
            CalendarDate calendarDate_continue = calendarDateService.findCalendarDateByDateId(date_start_id+i);
            DateCamping dateCamping_continue = dateCampingService.findByDateIdAndCarName(calendarDate_continue, campingCarPrice);
            if(dateCamping_continue.getReserved().equals("1")){
                break;
            }
            possible_rent_date += 1;
        }

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(possible_rent_date);


        PrintWriter pw = res.getWriter();
        pw.print(jsonArray);
        pw.flush();
        pw.close();
    }



    // 캠핑카 가능한 추가시간 구하는 api
    @RequestMapping(value = "/camping/calendar/{carType}/getextratime/{year}/{month}/{day}/{rentDays}/{rentStartTime}", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_extra_time(HttpServletResponse res, @PathVariable String carType, @PathVariable String year, @PathVariable String month, @PathVariable String day, @PathVariable Integer rentDays, @PathVariable String rentStartTime) throws IOException {

        CalendarDate calendarDate = calendarDateService.findCalendarDateByMonthAndDayAndYear(month, day, year);
        CampingCarPrice campingCarPrice;

        campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(carType);


        Long date_start_id = calendarDate.getDateId();
        Long date_last_id = date_start_id + rentDays;

        CalendarDate calendarLastDate = calendarDateService.findCalendarDateByDateId(date_last_id);


        List<CalendarTime> calendarTimeList = calendarTimeService.findCalendarTimeByDateIdAndCarName(calendarLastDate, campingCarPrice);

        Integer extraTime = 0;
        Integer flg = 0;


        int i;
        for (i=0; i<calendarTimeList.size(); i++){

            if (flg == 1){
                if (calendarTimeList.get(i).getReserveComplete().equals("1")){
                    break;
                } else {
                    extraTime += 1;
                }
            }

            if (calendarTimeList.get(i).getReserveTime().equals(rentStartTime)){
                flg = 1;
            }
        }


        System.out.println("마지막 time:"+calendarTimeList.get(i-1).getReserveTime());
        if (!calendarTimeList.get(i-1).getReserveTime().equals("18시") && extraTime >= 2){
            extraTime -= 2;
        }

        System.out.println("extraTime: "+extraTime);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(extraTime);


        PrintWriter pw = res.getWriter();
        pw.print(jsonArray);
        pw.flush();
        pw.close();
    }


//    @GetMapping("/camping/calendar/{carType}_reserve/{date_id}")
//    public String handleRequest(ModelMap model, @PathVariable("carType") String carType, @PathVariable("date_id") Long date_id) throws Exception {
//
//        // 클릭한 날짜 데이터
//        CalendarDate clickedDate = calendarDateService.findCalendarDateByDateId(date_id);
//
//        // 날짜
//        int thisYear = TodayDateInt()[0];
//        int thisMonth = TodayDateInt()[1];
//        int thisDay = TodayDateInt()[2];
//
//        CalendarDate calendarDate = calendarDateService.findCalendarDateByDateId(date_id);
//        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(carType);
//        List<CalendarTime> calendarTimeList = calendarTimeService.findCalendarTimeByDateIdAndCarName(calendarDate, campingCarPrice);
//
//        // 이번달 날짜
//        List<CalendarDate> calendarDateList = calendarDateService.findCalendarDateByMonth(clickedDate.getMonth());
//
//        // 저번달 날짜
//        Calendar cal = Calendar.getInstance();
//        cal.set(Integer.parseInt(clickedDate.getYear()), Integer.parseInt(clickedDate.getMonth())-1, 1);
//        int thisDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
//
//        Long firstDateId = calendarDateList.get(0).getDateId();
//        for (int i = 1; i < thisDayOfWeek; i++) {
//            calendarDateList.add(0, calendarDateService.findCalendarDateByDateId(firstDateId - i));
//        }
//
//        // 날짜별 캠핑카
//        List<List<DateCamping>> dateCampingList = new ArrayList();
//
//        for (CalendarDate calendarDateInd : calendarDateList) { dateCampingList.add(dateCampingService.findByDateId(calendarDateInd)); }
//
//        // 클릭된 날짜
//        String clickedDateFormated = calendarDate.getYear() + String.format("%02d", Integer.parseInt(calendarDate.getMonth())) + "01";
//        int[] clickedPrevMonthDate = DateStringToInt(AddDate(clickedDateFormated, 0, -1, 0));
//        int[] clickedNextMonthDate = DateStringToInt(AddDate(clickedDateFormated, 0, 1, 0));
//
//
//        model.addAttribute("calendarDateList", calendarDateList);
//        model.addAttribute("dateCampingList", dateCampingList);
//        model.put("campingCarPrice", campingCarPrice);
//
//        model.addAttribute("dateId", date_id);
//        model.addAttribute("clickedDay", clickedDate.getDay());
//        model.addAttribute("clickedMonth", clickedDate.getMonth());
//        model.addAttribute("clickedYear", clickedDate.getYear());
//        model.addAttribute("clickedWDay", clickedDate.getWDay());
//
//        model.addAttribute("clickedPrevYear", clickedPrevMonthDate[0]);
//        model.addAttribute("clickedPrevMonth", clickedPrevMonthDate[1]);
//        model.addAttribute("clickedNextYear", clickedNextMonthDate[0]);
//        model.addAttribute("clickedNextMonth", clickedNextMonthDate[1]);
//
//        model.addAttribute("thisYear", thisYear);
//        model.addAttribute("thisMonth", thisMonth);
//        model.addAttribute("today", thisDay);
//        model.addAttribute("calendarTimeList", calendarTimeList);
//
//        return  "camping_" + carType;
//    }



}
