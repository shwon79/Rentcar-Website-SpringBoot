package kr.carz.savecar.controller;

import kr.carz.savecar.domain.MonthlyRent;
import kr.carz.savecar.domain.YearlyRent;
import kr.carz.savecar.service.MonthlyRentService;
import kr.carz.savecar.service.YearlyRentService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
public class MonthlyRentController {

    private final MonthlyRentService monthlyRentService;
    private final YearlyRentService yearlyRentService;

    @Autowired
    public MonthlyRentController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
    }


    @GetMapping("/rent/month")
    public String rent_month() {
        return "rent_month";
    }
//월렌트 차종 api
    @RequestMapping(value = "/rent/month/rentMonth", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_monthly_rent_category1(HttpServletResponse res, HttpServletRequest req) throws IOException {

        List<MonthlyRent> monthlyRents = monthlyRentService.findMonthlyRents();
        HashSet<String> categoryList = new HashSet<String>();

        for (int i = 0; i < monthlyRents.size(); i++) {
            categoryList.add(monthlyRents.get(i).getCategory1());
        }

        JSONArray jsonArray = new JSONArray();
        for (String c : categoryList) {
            jsonArray.put(c);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }
    //차 분류api
    @RequestMapping(value = "/rent/month/rentMonth/{category1}", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_monthly_rent_category2(HttpServletResponse res, @PathVariable String category1) throws IOException {

        List<MonthlyRent> monthlyRents = monthlyRentService.findCategory2OfMonthlyRents(category1);

        HashSet<String> categoryList = new HashSet<String>();

        for (int i = 0; i < monthlyRents.size(); i++) {
            categoryList.add(monthlyRents.get(i).getCategory2());
        }

        JSONArray jsonArray = new JSONArray();
        for (String c : categoryList) {
            jsonArray.put(c);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }
    // 차명 api
    @RequestMapping(value = "/rent/month/rentMonth/name/{category1}/{category2}", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_monthly_rent_name(HttpServletResponse res, @PathVariable String category1, @PathVariable String category2) throws IOException {

        List<MonthlyRent> monthlyRents = monthlyRentService.findNameOfMonthlyRents(category1, category2);

        HashSet<String> categoryList = new HashSet<String>();

        for (int i = 0; i < monthlyRents.size(); i++) {
            categoryList.add(monthlyRents.get(i).getName());
        }

        JSONArray jsonArray = new JSONArray();
        for (String c : categoryList) {
            jsonArray.put(c);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }

    //가격 구하는 api
    @RequestMapping(value = "/rent/month/rentMonth/price/{carName}/{mileage}", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_monthly_price(HttpServletResponse res, @PathVariable String carName, @PathVariable String mileage) throws IOException {

        MonthlyRent rentCar = monthlyRentService.findPrice(carName);
        JSONArray jsonArray = new JSONArray();
        if(mileage.equals("2500")) {
            jsonArray.put(rentCar.getCost_for_2_5k());
        } else if(mileage.equals("2000")) {
            jsonArray.put(rentCar.getCost_for_2k());
        } else if(mileage.equals("3000")) {
            jsonArray.put(rentCar.getCost_for_3k());
        } else if(mileage.equals("4000")) {
            jsonArray.put(rentCar.getCost_for_4k());
        } else if(mileage.equals("기타주행거리")) {
            jsonArray.put(rentCar.getCost_for_others());
        }

        jsonArray.put(rentCar.getDeposit());

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }


    //12개월 렌트 차종 api
    @RequestMapping(value = "/rent/month/rentYear", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_yearly_rent_category1(HttpServletResponse res, HttpServletRequest req) throws IOException {

        List<YearlyRent> yearlyRents = yearlyRentService.findYearlyRents();

        HashSet<String> categoryList = new HashSet<String>();

        for (int i = 0; i < yearlyRents.size(); i++) {
            categoryList.add(yearlyRents.get(i).getCategory1());
        }

        JSONArray jsonArray = new JSONArray();
        for (String c : categoryList) {
            jsonArray.put(c);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }

    //차 분류 api
    @RequestMapping(value = "/rent/month/rentYear/{category1}", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_yearly_rent_category2(HttpServletResponse res, @PathVariable String category1) throws IOException {

        List<YearlyRent> yearlyRents = yearlyRentService.findCategory2OfMonthlyRents(category1);

        HashSet<String> categoryList = new HashSet<String>();

        for (int i = 0; i < yearlyRents.size(); i++) {
            categoryList.add(yearlyRents.get(i).getCategory2());
        }

        JSONArray jsonArray = new JSONArray();
        for (String c : categoryList) {
            jsonArray.put(c);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }

    //차명 api
    @RequestMapping(value = "/rent/month/rentYear/name/{category1}/{category2}", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_yearly_rent_name(HttpServletResponse res, @PathVariable String category1, @PathVariable String category2) throws IOException {

        List<YearlyRent> yearlyRents = yearlyRentService.findNameOfYearlyRents(category1, category2);

        HashSet<String> categoryList = new HashSet<String>();

        for (int i = 0; i < yearlyRents.size(); i++) {
            categoryList.add(yearlyRents.get(i).getName());
        }

        JSONArray jsonArray = new JSONArray();
        for (String c : categoryList) {
            jsonArray.put(c);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }

    // 가격 구하는 api
    @RequestMapping(value = "/rent/month/rentYear/price/{carName}/{mileage}", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_yearly_price(HttpServletResponse res, @PathVariable String carName, @PathVariable String mileage) throws IOException {

        YearlyRent rentCar = yearlyRentService.findPrice(carName);

        JSONArray jsonArray = new JSONArray();
        if(mileage.equals("20000")) {
            jsonArray.put(rentCar.getCost_for_20k());
        } else if(mileage.equals("30000")) {
            jsonArray.put(rentCar.getCost_for_30k());
        } else if(mileage.equals("40000")) {
            jsonArray.put(rentCar.getCost_for_40k());
        } else if(mileage.equals("기타주행거리")) {
            jsonArray.put(rentCar.getCost_for_others());
        }

        jsonArray.put(rentCar.getDeposit());

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }

}
