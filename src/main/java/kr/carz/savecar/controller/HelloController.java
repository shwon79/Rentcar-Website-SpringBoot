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
public class HelloController {
    MonthlyRentService monthlyRentService;
    YearlyRentService yearlyRentService;
    ShortRentService shortRentService;
    CampingCarService campingCarService;
    CampingCarVariableService campingCarVariableService;

    @Autowired
    public HelloController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService,
                           ShortRentService shortRentService, CampingCarService campingCarService, CampingCarVariableService campingCarVariableService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.shortRentService = shortRentService;
        this.campingCarService = campingCarService;
        this.campingCarVariableService = campingCarVariableService;

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


        List<MonthlyRent> monthlyRentList = monthlyRentService.findMonthlyRents();
        model.addAttribute("monthlyRentList", monthlyRentList);

        return "camping_europe";
    }



}
