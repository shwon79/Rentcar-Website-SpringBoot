package kr.carz.savecar.controller;

import kr.carz.savecar.domain.CampingCar;
import kr.carz.savecar.domain.MonthlyRent;
import kr.carz.savecar.domain.ShortRent;
import kr.carz.savecar.domain.YearlyRent;
import kr.carz.savecar.service.CampingCarService;
import kr.carz.savecar.service.MonthlyRentService;
import kr.carz.savecar.service.ShortRentService;
import kr.carz.savecar.service.YearlyRentService;
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

    @Autowired
    public HelloController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService,
                           ShortRentService shortRentService, CampingCarService campingCarService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.shortRentService = shortRentService;
        this.campingCarService = campingCarService;

    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/rent/long_term")
    public String rent_long_term() {
        return "rent_long_term";
    }

    @GetMapping("/price")
    public String price(Model model) {
        List<MonthlyRent> monthlyRentList = monthlyRentService.findMonthlyRents();
        List<YearlyRent> yearlyRentList = yearlyRentService.findYearlyRents();
        List<ShortRent> shortRentList = shortRentService.findShortRents();
        List<CampingCar> campingCarList = campingCarService.findCampingCarRents();

        model.addAttribute("monthlyRentList", monthlyRentList);
        model.addAttribute("yearlyRentList", yearlyRentList);
        model.addAttribute("shortRentList", shortRentList);
        model.addAttribute("campingCarList", campingCarList);

        return "price";
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
    public String camping_europe() {
        return "camping_europe";
    }


}
