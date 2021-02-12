package kr.carz.savecar.controller;

import com.mysql.cj.xdevapi.JsonArray;
import kr.carz.savecar.domain.MonthlyRent;
import kr.carz.savecar.service.MonthlyRentService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
public class MonthlyController {
    private final MonthlyRentService monthlyRentService;

    @Autowired
    public MonthlyController(MonthlyRentService monthlyRentService) {
        this.monthlyRentService = monthlyRentService;
    }

    @GetMapping("/monthly")
    public String monthly_rent() {
        return "rent_month";
    }

    @RequestMapping(value = "/monthly/rentMonth", produces = "application/json; charset=UTF-8", method= RequestMethod.POST)
    @ResponseBody
    public void get_monthly_rent(HttpServletResponse res) throws IOException {

        List<MonthlyRent> monthlyRents = monthlyRentService.findMonthlyRents();
        System.out.println(monthlyRents);

        List<String> categoryList = new ArrayList();

        for (int i = 0; i < monthlyRents.size(); i++) {
            categoryList.add(monthlyRents.get(i).getCategory1());
        }

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < categoryList.size(); i++) {
            jsonArray.put(categoryList.get(i));
        }

        System.out.println(jsonArray.toString());
        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }

}
