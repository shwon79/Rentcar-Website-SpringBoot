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

        CampingcarDateTime2 campingcarDateTime = campingcarDateTimeService2.findByDateTimeId(reserveId);

        System.out.println(campingcarDateTime.getReservation());

        String [] rent_date = campingcarDateTime.getRentDate().split("월 ");
        String rent_month = rent_date[0];
        System.out.println(rent_month);

        String [] rent_day_list = rent_date[1].split("일");
        String rent_day = rent_day_list[0];
        System.out.println(rent_day);

        CalendarDate calendarDate = calendarDateService.findCalendarDateByMonthAndDayAndYear(rent_month, rent_day, "2021");
        System.out.println(campingcarDateTime.getCarType());
        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(campingcarDateTime.getCarType());


        // 수정필요 :
        DateCamping dateCamping = dateCampingService.findByDateIdAndCarName(calendarDate,campingCarPrice);
        dateCamping.setReserved("1");
        campingcarDateTime.setReservation("1");

        dateCampingService.save(dateCamping);

    }

}
