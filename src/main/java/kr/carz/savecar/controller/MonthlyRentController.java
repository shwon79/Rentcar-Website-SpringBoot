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

    @RequestMapping(value = "/rent/month/rentMonth", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_monthly_rent_category1(HttpServletResponse res, HttpServletRequest req) throws IOException {

        List<MonthlyRent> monthlyRents = monthlyRentService.findMonthlyRents();

        List<String> categoryList = new ArrayList();

        for (int i = 0; i < monthlyRents.size(); i++) {
            categoryList.add(monthlyRents.get(i).getCategory1());
        }

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < categoryList.size(); i++) {
            jsonArray.put(categoryList.get(i));
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }
    @RequestMapping(value = "/rent/month/rentMonth/{category1}", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_monthly_rent_category2(HttpServletResponse res, @PathVariable String category1) throws IOException {

        List<MonthlyRent> monthlyRents = monthlyRentService.findCategory2OfMonthlyRents(category1);

        List<String> categoryList = new ArrayList();

        for (int i = 0; i < monthlyRents.size(); i++) {
            categoryList.add(monthlyRents.get(i).getCategory2());
        }

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < categoryList.size(); i++) {
            jsonArray.put(categoryList.get(i));
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }
    @RequestMapping(value = "/rent/month/rentMonth/{category1}/{category2}", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_monthly_rent_name(HttpServletResponse res, @PathVariable String category1, @PathVariable String category2) throws IOException {

        List<MonthlyRent> monthlyRents = monthlyRentService.findNameOfMonthlyRents(category1, category2);

        List<String> categoryList = new ArrayList();

        for (int i = 0; i < monthlyRents.size(); i++) {
            categoryList.add(monthlyRents.get(i).getName());
        }

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < categoryList.size(); i++) {
            jsonArray.put(categoryList.get(i));
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }
    @RequestMapping(value = "/rent/month/rentMonth/mileage", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_monthly_rent_mileage(HttpServletResponse res) throws IOException {

        Integer[] monthlyRents = {2000, 2500, 3000, 4000};
        List<String> categoryList = new ArrayList();

        for (int i = 0; i < 4; i++) {
            categoryList.add(monthlyRents[i].toString());
        }

        for (int i = 0; i < 3; i++) {
            categoryList.add(monthlyRents[i].toString());
        }

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < categoryList.size(); i++) {
            jsonArray.put(categoryList.get(i));
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }


    @RequestMapping(value = "/rent/month/rentYear", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_yearly_rent_category1(HttpServletResponse res, HttpServletRequest req) throws IOException {

        List<YearlyRent> yearlyRents = yearlyRentService.findYearlyRents();

        List<String> categoryList = new ArrayList();

        for (int i = 0; i < yearlyRents.size(); i++) {
            categoryList.add(yearlyRents.get(i).getCategory1());
        }

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < categoryList.size(); i++) {
            jsonArray.put(categoryList.get(i));
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }

    @RequestMapping(value = "/rent/month/rentYear/{category1}", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_yearly_rent_category2(HttpServletResponse res, @PathVariable String category1) throws IOException {

        List<YearlyRent> yearlyRents = yearlyRentService.findCategory2OfMonthlyRents(category1);
        List<String> categoryList = new ArrayList();

        for (int i = 0; i < yearlyRents.size(); i++) {
            categoryList.add(yearlyRents.get(i).getCategory2());
        }

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < categoryList.size(); i++) {
            jsonArray.put(categoryList.get(i));
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }

    @RequestMapping(value = "/rent/month/rentYear/{category1}/{category2}", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_yearly_rent_name(HttpServletResponse res, @PathVariable String category1, @PathVariable String category2) throws IOException {

        List<YearlyRent> yearlyRents = yearlyRentService.findNameOfYearlyRents(category1, category2);
        List<String> categoryList = new ArrayList();

        for (int i = 0; i < yearlyRents.size(); i++) {
            categoryList.add(yearlyRents.get(i).getName());
        }

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < categoryList.size(); i++) {
            jsonArray.put(categoryList.get(i));
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }

    @RequestMapping(value = "/rent/month/rentYear/mileage", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_yearly_rent_mileage(HttpServletResponse res) throws IOException {

        Integer[] yearlyRents = {2000, 3000, 4000};
        List<String> categoryList = new ArrayList();

        for (int i = 0; i < 3; i++) {
            categoryList.add(yearlyRents[i].toString());
        }

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < categoryList.size(); i++) {
            jsonArray.put(categoryList.get(i));
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }

}
