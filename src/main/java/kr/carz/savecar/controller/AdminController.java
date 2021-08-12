package kr.carz.savecar.controller;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.*;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class AdminController {
    MonthlyRentService monthlyRentService;
    YearlyRentService yearlyRentService;
    ShortRentService shortRentService;
    CampingCarService campingCarService;
    CalendarDateService calendarDateService;
    DateCampingService dateCampingService;
    CampingcarDateTimeService campingcarDateTimeService;
    LoginService loginService;


    @Autowired
    public AdminController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService,
                           ShortRentService shortRentService, CampingCarService campingCarService, CalendarDateService calendarDateService,
                           DateCampingService dateCampingService, CampingcarDateTimeService campingcarDateTimeService,
                           LoginService loginService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.shortRentService = shortRentService;
        this.campingCarService = campingCarService;
        this.calendarDateService = calendarDateService;
        this.dateCampingService = dateCampingService;
        this.campingcarDateTimeService = campingcarDateTimeService;
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String login(Model model) {

        List<CampingcarDateTime> campingcarDateTimeList = campingcarDateTimeService.findAllReservations();
        model.addAttribute("campingcarDateTimeList", campingcarDateTimeList);

        return "login";
    }


    @GetMapping("/admin")
    public String admin(Model model) {

        List<CampingcarDateTime> campingcarDateTimeList = campingcarDateTimeService.findAllReservations();
        model.addAttribute("campingcarDateTimeList", campingcarDateTimeList);

        return "admin";
    }


    //로그인
    @RequestMapping(value = "/logininfo", produces = "application/json; charset=UTF-8", method= RequestMethod.POST)
    @ResponseBody
    public void post_login_info(HttpServletResponse res, @RequestBody Login login) throws IOException {

        JSONArray jsonArray = new JSONArray();

        try {
            Login test = loginService.findLoginById(login.getId());
            System.out.println(test.getId());

            System.out.println("true");
            jsonArray.put("true");

        } catch (NullPointerException e){

            System.out.println("false");
            jsonArray.put("false");

        } finally {

            PrintWriter pw = res.getWriter();
            pw.print(jsonArray.toString());
            pw.flush();
            pw.close();
        }
    }
}
