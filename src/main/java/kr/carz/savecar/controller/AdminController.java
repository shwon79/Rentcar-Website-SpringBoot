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


    @Autowired
    public AdminController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService,
                           ShortRentService shortRentService, CampingCarService campingCarService, CalendarDateService calendarDateService,
                           DateCampingService dateCampingService, CampingcarDateTimeService2 campingcarDateTimeService2,
                           LoginService loginService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.shortRentService = shortRentService;
        this.campingCarService = campingCarService;
        this.calendarDateService = calendarDateService;
        this.dateCampingService = dateCampingService;
        this.campingcarDateTimeService2 = campingcarDateTimeService2;
        this.loginService = loginService;
    }

    @GetMapping("/admin/login")
    public String login(Model model) {

        return "login";
    }


//    @GetMapping("/admin/main")
//    public String admin(Model model, HttpServletRequest req) {
//
//        if(req.getSession() != null){
//            List<CampingcarDateTime> campingcarDateTimeList = campingcarDateTimeService.findAllReservations();
//            model.addAttribute("campingcarDateTimeList", campingcarDateTimeList);
//
//
//            System.out.println(req.getSession().getAttribute("user"));
//
//
//            return "admin";
//        } else {
//            return "login";
//        }
//    }


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

            System.out.println("false");

            // 다시 login page로 back
            mav.setViewName("login");
        }

        return mav;
    }

    //캠핑카 예약 확정
    @RequestMapping(value = "/campingcar/reservation/update/{reserveId}", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public void get_monthly_rent_category2(HttpServletResponse res, @PathVariable Long reserveId) throws IOException {

//        Optional<CampingcarDateTime> campingcarDateTime = campingcarDateTimeService.findById(reserveId);
//
//
//        System.out.println(campingcarDateTime.get().getCreatedDate());



//        List<MonthlyRent> monthlyRents = monthlyRentService.findCategory2OfMonthlyRents(category1);
//
//        List <String> categoryList2 = new ArrayList();
//
//
//        for (int i = 0; i < monthlyRents.size(); i++) {
//            if (!categoryList2.contains(monthlyRents.get(i).getCategory2() )){
//                categoryList2.add(monthlyRents.get(i).getCategory2());
//            }
//        }
//
//        JSONArray jsonArray = new JSONArray();
//
//        for (String c : categoryList2) {
//            jsonArray.put(c);
//        }
//
//        PrintWriter pw = res.getWriter();
//        pw.print(jsonArray.toString());
//        pw.flush();
//        pw.close();
    }

}
