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
import java.util.*;

@Controller
public class AdminController {
    MonthlyRentService monthlyRentService;
    YearlyRentService yearlyRentService;
    ShortRentService shortRentService;
    CampingCarService campingCarService;
    CalendarDateService calendarDateService;
    DateCampingService dateCampingService;
    CampingcarDateTimeService2 campingcarDateTimeService2;
    LoginService loginService;
    CampingCarPriceService campingCarPriceService;


    @Autowired
    public AdminController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService,
                           ShortRentService shortRentService, CampingCarService campingCarService, CalendarDateService calendarDateService,
                           DateCampingService dateCampingService, CampingcarDateTimeService2 campingcarDateTimeService2,
                           LoginService loginService, CampingCarPriceService campingCarPriceService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.shortRentService = shortRentService;
        this.campingCarService = campingCarService;
        this.calendarDateService = calendarDateService;
        this.dateCampingService = dateCampingService;
        this.campingcarDateTimeService2 = campingcarDateTimeService2;
        this.loginService = loginService;
        this.campingCarPriceService = campingCarPriceService;
    }

    @GetMapping("/admin/login")
    public String login(Model model) {

        return "login";
    }


    @GetMapping("/admin/detail/{date_time_id}")
    public String get_admin_detail(Model model,  @PathVariable String date_time_id) throws Exception {

        CampingcarDateTime2 campingcarDateTime2 = campingcarDateTimeService2.findByDateTimeId(Long.parseLong(date_time_id));
        model.addAttribute("campingcarDateTime2",campingcarDateTime2);
        System.out.println(campingcarDateTime2.getDateTimeId());

        return "admin_detail";
    }


    //로그인
    @RequestMapping(value = "/admin/logininfo", method= RequestMethod.POST)
    @ResponseBody
    public ModelAndView post_login_info(HttpServletResponse res, HttpServletRequest req) throws IOException {

        ModelAndView mav = new ModelAndView();

        try {
            Login user = loginService.findLoginByIdAndPwd(req.getParameter("id"), req.getParameter("pwd"));
            System.out.println(user.getId());  // exception 발생코드임, 건들지 말기


            HttpSession session = req.getSession();
            session.setAttribute("user", user);


            System.out.println("true");


            // admin view로 넘기기
            List<CampingcarDateTime2> campingcarDateTimeList = campingcarDateTimeService2.findAllReservations();

            mav.addObject("campingcarDateTimeList",campingcarDateTimeList);
            mav.setViewName("admin");

        } catch (NullPointerException e){

            res.setContentType("text/html; charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('아이디 또는 비밀번호가 틀렸습니다.'); </script>");
            out.flush();


            // 다시 login page로 back
            mav.setViewName("login");
        }

        return mav;
    }


}
