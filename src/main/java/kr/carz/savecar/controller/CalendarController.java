package kr.carz.savecar.controller;

import kr.carz.savecar.domain.CalendarDate;
import kr.carz.savecar.domain.MonthlyRent;
import kr.carz.savecar.domain.YearlyRent;
import kr.carz.savecar.service.*;
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
public class CalendarController {

    private final MonthlyRentService monthlyRentService;
    private final YearlyRentService yearlyRentService;
    private final CalendarTimeService calendarTimeService;
    private final CalendarDateService calendarDateService;
    private final CampingCarPriceService campingCarPriceService;



    @Autowired
    public CalendarController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService, CalendarTimeService calendarTimeService,
                              CalendarDateService calendarDateService, CampingCarPriceService campingCarPriceService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.calendarTimeService = calendarTimeService;
        this.calendarDateService = calendarDateService;
        this.campingCarPriceService = campingCarPriceService;
    }


    //차 분류api
    @RequestMapping(value = "/calendar", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_monthly_rent_category2(HttpServletResponse res) throws IOException {

        List<CalendarDate> dates = calendarDateService.findCalendarDate();

        List <String> dateList = new ArrayList();


        for (int i = 0; i < dates.size(); i++) {
            dateList.add(dates.get(i).getSeason());
        }

        JSONArray jsonArray = new JSONArray();

        for (String c : dateList) {
            jsonArray.put(c);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonArray.toString());
        pw.flush();
        pw.close();
    }
}
