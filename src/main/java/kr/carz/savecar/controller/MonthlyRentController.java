package kr.carz.savecar.controller;

import kr.carz.savecar.domain.MonthlyRent;
import kr.carz.savecar.domain.TwoYearlyRent;
import kr.carz.savecar.domain.YearlyRent;
import kr.carz.savecar.service.MonthlyRentService;
import kr.carz.savecar.service.TwoYearlyRentService;
import kr.carz.savecar.service.YearlyRentService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Controller
public class MonthlyRentController {

    private final MonthlyRentService monthlyRentService;
    private final YearlyRentService yearlyRentService;
    private final TwoYearlyRentService twoYearlyRentService;

    @Autowired
    public MonthlyRentController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService, TwoYearlyRentService twoYearlyRentService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.twoYearlyRentService = twoYearlyRentService;
    }


    @GetMapping("/rent/month")
    public String rent_month() {
        return "rent_month";
    }

    @RequestMapping("/rent/month/{category1}/{category2}")
    public String handleRequest(ModelMap model, @PathVariable("category1") String category1, @PathVariable("category2") String category2) throws Exception {
        model.put("category1", category1);
        model.put("category2", category2);

        return "rent_month";
    }




    /* ======================================================================================== */
    /*                                          실시간 견적내기                                    */
    /* ======================================================================================== */


    // 차종 api
    @RequestMapping(value = "/rent/month/{period}", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public void get_monthly_rent_category1(HttpServletResponse res, HttpServletRequest req, @PathVariable String period) throws IOException {


        HashSet<String> categoryList = new HashSet<String>();

        if (period.equals("rentMonth")) {
            List<MonthlyRent> monthlyRents = monthlyRentService.findMonthlyRents();

            for (int i = 0; i < monthlyRents.size(); i++) {
                categoryList.add(monthlyRents.get(i).getCategory1());
            }
        } else if (period.equals("rentYear")) {
            List<YearlyRent> yearlyRents = yearlyRentService.findYearlyRents();

            for (int i = 0; i < yearlyRents.size(); i++) {
                categoryList.add(yearlyRents.get(i).getCategory1());
            }
        } else if (period.equals("rent2Year")) {
            List<TwoYearlyRent> twoYearlyRents = twoYearlyRentService.findTwoYearlyRents();

            for (int i = 0; i < twoYearlyRents.size(); i++) {
                categoryList.add(twoYearlyRents.get(i).getCategory1());
            }
        } else {
            throw new NullPointerException();
        }


        List<String> categoryHashToList = new ArrayList(categoryList);
        Collections.sort(categoryHashToList);

        JSONArray jsonArray = new JSONArray();
        for (String c : categoryHashToList) {
            jsonArray.put(c);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }

    //차 분류api
    @RequestMapping(value = "/rent/month/{period}/{category1}", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public void get_monthly_rent_category2(HttpServletResponse res, @PathVariable String period, @PathVariable String category1) throws IOException {

        List<String> categoryList2 = new ArrayList();


        if (period.equals("rentMonth")) {
            List<MonthlyRent> monthlyRents = monthlyRentService.findCategory2OfMonthlyRents(category1);

            for (int i = 0; i < monthlyRents.size(); i++) {
                if (!categoryList2.contains(monthlyRents.get(i).getCategory2())) {
                    categoryList2.add(monthlyRents.get(i).getCategory2());
                }
            }
        } else if (period.equals("rentYear")) {
            List<YearlyRent> yearlyRents = yearlyRentService.findCategory2OfMonthlyRents(category1);

            for (int i = 0; i < yearlyRents.size(); i++) {
                if (!categoryList2.contains(yearlyRents.get(i).getCategory2())) {
                    categoryList2.add(yearlyRents.get(i).getCategory2());
                }
            }
        } else if (period.equals("rent2Year")) {
            List<TwoYearlyRent> twoYearlyRents = twoYearlyRentService.findByCategory1(category1);

            for (int i = 0; i < twoYearlyRents.size(); i++) {
                if (!categoryList2.contains(twoYearlyRents.get(i).getCategory2())) {
                    categoryList2.add(twoYearlyRents.get(i).getCategory2());
                }
            }
        } else {
            throw new NullPointerException();
        }

        JSONArray jsonArray = new JSONArray();

        for (String c : categoryList2) {
            jsonArray.put(c);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }

    // 차명 api
    @RequestMapping(value = "/rent/month/{period}/name/{category1}/{category2}", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public void get_monthly_rent_name(HttpServletResponse res, @PathVariable String period, @PathVariable String category1, @PathVariable String category2) throws IOException {

        HashSet<String> categoryList = new HashSet<String>();


        if (period.equals("rentMonth")) {
            List<MonthlyRent> monthlyRents = monthlyRentService.findNameOfMonthlyRents(category1, category2);

            for (int i = 0; i < monthlyRents.size(); i++) {
                categoryList.add(monthlyRents.get(i).getName());
            }
        } else if (period.equals("rentYear")) {
            List<YearlyRent> yearlyRents = yearlyRentService.findNameOfYearlyRents(category1, category2);

            for (int i = 0; i < yearlyRents.size(); i++) {
                categoryList.add(yearlyRents.get(i).getName());
            }
        } else if (period.equals("rent2Year")) {
            List<TwoYearlyRent> twoYearlyRents = twoYearlyRentService.findNameOfTwoYearlyRents(category1, category2);

            for (int i = 0; i < twoYearlyRents.size(); i++) {
                categoryList.add(twoYearlyRents.get(i).getName());
            }
        } else {
            throw new NullPointerException();
        }


        List<String> categoryHashToList = new ArrayList(categoryList);
        Collections.sort(categoryHashToList);


        JSONArray jsonArray = new JSONArray();
        for (String c : categoryHashToList) {
            jsonArray.put(c);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }

    //가격 구하는 api
    @RequestMapping(value = "/rent/month/{period}/price/{carName}/{mileage}", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public void get_monthly_price(HttpServletResponse res, @PathVariable String period, @PathVariable String carName, @PathVariable String mileage) throws IOException {


        JSONArray jsonArray = new JSONArray();

        if (period.equals("rentMonth")) {
            MonthlyRent rentCar = monthlyRentService.findPrice(carName);

            if (mileage.equals("2500")) {
                jsonArray.put(rentCar.getCost_for_2_5k());
            } else if (mileage.equals("2000")) {
                jsonArray.put(rentCar.getCost_for_2k());
            } else if (mileage.equals("3000")) {
                jsonArray.put(rentCar.getCost_for_3k());
            } else if (mileage.equals("4000")) {
                jsonArray.put(rentCar.getCost_for_4k());
            } else if (mileage.equals("기타주행거리")) {
                jsonArray.put(rentCar.getCost_for_others());
            }

            jsonArray.put(rentCar.getDeposit());
            jsonArray.put(rentCar.getAge_limit());

        } else if (period.equals("rentYear")) {
            YearlyRent rentCar = yearlyRentService.findPrice(carName);

            if (mileage.equals("20000")) {
                jsonArray.put(rentCar.getCost_for_20k());
            } else if (mileage.equals("30000")) {
                jsonArray.put(rentCar.getCost_for_30k());
            } else if (mileage.equals("40000")) {
                jsonArray.put(rentCar.getCost_for_40k());
            } else if (mileage.equals("기타주행거리")) {
                jsonArray.put(rentCar.getCost_for_others());
            }

            jsonArray.put(rentCar.getDeposit());
            jsonArray.put(rentCar.getAge_limit());

        } else if (period.equals("rent2Year")) {
            TwoYearlyRent rentCar = twoYearlyRentService.findPrice(carName);

            if (mileage.equals("20000")) {
                jsonArray.put(rentCar.getCost_for_20Tk());
            } else if (mileage.equals("30000")) {
                jsonArray.put(rentCar.getCost_for_30Tk());
            } else if (mileage.equals("40000")) {
                jsonArray.put(rentCar.getCost_for_40Tk());
            } else if (mileage.equals("기타주행거리")) {
                jsonArray.put(rentCar.getCost_for_others());
            }

            jsonArray.put(rentCar.getDeposit());
            jsonArray.put(rentCar.getAge_limit());

        } else {
            throw new NullPointerException();
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }


}