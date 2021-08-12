package kr.carz.savecar.controller;

import kr.carz.savecar.domain.CampingCar;
import kr.carz.savecar.domain.MonthlyRent;
import kr.carz.savecar.domain.ShortRent;
import kr.carz.savecar.domain.YearlyRent;
import kr.carz.savecar.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {
    MonthlyRentService monthlyRentService;
    YearlyRentService yearlyRentService;
    ShortRentService shortRentService;
    CampingCarService campingCarService;
    CalendarDateService calendarDateService;
    DateCampingService dateCampingService;

    @Autowired
    public AdminController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService,
                           ShortRentService shortRentService, CampingCarService campingCarService, CalendarDateService calendarDateService,
                           DateCampingService dateCampingService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.shortRentService = shortRentService;
        this.campingCarService = campingCarService;
        this.calendarDateService = calendarDateService;
        this.dateCampingService = dateCampingService;
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }



}
