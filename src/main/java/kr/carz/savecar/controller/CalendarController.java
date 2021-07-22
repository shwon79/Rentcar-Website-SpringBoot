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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

        List<CalendarDate> calendarDateList = calendarDateService.findCalendarDateByMonth(Integer.toString(cal.get(Calendar.MONTH) + 1));
        model.addAttribute("calendarDateList", calendarDateList);

        // 전달 날짜 구하기
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
        System.out.println(cal.get(Calendar.DAY_OF_WEEK));


        // 날짜별 캠핑카
        List<List<DateCamping>> dateCampingList = new ArrayList();

        for (int i=0; i<30; i++){
            dateCampingList.add(dateCampingService.findByDateId(calendarDateList.get(i)));
        }

        System.out.println(dateCampingList.get(0).get(0).getCarName());
        model.addAttribute("dateCampingList", dateCampingList);


        return "camping_europe";
    }

    @GetMapping("/europe_reserve")
    public String camping_europe_reserve(Model model) {
        List<CalendarDate> calendarDateList = calendarDateService.findCalendarDate();
        model.addAttribute("calendarDateList", calendarDateList);


        List<List<DateCamping>> dateCampingList = new ArrayList();

        for (int i=0; i<30; i++){
            dateCampingList.add(dateCampingService.findByDateId(calendarDateList.get(i)));
        }

        model.addAttribute("dateCampingList", dateCampingList);

        return "camping_calendar";
    }


    @RequestMapping("/europe_reserve/{date_id}")
    public String handleRequest(ModelMap model, @PathVariable("date_id") Long date_id) throws Exception {

        // 시간
        CalendarDate calendarDate = calendarDateService.findCalendarDateByDateId(date_id);
        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName("europe");

        List<CalendarTime> calendarTimeList = calendarTimeService.findCalendarTimeByDateIdAndCarName(calendarDate, campingCarPrice);

        model.addAttribute("calendarTimeList", calendarTimeList);


        // 날짜 달력
        List<CalendarDate> calendarDateList = calendarDateService.findCalendarDate();
        model.addAttribute("calendarDateList", calendarDateList);


        List<List<DateCamping>> dateCampingList = new ArrayList();

        for (int i=0; i<30; i++){
            dateCampingList.add(dateCampingService.findByDateId(calendarDateList.get(i)));
        }

        model.addAttribute("dateCampingList", dateCampingList);


        return "camping_calendar";
    }


}
