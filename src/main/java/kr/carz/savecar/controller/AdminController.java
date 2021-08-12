package kr.carz.savecar.controller;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.*;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    @GetMapping("/admin/login")
    public String login(Model model) {

        return "login";
    }


    @GetMapping("/admin/main")
    public String admin(Model model) {

        List<CampingcarDateTime> campingcarDateTimeList = campingcarDateTimeService.findAllReservations();
        model.addAttribute("campingcarDateTimeList", campingcarDateTimeList);

        return "admin";
    }


    //로그인
    @RequestMapping(value = "/admin/logininfo", produces = "application/json; charset=UTF-8", method= RequestMethod.POST)
    @ResponseBody
    public ModelAndView post_login_info(HttpServletResponse res, HttpServletRequest req, @RequestBody Login login) throws IOException {

        // 아이디 비밀번호 맞는지 확인
        JSONArray jsonArray = new JSONArray();
        ModelAndView mav = new ModelAndView();

        try {
            Login user = loginService.findLoginByIdAndPwd(login.getId(), login.getPassword());
            System.out.println(user.getId());  // exception 발생코드임, 건들지 말기


            HttpSession session = req.getSession();
            session.setAttribute("user", user);


            System.out.println("true");
            jsonArray.put("true");


            // admin view로 넘기기
            List<CampingcarDateTime> campingcarDateTimeList = campingcarDateTimeService.findAllReservations();

            mav.addObject("campingcarDateTimeList",campingcarDateTimeList);
            mav.setViewName("admin");

        } catch (NullPointerException e){

            System.out.println("false");
            jsonArray.put("false");

            // 다시 login page로 back
            mav.setViewName("login");
        } finally {

            PrintWriter pw = res.getWriter();
            pw.print(jsonArray.toString());
            pw.flush();
            pw.close();
        }

        return mav;
    }
}
