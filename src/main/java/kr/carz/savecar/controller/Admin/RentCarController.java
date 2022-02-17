package kr.carz.savecar.controller.Admin;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    @GetMapping("/admin/rentcar/price/monthly/menu")
    public ModelAndView get_rent_car_price_monthly_menu() {

        ModelAndView mav = new ModelAndView();

        List<MonthlyRent> monthlyRentList = monthlyRentService.findAllMonthlyRents();

        mav.addObject("monthlyRentList", monthlyRentList);

        mav.setViewName("admin/rentcar_price_monthly_menu");

        return mav;
    }

    @GetMapping("/admin/rentcar/price/monthly/detail/{monthlyId}")
    public ModelAndView get_rent_car_price_monthly_detail(@PathVariable Long monthlyId) {

        ModelAndView mav = new ModelAndView();

        Optional<MonthlyRent> monthlyRentWrapper = monthlyRentService.findById(monthlyId);

        if(monthlyRentWrapper.isPresent()){
            mav.addObject("monthlyRent", monthlyRentWrapper.get());
        }

        mav.setViewName("admin/rentcar_price_monthly_detail");

        return mav;
    }



    @GetMapping("/admin/rentcar/price/yearly/menu")
    public ModelAndView get_rent_car_price_yearly_menu() {

        ModelAndView mav = new ModelAndView();

        List<YearlyRent> yearlyRentList = yearlyRentService.findAllYearlyRents();

        mav.addObject("yearlyRentList", yearlyRentList);

        mav.setViewName("admin/rentcar_price_yearly_menu");

        return mav;
    }


    @GetMapping("/admin/rentcar/price/yearly/detail/{yearlyId}")
    public ModelAndView get_rent_car_price_yearly_detail(@PathVariable Long yearlyId) {

        ModelAndView mav = new ModelAndView();

        Optional<YearlyRent> yearlyRentWrapper = yearlyRentService.findById(yearlyId);

        if(yearlyRentWrapper.isPresent()){
            mav.addObject("yearlyRent", yearlyRentWrapper.get());
        }

        mav.setViewName("admin/rentcar_price_yearly_detail");

        return mav;
    }

    @GetMapping("/admin/rentcar/price/twoYearly/menu")
    public ModelAndView get_rent_car_price_twoYearly_menu() {

        ModelAndView mav = new ModelAndView();

        List<TwoYearlyRent> twoYearlyRentList = twoYearlyRentService.findAllTwoYearlyRents();

        mav.addObject("twoYearlyRentList", twoYearlyRentList);

        mav.setViewName("admin/rentcar_price_twoYearly_menu");

        return mav;
    }

    @GetMapping("/admin/rentcar/price/twoYearly/detail/{twoYearlyId}")
    public ModelAndView get_rent_car_price_twoYearly_detail(@PathVariable Long twoYearlyId) {

        ModelAndView mav = new ModelAndView();

        Optional<TwoYearlyRent> twoYearlyRentWrapper = twoYearlyRentService.findById(twoYearlyId);

        if(twoYearlyRentWrapper.isPresent()){
            mav.addObject("twoYearlyRent", twoYearlyRentWrapper.get());
        }

        mav.setViewName("admin/rentcar_price_twoYearly_detail");

        return mav;
    }

    @GetMapping("/admin/rentcar/counsel/menu")
    public ModelAndView get_rent_car_counsel_menu(Pageable pageable) {

        ModelAndView mav = new ModelAndView();

        Page<Reservation> reservationPage = reservationService.findAllPageable(pageable);

        mav.addObject("currentPage", pageable.getPageNumber());
        mav.addObject("pageSize", pageable.getPageSize());

        mav.addObject("startPage", (pageable.getPageNumber() / 5) * 5 + 1);
        mav.addObject("endPage", Integer.min((pageable.getPageNumber() / 5 + 1) * 5, reservationPage.getTotalPages()));

        mav.addObject("totalPages", reservationPage.getTotalPages());
        mav.addObject("reservationList", reservationPage.getContent());

        mav.setViewName("admin/rentcar_counsel_menu");

        return mav;
    }


}
