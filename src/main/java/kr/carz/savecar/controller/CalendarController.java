package kr.carz.savecar.controller;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
    CampingcarDateTimeService campingcarDateTimeService;

    @Autowired
    public CalendarController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService,
                              ShortRentService shortRentService, CampingCarService campingCarService, CalendarDateService calendarDateService,
                              CalendarTimeService calendarTimeService, DateCampingService dateCampingService,
                              CampingCarPriceService campingCarPriceService, CampingcarDateTimeService campingcarDateTimeService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.shortRentService = shortRentService;
        this.campingCarService = campingCarService;
        this.calendarDateService = calendarDateService;
        this.calendarTimeService = calendarTimeService;
        this.dateCampingService = dateCampingService;
        this.campingCarPriceService = campingCarPriceService;
        this.campingcarDateTimeService = campingcarDateTimeService;
    }



    @GetMapping("/travel")
    public String camping_travel() {
        return "camping_travel";
    }

    @GetMapping("/liomousine")
    public String camping_liomousine() {
        return "camping_liomousine";
    }

    @GetMapping("/europe")
    public String camping_europe(Model model) {
        // 날짜
        Calendar cal = Calendar.getInstance();

        int today = cal.get(Calendar.DAY_OF_MONTH) + 1;

        model.addAttribute("today", today);
        System.out.println(today);

        int now_month = cal.get(Calendar.MONTH)+1;
        int now_year = cal.get(Calendar.YEAR);

        List<CalendarDate> calendarDateList = calendarDateService.findCalendarDateByMonth(Integer.toString(now_month));


        // 전달 날짜 구하기
        cal.set(cal.get(Calendar.YEAR),now_month-1,1);

        Long firstDateId = calendarDateList.get(0).getDateId();
        Integer before = cal.get(Calendar.DAY_OF_WEEK);


        for(int i=1; i<before; i++){
            calendarDateList.add(0, calendarDateService.findCalendarDateByDateId(firstDateId - i));
        }

        // 다음달 날짜 구하기
        cal.set(cal.get(Calendar.YEAR),now_month-1,Integer.parseInt(calendarDateList.get(calendarDateList.size() - 1).getDay()));

        Long lastDateId = calendarDateList.get(calendarDateList.size() - 1).getDateId();
        Integer after = cal.get(Calendar.DAY_OF_WEEK);

        for(int i=1; i<=7-after; i++){
            calendarDateList.add(calendarDateService.findCalendarDateByDateId(lastDateId + i));
        }

        Integer daylast = 7-after;
        System.out.println(daylast);

        model.addAttribute("daylast", calendarDateList);
        model.addAttribute("calendarDateList", calendarDateList);



        // 날짜별 캠핑카
        List<List<DateCamping>> dateCampingList = new ArrayList();

        for (int i=0; i<calendarDateList.size(); i++){
            dateCampingList.add(dateCampingService.findByDateId(calendarDateList.get(i)));
        }

        System.out.println(dateCampingList.get(0).get(0).getCarName());
        model.addAttribute("dateCampingList", dateCampingList);
        model.addAttribute("thisMonth", now_month);
        model.addAttribute("thisYear", now_year);


        return "calendar";
    }


    @RequestMapping("/calendar/{month}")
    public String handleRequest1(HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable("month") Long month) throws Exception {
        // 날짜
        Calendar cal = Calendar.getInstance();

        int now_month = month.intValue()+1; // 9
        int today = cal.get(Calendar.DAY_OF_MONTH) + 1;
        int this_month = cal.get(Calendar.MONTH) + 1;


        if(month == this_month-1){
            model.addAttribute("today", today);
            System.out.println(this_month);
        } else {
            model.addAttribute("today", 0);
            System.out.println(0);
        }

        if(now_month > 11){
            response.setContentType("text/html; charset=UTF-8");

            PrintWriter out = response.getWriter();

            out.println("<script>alert('이용할 수 없는 예약일자입니다.'); location.href='/calendar/10';</script>");

            out.flush();
        } else {

            List<CalendarDate> calendarDateList = calendarDateService.findCalendarDateByMonth(Long.toString(now_month));


            // 전달 날짜 구하기
            cal.set(cal.get(Calendar.YEAR), now_month - 1, 1); // 8

            Long firstDateId = calendarDateList.get(0).getDateId();
            Integer before = cal.get(Calendar.DAY_OF_WEEK);

            for (int i = 1; i < before; i++) {
                calendarDateList.add(0, calendarDateService.findCalendarDateByDateId(firstDateId - i));
            }

            // 다음달 날짜 구하기
            cal.set(cal.get(Calendar.YEAR), now_month - 1, Integer.parseInt(calendarDateList.get(calendarDateList.size() - 1).getDay()));

            Long lastDateId = calendarDateList.get(calendarDateList.size() - 1).getDateId();
            Integer after = cal.get(Calendar.DAY_OF_WEEK);

            for (int i = 1; i <= 7 - after; i++) {
                calendarDateList.add(calendarDateService.findCalendarDateByDateId(lastDateId + i));
            }

            Integer daylast = 7-after;
            System.out.println(daylast);

            model.addAttribute("daylast", calendarDateList);
            model.addAttribute("calendarDateList", calendarDateList);


            // 날짜별 캠핑카
            List<List<DateCamping>> dateCampingList = new ArrayList();

            for (int i = 0; i < calendarDateList.size(); i++) {
                dateCampingList.add(dateCampingService.findByDateId(calendarDateList.get(i)));
            }

            System.out.println(dateCampingList.get(0).get(0).getCarName());
            model.addAttribute("dateCampingList", dateCampingList);
            model.addAttribute("prevMonth", now_month-1);
            model.addAttribute("thisMonth", now_month);
            model.addAttribute("thisYear", 2021);
        }


        return "calendar";
    }



    @RequestMapping("/calendar/before/{month}")
    public String handleRequest2(HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable("month") Long month) throws Exception {
        // 날짜
        Calendar cal = Calendar.getInstance();

        int now_month = month.intValue(); // 9
        int today = cal.get(Calendar.DAY_OF_MONTH) + 1;
        int this_month = cal.get(Calendar.MONTH) + 1;


        if(month == this_month){
            model.addAttribute("today", today);
            System.out.println(this_month);
        } else {
            model.addAttribute("today", 0);
            System.out.println(0);
        }

        if(now_month < 8){
            response.setContentType("text/html; charset=UTF-8");

            PrintWriter out = response.getWriter();

            out.println("<script>alert('이용할 수 없는 예약일자입니다.'); location.href='/calendar/7';</script>");

            out.flush();
        } else {

            List<CalendarDate> calendarDateList = calendarDateService.findCalendarDateByMonth(Long.toString(now_month));


            // 전달 날짜 구하기
            cal.set(cal.get(Calendar.YEAR), now_month - 1, 1); // 8

            Long firstDateId = calendarDateList.get(0).getDateId();
            Integer before = cal.get(Calendar.DAY_OF_WEEK);

            for (int i = 1; i < before; i++) {
                calendarDateList.add(0, calendarDateService.findCalendarDateByDateId(firstDateId - i));
            }

            // 다음달 날짜 구하기
            cal.set(cal.get(Calendar.YEAR), now_month - 1, Integer.parseInt(calendarDateList.get(calendarDateList.size() - 1).getDay()));

            Long lastDateId = calendarDateList.get(calendarDateList.size() - 1).getDateId();
            Integer after = cal.get(Calendar.DAY_OF_WEEK);

            for (int i = 1; i <= 7 - after; i++) {
                calendarDateList.add(calendarDateService.findCalendarDateByDateId(lastDateId + i));
            }
            Integer daylast = 7-after;
            System.out.println(daylast);

            model.addAttribute("daylast", calendarDateList);
            model.addAttribute("calendarDateList", calendarDateList);


            // 날짜별 캠핑카
            List<List<DateCamping>> dateCampingList = new ArrayList();

            for (int i = 0; i < calendarDateList.size(); i++) {
                dateCampingList.add(dateCampingService.findByDateId(calendarDateList.get(i)));
            }

            System.out.println(dateCampingList.get(0).get(0).getCarName());
            model.addAttribute("dateCampingList", dateCampingList);
            model.addAttribute("prevMonth", now_month-1);
            model.addAttribute("thisMonth", now_month);
            model.addAttribute("thisYear", 2021);
        }


        return "calendar";
    }



    @RequestMapping("/europe_reserve/{date_id}")
    public String handleRequest(ModelMap model, @PathVariable("date_id") Long date_id) throws Exception {

        // 시간
        CalendarDate calendarDate = calendarDateService.findCalendarDateByDateId(date_id);
        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName("europe");

        List<CalendarTime> calendarTimeList = calendarTimeService.findCalendarTimeByDateIdAndCarName(calendarDate, campingCarPrice);

        model.addAttribute("calendarTimeList", calendarTimeList);

        for(int i=0; i<calendarTimeList.size(); i++){
            System.out.println(calendarTimeList.get(i).getTimeId());
        }


        // 날짜
        Calendar cal = Calendar.getInstance();

        int now_month = cal.get(Calendar.MONTH)+1;

        List<CalendarDate> calendarDateList = calendarDateService.findCalendarDateByMonth(Integer.toString(now_month));


        // 전달 날짜 구하기
        cal.set(cal.get(Calendar.YEAR),now_month-1,1);

        Long firstDateId = calendarDateList.get(0).getDateId();
        Integer before = cal.get(Calendar.DAY_OF_WEEK);

        System.out.println(before);

        for(int i=1; i<before; i++){
            calendarDateList.add(0, calendarDateService.findCalendarDateByDateId(firstDateId - i));
        }

        // 다음달 날짜 구하기
        cal.set(cal.get(Calendar.YEAR),now_month-1,Integer.parseInt(calendarDateList.get(calendarDateList.size() - 1).getDay()));

        Long lastDateId = calendarDateList.get(calendarDateList.size() - 1).getDateId();
        Integer after = cal.get(Calendar.DAY_OF_WEEK);

        for(int i=1; i<=7-after; i++){
            calendarDateList.add(calendarDateService.findCalendarDateByDateId(lastDateId + i));
        }

        model.addAttribute("calendarDateList", calendarDateList);


        // 날짜별 캠핑카
        List<List<DateCamping>> dateCampingList = new ArrayList();

        for (int i=0; i<calendarDateList.size(); i++){
            dateCampingList.add(dateCampingService.findByDateId(calendarDateList.get(i)));
        }

        System.out.println(dateCampingList.get(0).get(0).getCarName());
        model.addAttribute("dateCampingList", dateCampingList);


        // 캠핑카 가격
        model.put("campingCarPrice", campingCarPrice);  // 리스트 => 도메인 변수랑 이름 똑같이 해서 쓸 수 있음
        model.addAttribute("thisMonth", now_month);
        model.addAttribute("thisYear", 2021);

        return "camping_europe";
    }


    @RequestMapping("/europe_reserve/after/{month}")
    public String handleRequest3(HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable("month") Long month) throws Exception {
        // 날짜
        Calendar cal = Calendar.getInstance();
        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName("europe");


        int now_month = month.intValue()+1; // 9
        int today = cal.get(Calendar.DAY_OF_MONTH) + 1;
        int this_month = cal.get(Calendar.MONTH) + 1;


        if(month == this_month-1){
            model.addAttribute("today", today);
            System.out.println(this_month);
        } else {
            model.addAttribute("today", 0);
            System.out.println(0);
        }

        if(now_month > 11){
            response.setContentType("text/html; charset=UTF-8");

            PrintWriter out = response.getWriter();

            out.println("<script>alert('이용할 수 없는 예약일자입니다.'); location.href='/europe_reserve/after/10';</script>");

            out.flush();
        } else {

            List<CalendarDate> calendarDateList = calendarDateService.findCalendarDateByMonth(Long.toString(now_month));


            // 전달 날짜 구하기
            cal.set(cal.get(Calendar.YEAR), now_month - 1, 1); // 8

            Long firstDateId = calendarDateList.get(0).getDateId();
            Integer before = cal.get(Calendar.DAY_OF_WEEK);

            for (int i = 1; i < before; i++) {
                calendarDateList.add(0, calendarDateService.findCalendarDateByDateId(firstDateId - i));
            }

            // 다음달 날짜 구하기
            cal.set(cal.get(Calendar.YEAR), now_month - 1, Integer.parseInt(calendarDateList.get(calendarDateList.size() - 1).getDay()));

            Long lastDateId = calendarDateList.get(calendarDateList.size() - 1).getDateId();
            Integer after = cal.get(Calendar.DAY_OF_WEEK);

            for (int i = 1; i <= 7 - after; i++) {
                calendarDateList.add(calendarDateService.findCalendarDateByDateId(lastDateId + i));
            }

            Integer daylast = 7-after;
            System.out.println(daylast);

            model.addAttribute("daylast", calendarDateList);
            model.addAttribute("calendarDateList", calendarDateList);


            // 날짜별 캠핑카
            List<List<DateCamping>> dateCampingList = new ArrayList();

            for (int i = 0; i < calendarDateList.size(); i++) {
                dateCampingList.add(dateCampingService.findByDateId(calendarDateList.get(i)));
            }

            model.addAttribute("dateCampingList", dateCampingList);

            // 캠핑카 가격
            model.put("campingCarPrice", campingCarPrice);  // 리스트 => 도메인 변수랑 이름 똑같이 해서 쓸 수 있음

            model.addAttribute("prevMonth", now_month-1);
            model.addAttribute("thisMonth", now_month);
            model.addAttribute("thisYear", 2021);
            System.out.println(now_month);

        }

        return "camping_europe";
    }




    @RequestMapping("/europe_reserve/before/{month}")
    public String handleRequest4(HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable("month") Long month) throws Exception {
        // 날짜
        Calendar cal = Calendar.getInstance();

        int now_month = month.intValue(); // 9
        int today = cal.get(Calendar.DAY_OF_MONTH) + 1;
        int this_month = cal.get(Calendar.MONTH) + 1;


        if(month == this_month){
            model.addAttribute("today", today);
            System.out.println(this_month);
        } else {
            model.addAttribute("today", 0);
            System.out.println(0);
        }

        if(now_month < 8){
            response.setContentType("text/html; charset=UTF-8");

            PrintWriter out = response.getWriter();

            out.println("<script>alert('이용할 수 없는 예약일자입니다.'); location.href='/europe_reserve/before/8';</script>");

            out.flush();
        } else {

            List<CalendarDate> calendarDateList = calendarDateService.findCalendarDateByMonth(Long.toString(now_month));


            // 전달 날짜 구하기
            cal.set(cal.get(Calendar.YEAR), now_month - 1, 1); // 8

            Long firstDateId = calendarDateList.get(0).getDateId();
            Integer before = cal.get(Calendar.DAY_OF_WEEK);

            for (int i = 1; i < before; i++) {
                calendarDateList.add(0, calendarDateService.findCalendarDateByDateId(firstDateId - i));
            }

            // 다음달 날짜 구하기
            cal.set(cal.get(Calendar.YEAR), now_month - 1, Integer.parseInt(calendarDateList.get(calendarDateList.size() - 1).getDay()));

            Long lastDateId = calendarDateList.get(calendarDateList.size() - 1).getDateId();
            Integer after = cal.get(Calendar.DAY_OF_WEEK);

            for (int i = 1; i <= 7 - after; i++) {
                calendarDateList.add(calendarDateService.findCalendarDateByDateId(lastDateId + i));
            }
            Integer daylast = 7-after;
            System.out.println(daylast);

            model.addAttribute("daylast", calendarDateList);
            model.addAttribute("calendarDateList", calendarDateList);


            // 날짜별 캠핑카
            List<List<DateCamping>> dateCampingList = new ArrayList();

            for (int i = 0; i < calendarDateList.size(); i++) {
                dateCampingList.add(dateCampingService.findByDateId(calendarDateList.get(i)));
            }

            System.out.println(dateCampingList.get(0).get(0).getCarName());
            model.addAttribute("dateCampingList", dateCampingList);
            model.addAttribute("prevMonth", now_month-1);
            model.addAttribute("thisMonth", now_month);
            model.addAttribute("thisYear", 2021);
        }


        return "camping_europe";
    }



    // 캠핑카 예약 저장 api
    @PostMapping("/campingcar/reserve")
    @ResponseBody
    public String save(@RequestBody CampingcarDateTimeDto dto){


        System.out.println(dto.getRentDate());
        System.out.println(dto.getRentTime());
        System.out.println(dto.getReturnDate());
        System.out.println(dto.getReturnTime());
        System.out.println(dto.getAgree()); // 1
        System.out.println(dto.getDeposit());
        System.out.println(dto.getDepositor());
        System.out.println(dto.getDetail());
        System.out.println(dto.getName());
        System.out.println(dto.getPhone());
        System.out.println(dto.getReservation()); // 1
        System.out.println(dto.getTotal());
        System.out.println(dto.getDay());


        campingcarDateTimeService.save(dto);

        return "paying";
    }


    // 캠핑카 예약 저장 api
    @PostMapping("/campingcar/sendrentdate")
    @ResponseBody
    public String send_rent_date(@RequestBody CalendarDate dto){


        System.out.println(dto.getMonth());
        System.out.println(dto.getDay());
        System.out.println(dto.getYear());

        return "paying";
    }


    @RequestMapping("/campingcar/reserve/{rent_date}/{rent_time}/{return_date}/{return_time}/{day}/{time}")
    public String handleRequest(ModelMap model, @PathVariable("rent_date") String rent_date, @PathVariable("rent_time") String rent_time, @PathVariable("return_date") String return_date, @PathVariable("return_time") String return_time, @PathVariable("day") String day, @PathVariable("time") String time) throws Exception {

        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName("europe");
        model.put("campingCarPrice", campingCarPrice);  // 리스트 => 도메인 변수랑 이름 똑같이 해서 쓸 수 있음

        model.put("rent_date", rent_date);
        model.put("rent_time", rent_time);
        model.put("return_date", return_date);
        model.put("return_time", return_time);
        model.put("day", day);
        model.put("time", time);

        System.out.println(rent_date);
        System.out.println(rent_time);
        System.out.println(return_date);
        System.out.println(return_time);
        System.out.println(day);
        System.out.println(time);


        return "paying";
    }


    // europe 캠핑카 가격 구하는 api
    @RequestMapping(value = "/campingcar/getprice", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_yearly_price(HttpServletResponse res) throws IOException {


        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName("europe");
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("deposit",campingCarPrice.getDeposit());
        jsonObject.put("yearmodel",campingCarPrice.getYearmodel());
        jsonObject.put("carName",campingCarPrice.getCarName());

//        jsonObject.put("campingCarPrice",campingCarPrice);


        jsonObject.put("oneday",campingCarPrice.getOnedays());
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
