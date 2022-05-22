package kr.carz.savecar.controller.ShortTermRentCar;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.MonthlyRentService;
import kr.carz.savecar.service.TwoYearlyRentService;
import kr.carz.savecar.service.YearlyRentService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
public class RentEstimateController {

    private final MonthlyRentService monthlyRentService;
    private final YearlyRentService yearlyRentService;
    private final TwoYearlyRentService twoYearlyRentService;

    @Autowired
    public RentEstimateController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService, TwoYearlyRentService twoYearlyRentService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.twoYearlyRentService = twoYearlyRentService;
    }


    /* ======================================================================================== */
    /*                             [이전 버전] 실시간 월렌트 견적내기                                    */
    /* ======================================================================================== */



    @GetMapping("/rent/estimate")
    public String rent_month() {
        return "rent_month/original";
    }

    @RequestMapping("/rent/estimate/categories/{category1}/{category2}")
    public String handleRequest(ModelMap model, @PathVariable("category1") String category1, @PathVariable("category2") String category2) {
        model.put("category1", category1);
        model.put("category2", category2);

        return "rent_month/original";
    }

    // 차종 api
    @RequestMapping(value = "/rent/estimate/{period}", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public void get_monthly_rent_category1(HttpServletResponse res, @PathVariable String period) throws IOException {

        HashSet<String> categoryList = new HashSet<>();

        switch (period) {
            case "rentMonth":
                List<MonthlyRent> monthlyRents = monthlyRentService.findAllMonthlyRents();

                for (MonthlyRent monthlyRent : monthlyRents) {
                    categoryList.add(monthlyRent.getCategory1());
                }
                break;
            case "rentYear":
                List<YearlyRent> yearlyRents = yearlyRentService.findAllYearlyRents();

                for (YearlyRent yearlyRent : yearlyRents) {
                    categoryList.add(yearlyRent.getCategory1());
                }
                break;
            case "rent2Year":
                List<TwoYearlyRent> twoYearlyRents = twoYearlyRentService.findAllTwoYearlyRents();

                for (TwoYearlyRent twoYearlyRent : twoYearlyRents) {
                    categoryList.add(twoYearlyRent.getCategory1());
                }
                break;
            default:
                throw new NullPointerException();
        }


        List<String> categoryHashToList = new ArrayList(categoryList);
        Collections.sort(categoryHashToList);

        JSONArray jsonArray = new JSONArray();
        for (String c : categoryHashToList) {
            jsonArray.put(c);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray);
        pw.flush();
        pw.close();
    }

    //차 분류api
    @RequestMapping(value = "/rent/estimate/{period}/{category1}", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public void get_monthly_rent_category2(HttpServletResponse res, @PathVariable String period, @PathVariable String category1) throws IOException {

        List<String> categoryList2 = new ArrayList();

        switch (period) {
            case "rentMonth":
                List<MonthlyRent> monthlyRents = monthlyRentService.findCategory2OfMonthlyRents(category1);

                for (MonthlyRent monthlyRent : monthlyRents) {
                    if (!categoryList2.contains(monthlyRent.getCategory2())) {
                        categoryList2.add(monthlyRent.getCategory2());
                    }
                }
                break;
            case "rentYear":
                List<YearlyRent> yearlyRents = yearlyRentService.findCategory2OfMonthlyRents(category1);

                for (YearlyRent yearlyRent : yearlyRents) {
                    if (!categoryList2.contains(yearlyRent.getCategory2())) {
                        categoryList2.add(yearlyRent.getCategory2());
                    }
                }
                break;
            case "rent2Year":
                List<TwoYearlyRent> twoYearlyRents = twoYearlyRentService.findByCategory1(category1);

                for (TwoYearlyRent twoYearlyRent : twoYearlyRents) {
                    if (!categoryList2.contains(twoYearlyRent.getCategory2())) {
                        categoryList2.add(twoYearlyRent.getCategory2());
                    }
                }
                break;
            default:
                throw new NullPointerException();
        }

        JSONArray jsonArray = new JSONArray();

        for (String c : categoryList2) {
            jsonArray.put(c);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray);
        pw.flush();
        pw.close();
    }

    // 차명 api
    @RequestMapping(value = "/rent/estimate/{period}/name/{category1}/{category2}", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
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
        pw.print(jsonArray);
        pw.flush();
        pw.close();
    }

    //가격 구하는 api
    @RequestMapping(value = "/rent/estimate/{period}/price/{carName}/{mileage}", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public void get_monthly_price(HttpServletResponse res, @PathVariable String period, @PathVariable String carName, @PathVariable String mileage) throws IOException {

        JSONArray jsonArray = new JSONArray();

        MonthlyRent rentCar = monthlyRentService.findPrice(carName);
        double costFor2k = rentCar.getCost_for_2k();
        double percentage3k = rentCar.getCost_for_3k();
        double percentage4k = rentCar.getCost_for_4k();

        if (period.equals("rentMonth")) {

            if (mileage.equals("2500")) {
                jsonArray.put(Math.round(costFor2k * rentCar.getCost_for_2_5k() / 1000) * 1000);
            } else if (mileage.equals("2000")) {
                jsonArray.put(costFor2k);
            } else if (mileage.equals("3000")) {
                jsonArray.put(Math.round(costFor2k * percentage3k / 1000) * 1000);
            } else if (mileage.equals("4000")) {
                jsonArray.put(Math.round(costFor2k * percentage4k / 1000) * 1000);
            } else if (mileage.equals("기타주행거리")) {
                jsonArray.put(rentCar.getCost_for_others());
            }

            jsonArray.put(rentCar.getDeposit());
            jsonArray.put(rentCar.getAge_limit());

        } else if (period.equals("rentYear")) {
            YearlyRent yearlyRent = yearlyRentService.findPrice(carName);

            if (mileage.equals("20000")) {
                jsonArray.put(Math.round(costFor2k * yearlyRent.getCost_for_20k() / 1000) * 1000);
            } else if (mileage.equals("30000")) {
                jsonArray.put(Math.round(Math.round(costFor2k * percentage3k / 1000) * 1000 * yearlyRent.getCost_for_30k() / 1000) * 1000);
            } else if (mileage.equals("40000")) {
                jsonArray.put(Math.round(Math.round(costFor2k * percentage4k / 1000) * 1000 * yearlyRent.getCost_for_40k() / 1000) * 1000);
            } else if (mileage.equals("기타주행거리")) {
                jsonArray.put(yearlyRent.getCost_for_others());
            }

            jsonArray.put(yearlyRent.getDeposit());
            jsonArray.put(yearlyRent.getAge_limit());

        } else if (period.equals("rent2Year")) {
            TwoYearlyRent twoYearlyRent = twoYearlyRentService.findPrice(carName);

            if (mileage.equals("20000")) {
                jsonArray.put(Math.round(costFor2k * twoYearlyRent.getCost_for_20Tk() / 1000) * 1000);
            } else if (mileage.equals("30000")) {
                jsonArray.put(Math.round(Math.round(costFor2k * percentage3k / 1000) * 1000 * twoYearlyRent.getCost_for_30Tk() / 1000) * 1000);
            } else if (mileage.equals("40000")) {
                jsonArray.put(Math.round(Math.round(costFor2k * percentage4k / 1000) * 1000 * twoYearlyRent.getCost_for_40Tk() / 1000) * 1000);
            } else if (mileage.equals("기타주행거리")) {
                jsonArray.put(twoYearlyRent.getCost_for_others());
            }

            jsonArray.put(twoYearlyRent.getDeposit());
            jsonArray.put(twoYearlyRent.getAge_limit());

        } else {
            throw new NullPointerException();
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray);
        pw.flush();
        pw.close();
    }


}