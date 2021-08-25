package kr.carz.savecar.controller;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.*;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
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



    @GetMapping("/camping/price")
    public String camping_price() {
        return "camping_price";
    }

    @GetMapping("/test")
    public String camping_europe_test(Model model) {
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

        model.addAttribute("dateCampingList", dateCampingList);
        model.addAttribute("thisMonth", now_month);
        model.addAttribute("thisYear", now_year);

        return "calendar";
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

            model.addAttribute("dateCampingList", dateCampingList);
            model.addAttribute("prevMonth", now_month-1);
            model.addAttribute("thisMonth", now_month);
            model.addAttribute("thisYear", 2021);
        }


        return "calendar";
    }


    @GetMapping("/travel_reserve/{date_id}")
    public String handleRequest_travel(ModelMap model, @PathVariable("date_id") Long date_id) throws Exception {

        // 클릭한 날짜 데이터
        CalendarDate clickedDate = calendarDateService.findCalendarDateByDateId(date_id);

        model.addAttribute("clickedDay", clickedDate.getDay());
        model.addAttribute("clickedMonth", clickedDate.getMonth());
        model.addAttribute("clickedYear", clickedDate.getYear());
        model.addAttribute("clickedWDay", clickedDate.getWDay());

        // 날짜
        Calendar cal = Calendar.getInstance();

        int today = cal.get(Calendar.DAY_OF_MONTH) + 1;
        int this_month = cal.get(Calendar.MONTH) + 1;

        model.addAttribute("today", today);
        model.addAttribute("thisMonth", this_month);


        // 시간
        CalendarDate calendarDate = calendarDateService.findCalendarDateByDateId(date_id);
        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName("travel");

        List<CalendarTime> calendarTimeList = calendarTimeService.findCalendarTimeByDateIdAndCarName(calendarDate, campingCarPrice);

        model.addAttribute("calendarTimeList", calendarTimeList);

        for(int i=0; i<calendarTimeList.size(); i++){
            System.out.println(calendarTimeList.get(i).getTimeId());
        }


        // 날짜
        int now_month = Integer.parseInt(clickedDate.getMonth()); // 클릭한 데이터

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

        model.addAttribute("dateCampingList", dateCampingList);


        // 캠핑카 가격
        model.put("campingCarPrice", campingCarPrice);  // 리스트 => 도메인 변수랑 이름 똑같이 해서 쓸 수 있음
        model.addAttribute("thisMonth", this_month);
        model.addAttribute("thisYear", 2021);

        model.addAttribute("dateId", date_id);

        return "camping_travel";
    }

    @GetMapping("/liomousine_reserve/{date_id}")
    public String handleRequest_liomousine(ModelMap model, @PathVariable("date_id") Long date_id) throws Exception {

        // 클릭한 날짜 데이터
        CalendarDate clickedDate = calendarDateService.findCalendarDateByDateId(date_id);

        model.addAttribute("clickedDay", clickedDate.getDay());
        model.addAttribute("clickedMonth", clickedDate.getMonth());
        model.addAttribute("clickedYear", clickedDate.getYear());
        model.addAttribute("clickedWDay", clickedDate.getWDay());

        // 날짜
        Calendar cal = Calendar.getInstance();

        int today = cal.get(Calendar.DAY_OF_MONTH) + 1;
        int this_month = cal.get(Calendar.MONTH) + 1;

        model.addAttribute("today", today);
        model.addAttribute("thisMonth", this_month);


        // 시간
        CalendarDate calendarDate = calendarDateService.findCalendarDateByDateId(date_id);
        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName("limousine");

        List<CalendarTime> calendarTimeList = calendarTimeService.findCalendarTimeByDateIdAndCarName(calendarDate, campingCarPrice);

        model.addAttribute("calendarTimeList", calendarTimeList);

        for(int i=0; i<calendarTimeList.size(); i++){
            System.out.println(calendarTimeList.get(i).getTimeId());
        }


        // 날짜
        int now_month = Integer.parseInt(clickedDate.getMonth()); // 클릭한 데이터

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

        model.addAttribute("dateCampingList", dateCampingList);


        // 캠핑카 가격
        model.put("campingCarPrice", campingCarPrice);  // 리스트 => 도메인 변수랑 이름 똑같이 해서 쓸 수 있음
        model.addAttribute("thisMonth", this_month);
        model.addAttribute("thisYear", 2021);

        model.addAttribute("dateId", date_id);

        return "camping_liomousine";
    }




    @RequestMapping("/europe_reserve/{date_id}")
    public String handleRequest(ModelMap model, @PathVariable("date_id") Long date_id) throws Exception {

        // 클릭한 날짜 데이터
        CalendarDate clickedDate = calendarDateService.findCalendarDateByDateId(date_id);

        model.addAttribute("clickedDay", clickedDate.getDay());
        model.addAttribute("clickedMonth", clickedDate.getMonth());
        model.addAttribute("clickedYear", clickedDate.getYear());
        model.addAttribute("clickedWDay", clickedDate.getWDay());

        // 날짜
        Calendar cal = Calendar.getInstance();

        int today = cal.get(Calendar.DAY_OF_MONTH) + 1;
        int this_month = cal.get(Calendar.MONTH) + 1;

        model.addAttribute("today", today);
        model.addAttribute("thisMonth", this_month);


        // 시간
        CalendarDate calendarDate = calendarDateService.findCalendarDateByDateId(date_id);
        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName("europe");

        List<CalendarTime> calendarTimeList = calendarTimeService.findCalendarTimeByDateIdAndCarName(calendarDate, campingCarPrice);

        model.addAttribute("calendarTimeList", calendarTimeList);

        for(int i=0; i<calendarTimeList.size(); i++){
            System.out.println(calendarTimeList.get(i).getTimeId());
        }


        // 날짜
        int now_month = Integer.parseInt(clickedDate.getMonth()); // 클릭한 데이터

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

        model.addAttribute("dateCampingList", dateCampingList);


        // 캠핑카 가격
        model.put("campingCarPrice", campingCarPrice);  // 리스트 => 도메인 변수랑 이름 똑같이 해서 쓸 수 있음
        model.addAttribute("thisMonth", this_month);
        model.addAttribute("thisYear", 2021);


        model.addAttribute("dateId", date_id);


        return "camping_europe";
    }



    @RequestMapping("/travel_reserve/after/{month}")
    public String handleRequest_travel_after(HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable("month") Long month) throws Exception {


        // 날짜
        Calendar cal = Calendar.getInstance();
        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName("travel");


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

        System.out.println("통과 0");

        if(now_month > 11){
            response.setContentType("text/html; charset=UTF-8");

            PrintWriter out = response.getWriter();

            out.println("<script>alert('이용할 수 없는 예약일자입니다.'); location.href='/travel_reserve/after/10';</script>");

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

            model.addAttribute("clickedYear", "2021");
            model.addAttribute("clickedMonth", now_month);
            model.addAttribute("clickedPrevMonth",now_month-1);

        }

        return "camping_travel";
    }




    @RequestMapping("/liomousine_reserve/after/{month}")
    public String handleRequest_liomousine_after(HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable("month") Long month) throws Exception {
        // 날짜
        Calendar cal = Calendar.getInstance();
        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName("limousine");


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

            out.println("<script>alert('이용할 수 없는 예약일자입니다.'); location.href='/liomousine_reserve/after/10';</script>");

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

            model.addAttribute("daylast", calendarDateList);
            model.addAttribute("calendarDateList", calendarDateList);


            // 날짜별 캠핑카
            List<List<DateCamping>> dateCampingList = new ArrayList();

            for (int i = 0; i < calendarDateList.size(); i++) {
                dateCampingList.add(dateCampingService.findByDateId(calendarDateList.get(i)));
            }

            System.out.println("calendarDateList 사이즈 : "+calendarDateList.size());

            System.out.println("dateCampingList 사이즈 : "+dateCampingList.size());


            for (int i = 0; i < dateCampingList.size(); i++) {

                System.out.println(dateCampingList.get(i).get(0).getReserved());
                System.out.println(dateCampingList.get(i).get(1).getReserved());
            }

            model.addAttribute("dateCampingList", dateCampingList);

            // 캠핑카 가격
            model.put("campingCarPrice", campingCarPrice);  // 리스트 => 도메인 변수랑 이름 똑같이 해서 쓸 수 있음

            model.addAttribute("prevMonth", now_month-1);
            model.addAttribute("thisMonth", now_month);
            model.addAttribute("thisYear", 2021);

            model.addAttribute("clickedYear", "2021");
            model.addAttribute("clickedMonth", now_month);
            model.addAttribute("clickedPrevMonth",now_month-1);

        }

        return "camping_liomousine";
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


            model.addAttribute("clickedYear", "2021");
            model.addAttribute("clickedMonth", now_month);
            model.addAttribute("clickedPrevMonth",now_month-1);

        }

        return "camping_europe";
    }



    @RequestMapping("/travel_reserve/before/{month}")
    public String handleRequest_travel_before(HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable("month") Long month) throws Exception {
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

            out.println("<script>alert('이용할 수 없는 예약일자입니다.'); location.href='/travel_reserve/before/8';</script>");

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
            model.addAttribute("prevMonth", now_month-1);
            model.addAttribute("thisMonth", now_month);
            model.addAttribute("thisYear", 2021);


            model.addAttribute("clickedYear", "2021");
            model.addAttribute("clickedMonth", now_month);
            model.addAttribute("clickedPrevMonth",now_month-1);
        }


        return "camping_travel";
    }



    @RequestMapping("/liomousine_reserve/before/{month}")
    public String handleRequest_liomousine_before(HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable("month") Long month) throws Exception {
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

            out.println("<script>alert('이용할 수 없는 예약일자입니다.'); location.href='/liomousine_reserve/before/8';</script>");

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
            model.addAttribute("prevMonth", now_month-1);
            model.addAttribute("thisMonth", now_month);
            model.addAttribute("thisYear", 2021);


            model.addAttribute("clickedYear", "2021");
            model.addAttribute("clickedMonth", now_month);
            model.addAttribute("clickedPrevMonth",now_month-1);
        }


        return "camping_liomousine";
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

            model.addAttribute("dateCampingList", dateCampingList);
            model.addAttribute("prevMonth", now_month-1);
            model.addAttribute("thisMonth", now_month);
            model.addAttribute("thisYear", 2021);


            model.addAttribute("clickedYear", "2021");
            model.addAttribute("clickedMonth", now_month);
            model.addAttribute("clickedPrevMonth",now_month-1);
        }


        return "camping_europe";
    }



    // 캠핑카 예약 저장 api
    @RequestMapping(value = "/travel/sendrentdate/{year}/{month}/{day}", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void send_rent_date_travel(HttpServletResponse res, @PathVariable String year, @PathVariable String month, @PathVariable String day) throws IOException {

        System.out.println(year+ month+ day);

        CalendarDate calendarDate = calendarDateService.findCalendarDateByMonthAndDayAndYear(month, day, year);


        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName("travel");

        List<CalendarTime> calendarTimeList = calendarTimeService.findCalendarTimeByDateIdAndCarName(calendarDate,campingCarPrice);


        // list
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


    // 캠핑카 예약 저장 api
    @RequestMapping(value = "/liomousine/sendrentdate/{year}/{month}/{day}", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void send_rent_date_liomousine(HttpServletResponse res, @PathVariable String year, @PathVariable String month, @PathVariable String day) throws IOException {

        System.out.println(year+ month+ day);

        CalendarDate calendarDate = calendarDateService.findCalendarDateByMonthAndDayAndYear(month, day, year);


        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName("limousine");

        List<CalendarTime> calendarTimeList = calendarTimeService.findCalendarTimeByDateIdAndCarName(calendarDate,campingCarPrice);


        // list
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


    // 캠핑카 예약 저장 api
    @RequestMapping(value = "/europe/sendrentdate/{year}/{month}/{day}", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void send_rent_date(HttpServletResponse res, @PathVariable String year, @PathVariable String month, @PathVariable String day) throws IOException {

        System.out.println(year+ month+ day);

        CalendarDate calendarDate = calendarDateService.findCalendarDateByMonthAndDayAndYear(month, day, year);


        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName("europe");

        List<CalendarTime> calendarTimeList = calendarTimeService.findCalendarTimeByDateIdAndCarName(calendarDate,campingCarPrice);


        // list
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



    @RequestMapping("/travel_reserve/{rent_date}/{rent_time}/{return_date}/{return_time}/{day}/{extraTime}/{total}")
    public String handleRequest_travel_reserve(ModelMap model, @PathVariable("rent_date") String rent_date, @PathVariable("rent_time") String rent_time, @PathVariable("return_date") String return_date, @PathVariable("return_time") String return_time, @PathVariable("day") String day, @PathVariable("extraTime") String extraTime, @PathVariable("total") String total) throws Exception {

        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName("travel");
        model.put("campingCarPrice", campingCarPrice);  // 리스트 => 도메인 변수랑 이름 똑같이 해서 쓸 수 있음

        model.put("rent_date", rent_date);
        model.put("rent_time", rent_time);
        model.put("return_date", return_date);
        model.put("return_time", return_time);
        model.put("day", day);
        model.put("extraTime", extraTime);
        model.put("total", total);

        model.put("carType", "travel");

        System.out.println(rent_date);
        System.out.println(rent_time);
        System.out.println(return_date);
        System.out.println(return_time);
        System.out.println(day);
        System.out.println(extraTime);
        System.out.println(total);


        return "paying";
    }



    @RequestMapping("/liomousine_reserve/{rent_date}/{rent_time}/{return_date}/{return_time}/{day}/{extraTime}/{total}")
    public String handleRequest_liomousine_reserve(ModelMap model, @PathVariable("rent_date") String rent_date, @PathVariable("rent_time") String rent_time, @PathVariable("return_date") String return_date, @PathVariable("return_time") String return_time, @PathVariable("day") String day, @PathVariable("extraTime") String extraTime, @PathVariable("total") String total) throws Exception {

        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName("limousine");
        model.put("campingCarPrice", campingCarPrice);  // 리스트 => 도메인 변수랑 이름 똑같이 해서 쓸 수 있음

        model.put("rent_date", rent_date);
        model.put("rent_time", rent_time);
        model.put("return_date", return_date);
        model.put("return_time", return_time);
        model.put("day", day);
        model.put("extraTime", extraTime);
        model.put("total", total);


        model.put("carType", "limousine");

        System.out.println(rent_date);
        System.out.println(rent_time);
        System.out.println(return_date);
        System.out.println(return_time);
        System.out.println(day);
        System.out.println(extraTime);
        System.out.println(total);


        return "paying";
    }


    @RequestMapping("/europe_reserve/{rent_date}/{rent_time}/{return_date}/{return_time}/{day}/{extraTime}/{total}")
    public String handleRequest(ModelMap model, @PathVariable("rent_date") String rent_date, @PathVariable("rent_time") String rent_time, @PathVariable("return_date") String return_date, @PathVariable("return_time") String return_time, @PathVariable("day") String day, @PathVariable("extraTime") String extraTime, @PathVariable("total") String total) throws Exception {

        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName("europe");
        model.put("campingCarPrice", campingCarPrice);  // 리스트 => 도메인 변수랑 이름 똑같이 해서 쓸 수 있음

        model.put("rent_date", rent_date);
        model.put("rent_time", rent_time);
        model.put("return_date", return_date);
        model.put("return_time", return_time);
        model.put("day", day);
        model.put("extraTime", extraTime);
        model.put("total", total);

        model.put("carType", "europe");


        System.out.println(rent_date);
        System.out.println(rent_time);
        System.out.println(return_date);
        System.out.println(return_time);
        System.out.println(day);
        System.out.println(extraTime);
        System.out.println(total);


        return "paying";
    }


    // europe 캠핑카 가격 구하는 api
    @RequestMapping(value = "/{carType}/getprice/{season}", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_travel_price(HttpServletResponse res, @PathVariable String carType, @PathVariable String season) throws IOException {

        if(season.equals("0")){
            CampingCarPrice campingCarPrice;

            if(carType.equals("liomousine")){

                campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName("limousine");

            } else {
                campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(carType);
            }

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

            if(carType.equals("liomousine")){

                campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName("limousine_peak");

            } else {
                campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(carType+"_peak");
            }

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
    @RequestMapping(value = "/{carType}/getrentdate/{year}/{month}/{day}", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_rent_date(HttpServletResponse res, @PathVariable String carType, @PathVariable String year, @PathVariable String month, @PathVariable String day) throws IOException {

        CalendarDate calendarDate = calendarDateService.findCalendarDateByMonthAndDayAndYear(month, day, year);
        CampingCarPrice campingCarPrice;

        if(carType.equals("liomousine")){
            campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName("limousine");

        } else {
            campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(carType);
        }


        Long date_start_id = calendarDate.getDateId();

        int possible_rent_date = 1;

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
    @RequestMapping(value = "/{carType}/getextratime/{year}/{month}/{day}/{rentDays}/{rentStartTime}", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_extra_time(HttpServletResponse res, @PathVariable String carType, @PathVariable String year, @PathVariable String month, @PathVariable String day, @PathVariable Integer rentDays, @PathVariable String rentStartTime) throws IOException {

        CalendarDate calendarDate = calendarDateService.findCalendarDateByMonthAndDayAndYear(month, day, year);
        CampingCarPrice campingCarPrice;

        if(carType.equals("liomousine")){
            campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName("limousine");

        } else {
            campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(carType);
        }



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

        if (!calendarTimeList.get(i).getReserveTime().equals("18시") && extraTime >= 2){
            extraTime -= 2;
        }


        JSONArray jsonArray = new JSONArray();
        jsonArray.put(extraTime);


        PrintWriter pw = res.getWriter();
        pw.print(jsonArray);
        pw.flush();
        pw.close();
    }



}
