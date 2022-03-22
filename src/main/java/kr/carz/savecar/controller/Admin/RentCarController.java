package kr.carz.savecar.controller.Admin;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.dto.*;
import kr.carz.savecar.service.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
        Collections.sort(monthlyRentList);

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


    @GetMapping("/admin/rentcar/price/register")
    public ModelAndView get_rent_car_price_register() {

        ModelAndView mav = new ModelAndView();

        mav.setViewName("admin/rentcar_price_register");

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


    // 일괄수정
    @PutMapping("/admin/rentcar/price/monthly/{column}/{value}")
    @ResponseBody
    public void put_rent_car_price_monthly_kilometer_percentage(HttpServletResponse res, @PathVariable String column, @PathVariable double value) throws Exception {

        JSONObject jsonObject = new JSONObject();

        List<MonthlyRent> monthlyRentList = monthlyRentService.findAllMonthlyRents();

        switch (column){
            case "보증금":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.setDeposit(String.valueOf(value));
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "21세":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.setAge_limit(String.valueOf(value));
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "2500km":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.setCost_for_2_5k(value);
                    monthlyRent.setCost_for_2_5k_price(Math.round(monthlyRent.getCost_for_2k() * value / 1000) * 1000);
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "3000km":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.setCost_for_3k(value);
                    monthlyRent.setCost_for_3k_price(Math.round(monthlyRent.getCost_for_2k() * value / 1000) * 1000);
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "4000km":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.setCost_for_4k(value);
                    monthlyRent.setCost_for_4k_price(Math.round(monthlyRent.getCost_for_2k() * value / 1000) * 1000);
                    monthlyRentService.save(monthlyRent);
                }
                break;
            default:
                throw new Exception("column not mathced");
        }

        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    @PutMapping(value="/admin/rentcar/price/monthly/image/{monthlyId}", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public void put_rent_car_price_monthly_with_image(MonthlyRentVO monthlyRentVO, @PathVariable Long monthlyId) throws IOException {

        Optional<MonthlyRent> monthlyRentWrapper = monthlyRentService.findById(monthlyId);
        if(monthlyRentWrapper.isPresent()){

            String imgPath = s3Service.upload(monthlyRentVO.getFile());
            monthlyRentVO.setImg_url(imgPath);

            monthlyRentService.updateAllPriceByVO(monthlyRentVO, monthlyRentWrapper.get());
        }

    }


    @PostMapping(value="/admin/rentcar/price", consumes=MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public void post_rent_car_price(HttpServletResponse res, RentCarVO rentCarVO) throws IOException {

        JSONObject jsonObject = new JSONObject();

        String imgPath = s3Service.upload(rentCarVO.getFile());

        Long yearRentId = yearlyRentService.saveByRentCarVO(rentCarVO, imgPath);
        Optional<YearlyRent> yearlyRentWrapper = yearlyRentService.findByid(yearRentId);

        if(rentCarVO.getIsTwoYearExist() == 1) {
            Long twoYearRentId = twoYearlyRentService.saveByRentCarVO(rentCarVO, imgPath);
            Optional<TwoYearlyRent> twoYearlyRentWrapper = twoYearlyRentService.findByid(twoYearRentId);
            monthlyRentService.saveByRentCarVO(rentCarVO, yearlyRentWrapper.get(), twoYearlyRentWrapper.get(), imgPath);
        } else {
            monthlyRentService.saveByRentCarVO(rentCarVO, yearlyRentWrapper.get(), null, imgPath);
        }

        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    @DeleteMapping("/admin/rentcar/price/{monthlyId}")
    @ResponseBody
    public void delete_rent_car_price(HttpServletResponse res, @PathVariable Long monthlyId) throws IOException {

        JSONObject jsonObject = new JSONObject();

        Optional<MonthlyRent> monthlyRentWrapper = monthlyRentService.findById(monthlyId);

        if(monthlyRentWrapper.isPresent()) {
            MonthlyRent monthlyRent = monthlyRentWrapper.get();
            YearlyRent yearlyRent = monthlyRent.getYearlyRent();
            monthlyRentService.delete(monthlyRent);
            yearlyRentService.delete(yearlyRent);
            if(monthlyRent.getTwoYearlyRent() != null){
                TwoYearlyRent twoYearlyRent = monthlyRent.getTwoYearlyRent();
                twoYearlyRentService.delete(twoYearlyRent);
            }
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

        List<MonthlyRent> monthlyRentList = monthlyRentService.findByCategory2(category2);
        Collections.sort(monthlyRentList);

        mav.addObject("monthlyRentList", monthlyRentList);

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



    @PutMapping("/admin/rentcar/price/yearly/{yearlyId}")
    @ResponseBody
    public void put_rent_car_price_yearly(HttpServletResponse res, @RequestBody YearlyRentDTO yearlyRentDTO, @PathVariable Long yearlyId) throws IOException {

        JSONObject jsonObject = new JSONObject();

        Optional<YearlyRent> yearlyRentWrapper  = yearlyRentService.findById(yearlyId);
        if(yearlyRentWrapper.isPresent()){

            yearlyRentService.updateAllPriceByDTO(yearlyRentDTO, yearlyRentWrapper.get());

            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    @PutMapping("/admin/rentcar/price/yearly/{column}/{value}")
    @ResponseBody
    public void put_rent_car_price_yearly_kilometer_percentage(HttpServletResponse res, @PathVariable String column, @PathVariable double value) throws Exception {

        JSONObject jsonObject = new JSONObject();

        List<MonthlyRent> monthlyRentList = monthlyRentService.findAllMonthlyRents();

        switch (column){
            case "보증금":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.getYearlyRent().setDeposit(String.valueOf(value));
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "20000km":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.getYearlyRent().setCost_for_20k(value);
                    monthlyRent.getYearlyRent().setCost_for_20k_price(Math.round(monthlyRent.getCost_for_2k() * value / 1000) * 1000);
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "30000km":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.getYearlyRent().setCost_for_30k(value);
                    monthlyRent.getYearlyRent().setCost_for_30k_price(Math.round(Math.round(monthlyRent.getCost_for_2k() * monthlyRent.getCost_for_3k() / 1000) * 1000 * value / 1000) * 1000);
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "40000km":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.getYearlyRent().setCost_for_40k(value);
                    monthlyRent.getYearlyRent().setCost_for_40k_price(Math.round(Math.round(monthlyRent.getCost_for_2k() * monthlyRent.getCost_for_4k() / 1000) * 1000 * value / 1000) * 1000);
                    monthlyRentService.save(monthlyRent);
                }
                break;
            default:
                throw new Exception("column not mathced");
        }

        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }



    @GetMapping("/admin/rentcar/price/twoYearly/menu/{category2}")
    public ModelAndView get_rent_car_price_twoYearly_menu(@PathVariable String category2) {

        ModelAndView mav = new ModelAndView();

        List<MonthlyRent> monthlyRentList = monthlyRentService.findByCategory2AndTwoYearlyRentIsNotNull(category2);
        Collections.sort(monthlyRentList);

        mav.addObject("monthlyRentList", monthlyRentList);

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



    @PutMapping("/admin/rentcar/price/twoYearly/{twoYearlyId}")
    @ResponseBody
    public void put_rent_car_price_twoYearly(HttpServletResponse res, @RequestBody TwoYearlyRentDTO twoYearlyRentDTO, @PathVariable Long twoYearlyId) throws IOException {

        JSONObject jsonObject = new JSONObject();

        Optional<TwoYearlyRent> twoYearlyRentWrapper  = twoYearlyRentService.findById(twoYearlyId);
        if(twoYearlyRentWrapper.isPresent()){

            twoYearlyRentService.updateAllPriceByDTO(twoYearlyRentDTO, twoYearlyRentWrapper.get());

            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }



    @PutMapping("/admin/rentcar/price/twoYearly/{column}/{value}")
    @ResponseBody
    public void put_rent_car_price_twoYearly_kilometer_percentage(HttpServletResponse res, @PathVariable String column, @PathVariable double value) throws Exception {

        JSONObject jsonObject = new JSONObject();

        List<MonthlyRent> monthlyRentList = monthlyRentService.findAllByTwoYearlyRentIsNotNull();

        switch (column){
            case "보증금":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.getTwoYearlyRent().setDeposit(String.valueOf(value));
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "20000km":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.getTwoYearlyRent().setCost_for_20Tk(value);
                    monthlyRent.getTwoYearlyRent().setCost_for_20Tk_price(Math.round(monthlyRent.getCost_for_2k() * value / 1000) * 1000);
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "30000km":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.getTwoYearlyRent().setCost_for_30Tk(value);
                    monthlyRent.getTwoYearlyRent().setCost_for_30Tk_price(Math.round(Math.round(monthlyRent.getCost_for_2k() * monthlyRent.getCost_for_3k() / 1000) * 1000 * value / 1000) * 1000);
                    monthlyRentService.save(monthlyRent);
                }
                break;
            case "40000km":
                for (MonthlyRent monthlyRent : monthlyRentList) {
                    monthlyRent.getTwoYearlyRent().setCost_for_40Tk(value);
                    monthlyRent.getTwoYearlyRent().setCost_for_40Tk_price(Math.round(Math.round(monthlyRent.getCost_for_2k() * monthlyRent.getCost_for_4k() / 1000) * 1000 * value / 1000) * 1000);
                    monthlyRentService.save(monthlyRent);
                }
                break;
            default:
                throw new Exception("column not mathced");
        }

        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


}
