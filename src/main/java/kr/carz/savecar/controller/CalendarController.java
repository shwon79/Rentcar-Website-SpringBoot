package kr.carz.savecar.controller;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

    @Autowired
    public CalendarController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService,
                              ShortRentService shortRentService, CampingCarService campingCarService, CalendarDateService calendarDateService,
                              CalendarTimeService calendarTimeService, DateCampingService dateCampingService,
                              CampingCarPriceService campingCarPriceService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.shortRentService = shortRentService;
        this.campingCarService = campingCarService;
        this.calendarDateService = calendarDateService;
        this.calendarTimeService = calendarTimeService;
        this.dateCampingService = dateCampingService;
        this.campingCarPriceService = campingCarPriceService;
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




    @GetMapping("/europe_reserve")
    public String camping_europe_reserve(Model model) {
//        List<CalendarDate> calendarDateList = calendarDateService.findCalendarDate();
//        model.addAttribute("calendarDateList", calendarDateList);
//
//
//        List<List<DateCamping>> dateCampingList = new ArrayList();
//
//        for (int i=0; i<30; i++){
//            dateCampingList.add(dateCampingService.findByDateId(calendarDateList.get(i)));
//        }
//
//        model.addAttribute("dateCampingList", dateCampingList);

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


        return "camping_europe";
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



        return "camping_europe";
    }


}
