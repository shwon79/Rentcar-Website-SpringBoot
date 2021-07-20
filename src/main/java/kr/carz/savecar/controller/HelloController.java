package kr.carz.savecar.controller;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {
    MonthlyRentService monthlyRentService;
    YearlyRentService yearlyRentService;
    ShortRentService shortRentService;
    CampingCarService campingCarService;
    CalendarDateService calendarDateService;
    DateCampingService dateCampingService;

    @Autowired
    public HelloController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService,
                           ShortRentService shortRentService, CampingCarService campingCarService, CalendarDateService calendarDateService,
                           DateCampingService dateCampingService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.shortRentService = shortRentService;
        this.campingCarService = campingCarService;
        this.calendarDateService = calendarDateService;
        this.dateCampingService = dateCampingService;
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/rent/long_term")
    public String rent_long_term() {
        return "rent_long_term";
    }

    @GetMapping("/price/month")
    public String price_month(Model model) {

        List<MonthlyRent> monthlyRentList = monthlyRentService.findMonthlyRents();
        model.addAttribute("monthlyRentList", monthlyRentList);

        return "price_month";
    }


    @GetMapping("/price/long")
    public String price_long(Model model) {

        List<YearlyRent> yearlyRentList = yearlyRentService.findYearlyRents();
        model.addAttribute("yearlyRentList", yearlyRentList);

        return "price_long";
    }


    @GetMapping("/price/short")
    public String price_short(Model model) {

        List<ShortRent> shortRentList = shortRentService.findShortRents();
        List<ShortRent> shortRentListForeign = shortRentService.findShortRentsByCategory1("수입차");
        List<ShortRent> shortRentListNotForeign = shortRentService.findShortRentsByNotCategory1("수입차");

        model.addAttribute("shortRentList", shortRentList);
        model.addAttribute("shortRentListForeign", shortRentListForeign);
        model.addAttribute("shortRentListNotForeign", shortRentListNotForeign);

        return "price_short";
    }


    @GetMapping("/price/camp")
    public String price_camp(Model model) {

        List<CampingCar> campingCarList = campingCarService.findCampingCarRents();

        model.addAttribute("campingCarList", campingCarList);

        return "price_camp";
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
        List<CalendarDate> calendarDateList = calendarDateService.findCalendarDate();
        model.addAttribute("calendarDateList", calendarDateList);

        List<List<DateCamping>> dateCampingList = new ArrayList();

        System.out.println("여기여기");
        System.out.println(dateCampingService.findByDateId(calendarDateList.get(0)).get(0));

//        dateCampingListTest.add(dateCampingService.findByDateId("1").get(0));

        for (int i=0; i<30; i++){
            dateCampingList.add(dateCampingService.findByDateId(calendarDateList.get(i)));
        }

        System.out.println(dateCampingList.get(0).get(0).getCarName().getCarName());
        System.out.println(dateCampingList.get(0).get(0).getReserved());

        model.addAttribute("dateCampingList", dateCampingList);

        return "camping_europe";
    }

    @GetMapping("/europe_reserve")
    public String camping_europe_reserve() {
        return "camping_calendar";
    }



}
