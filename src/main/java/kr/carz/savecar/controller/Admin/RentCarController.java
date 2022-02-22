package kr.carz.savecar.controller.Admin;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.dto.CampingCarPriceRateDTO;
import kr.carz.savecar.dto.MonthlyRentDTO;
import kr.carz.savecar.service.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.Month;
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


    @GetMapping("/admin/rentcar/price/monthly/menu/{category2}")
    public ModelAndView get_rent_car_price_monthly_menu(@PathVariable String category2) {

        ModelAndView mav = new ModelAndView();

        List<MonthlyRent> monthlyRentList = monthlyRentService.findByCategory2(category2);

//        Collections.sort(monthlyRentList);

//        List<List<MonthlyRent>> monthlyRentListTotal = new ArrayList<>();
//        monthlyRentListTotal.add(monthlyRentService.findByCategory2("경형"));
//        monthlyRentListTotal.add(monthlyRentService.findByCategory2("준중형"));
//        monthlyRentListTotal.add(monthlyRentService.findByCategory2("중형"));
//        monthlyRentListTotal.add(monthlyRentService.findByCategory2("중대형"));
//        monthlyRentListTotal.add(monthlyRentService.findByCategory2("대형"));
//        monthlyRentListTotal.add(monthlyRentService.findByCategory2("소중형SUV"));
//        monthlyRentListTotal.add(monthlyRentService.findByCategory2("중형SUV"));
//        monthlyRentListTotal.add(monthlyRentService.findByCategory2("중대형SUV"));
//        monthlyRentListTotal.add(monthlyRentService.findByCategory2("대형SUV"));
//        monthlyRentListTotal.add(monthlyRentService.findByCategory2("승합"));
//        monthlyRentListTotal.add(monthlyRentService.findByCategory2("수입차"));
//
//        List<MonthlyRent> monthlyRentList = new ArrayList<>();
//        for(List<MonthlyRent> currentList : monthlyRentListTotal){
//            Collections.sort(currentList);
//            monthlyRentList.addAll(currentList);
//        }

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


    @PutMapping("/admin/rentcar/price/monthly/{monthlyId}")
    @ResponseBody
    public void put_rent_car_price_monthly(HttpServletResponse res, @RequestBody MonthlyRentDTO monthlyRentDTO, @PathVariable Long monthlyId) throws IOException {

        JSONObject jsonObject = new JSONObject();

        Optional<MonthlyRent> monthlyRentWrapper = monthlyRentService.findById(monthlyId);
        if(monthlyRentWrapper.isPresent()){

            monthlyRentService.updateAllPriceByDTO(monthlyRentDTO, monthlyRentWrapper.get());
            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }




    @GetMapping("/admin/rentcar/price/yearly/menu")
    public ModelAndView get_rent_car_price_yearly_menu() {

        ModelAndView mav = new ModelAndView();

        List<List<YearlyRent>> yearlyRentListTotal = new ArrayList<>();
        yearlyRentListTotal.add(yearlyRentService.findByCategory2("경형"));
        yearlyRentListTotal.add(yearlyRentService.findByCategory2("준중형"));
        yearlyRentListTotal.add(yearlyRentService.findByCategory2("중형"));
        yearlyRentListTotal.add(yearlyRentService.findByCategory2("중대형"));
        yearlyRentListTotal.add(yearlyRentService.findByCategory2("대형"));
        yearlyRentListTotal.add(yearlyRentService.findByCategory2("소중형SUV"));
        yearlyRentListTotal.add(yearlyRentService.findByCategory2("중형SUV"));
        yearlyRentListTotal.add(yearlyRentService.findByCategory2("중대형SUV"));
        yearlyRentListTotal.add(yearlyRentService.findByCategory2("대형SUV"));
        yearlyRentListTotal.add(yearlyRentService.findByCategory2("승합"));
        yearlyRentListTotal.add(yearlyRentService.findByCategory2("수입차"));

        List<YearlyRent> yearlyRentList = new ArrayList<>();
        for(List<YearlyRent> currentList : yearlyRentListTotal){
            Collections.sort(currentList);
            yearlyRentList.addAll(currentList);
        }

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

        List<List<TwoYearlyRent>> twoYearlyRentListTotal = new ArrayList<>();
        twoYearlyRentListTotal.add(twoYearlyRentService.findByCategory2("경형"));
        twoYearlyRentListTotal.add(twoYearlyRentService.findByCategory2("준중형"));
        twoYearlyRentListTotal.add(twoYearlyRentService.findByCategory2("중형"));
        twoYearlyRentListTotal.add(twoYearlyRentService.findByCategory2("중대형"));
        twoYearlyRentListTotal.add(twoYearlyRentService.findByCategory2("대형"));
        twoYearlyRentListTotal.add(twoYearlyRentService.findByCategory2("소중형SUV"));
        twoYearlyRentListTotal.add(twoYearlyRentService.findByCategory2("중형SUV"));
        twoYearlyRentListTotal.add(twoYearlyRentService.findByCategory2("중대형SUV"));
        twoYearlyRentListTotal.add(twoYearlyRentService.findByCategory2("대형SUV"));
        twoYearlyRentListTotal.add(twoYearlyRentService.findByCategory2("승합"));
        twoYearlyRentListTotal.add(twoYearlyRentService.findByCategory2("수입차"));

        List<TwoYearlyRent> twoYearlyRentList = new ArrayList<>();
        for(List<TwoYearlyRent> currentList : twoYearlyRentListTotal){
            Collections.sort(currentList);
            twoYearlyRentList.addAll(currentList);
        }

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
