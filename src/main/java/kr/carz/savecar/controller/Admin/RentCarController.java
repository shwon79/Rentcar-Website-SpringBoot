package kr.carz.savecar.controller.Admin;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class RentCarController {
    private final ReservationService reservationService;
    private final MonthlyRentService monthlyRentService;
    private final YearlyRentService yearlyRentService;
    private final TwoYearlyRentService twoYearlyRentService;

    @Autowired
    public RentCarController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService,
                             TwoYearlyRentService twoYearlyRentService, ReservationService reservationService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.twoYearlyRentService = twoYearlyRentService;
        this.reservationService = reservationService;
    }


    @GetMapping("/admin/rentcar/price/menu")
    public ModelAndView get_rent_car_price_menu() {

        ModelAndView mav = new ModelAndView();

        List<MonthlyRent> monthlyRentList = monthlyRentService.findAllMonthlyRents();
        List<YearlyRent> yearlyRentList = yearlyRentService.findAllYearlyRents();
        List<TwoYearlyRent> twoYearlyRentList = twoYearlyRentService.findAllTwoYearlyRents();

        mav.addObject("monthlyRentList", monthlyRentList);
        mav.addObject("yearlyRentList", yearlyRentList);
        mav.addObject("twoYearlyRentList", twoYearlyRentList);

        mav.setViewName("admin/rentcar_price_menu");

        return mav;
    }

    @GetMapping("/admin/rentcar/counsel/menu")
    public ModelAndView get_rent_car_counsel_menu() {

        ModelAndView mav = new ModelAndView();

        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = currentDateTime.minusDays(15);

        List<Reservation> reservationList = reservationService.findByCreatedDateAfter(startDateTime);
        mav.addObject("reservationList", reservationList);

        mav.setViewName("admin/rentcar_counsel_menu");

        return mav;
    }


}
