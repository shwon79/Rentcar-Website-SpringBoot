package kr.carz.savecar.controller.Admin;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.dto.CampingCarPriceRateDTO;
import kr.carz.savecar.dto.MonthlyRentDTO;
import kr.carz.savecar.dto.MonthlyRentVO;
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
    private final S3Service s3Service;

    @Autowired
    public RentCarController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService,
                             TwoYearlyRentService twoYearlyRentService, ReservationService reservationService,
                             S3Service s3Service) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.twoYearlyRentService = twoYearlyRentService;
        this.reservationService = reservationService;
        this.s3Service = s3Service;
    }


    @GetMapping("/admin/rentcar/price/monthly/menu/{category2}")
    public ModelAndView get_rent_car_price_monthly_menu(@PathVariable String category2) {

        ModelAndView mav = new ModelAndView();

        List<MonthlyRent> monthlyRentList = monthlyRentService.findByCategory2(category2);

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



    @PutMapping("/admin/rentcar/price/monthly/image/{monthlyId}")
    @ResponseBody
    public void put_rent_car_price_monthly_with_image(HttpServletResponse res, @RequestBody MonthlyRentVO monthlyRentVO, @PathVariable Long monthlyId) throws IOException {

        JSONObject jsonObject = new JSONObject();

        Optional<MonthlyRent> monthlyRentWrapper = monthlyRentService.findById(monthlyId);
        if(monthlyRentWrapper.isPresent()){

            String imgPath = s3Service.upload(monthlyRentVO.getFile());
            monthlyRentVO.setImg_url(imgPath);

            monthlyRentService.updateAllPriceByVO(monthlyRentVO, monthlyRentWrapper.get());
            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }




    @GetMapping("/admin/rentcar/price/yearly/menu/{category2}")
    public ModelAndView get_rent_car_price_yearly_menu(@PathVariable String category2) {

        ModelAndView mav = new ModelAndView();

        List<YearlyRent> yearlyRentList = yearlyRentService.findByCategory2(category2);

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

    @GetMapping("/admin/rentcar/price/twoYearly/menu/{category2}")
    public ModelAndView get_rent_car_price_twoYearly_menu(@PathVariable String category2) {

        ModelAndView mav = new ModelAndView();

        List<TwoYearlyRent> twoYearlyRentList = twoYearlyRentService.findByCategory2(category2);

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
