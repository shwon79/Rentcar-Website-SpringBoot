package kr.carz.savecar.controller;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.util.*;

@Controller
public class AdminController {
    MonthlyRentService monthlyRentService;
    YearlyRentService yearlyRentService;
    ShortRentService shortRentService;
    CampingCarService campingCarService;
    LoginService loginService;
    ReservationService reservationService;
    private HttpURLConnection http;

    @Autowired
    public AdminController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService,
                           ShortRentService shortRentService, CampingCarService campingCarService,
                           LoginService loginService, ReservationService reservationService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.shortRentService = shortRentService;
        this.campingCarService = campingCarService;
        this.loginService = loginService;
        this.reservationService = reservationService;
    }

    @GetMapping("/admin/login")
    public String login(Model model) {
        return "login";
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


            // admin view로 넘기기
//            List<CampingcarDateTime2> campingcarDateTimeList = campingcarDateTimeService2.findAllReservations();

//            mav.addObject("campingcarDateTimeList",campingcarDateTimeList);
            mav.setViewName("admin_campingcar_menu");

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


    // 로그아웃
    @GetMapping(value = "/admin/logout")
    @ResponseBody
    public ModelAndView get_admin_logout(HttpServletResponse res, HttpServletRequest req) throws IOException {

        ModelAndView mav = new ModelAndView();

        HttpSession session = req.getSession();
        session.removeAttribute("user");
        session.invalidate();

        res.setContentType("text/html; charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.println("<script>alert('로그아웃이 완료되었습니다.'); </script>");
        out.flush();

        mav.setViewName("login");


        return mav;
    }



    // 메인페이지
    @GetMapping(value = "/admin/campingcar/menu")
    @ResponseBody
    public ModelAndView get_admin_main(HttpServletResponse res, HttpServletRequest req) throws IOException {

        ModelAndView mav = new ModelAndView();
        HttpSession session = req.getSession();

        if((Login)session.getAttribute("user") == null){

            res.setContentType("text/html; charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('로그인 정보가 없습니다.'); </script>");
            out.flush();

            mav.setViewName("login");
        } else {

            // admin view로 넘기기
//            List<CampingcarDateTime2> campingcarDateTimeList = campingcarDateTimeService2.findAllReservations();

//            mav.addObject("campingcarDateTimeList",campingcarDateTimeList);
            mav.setViewName("admin_campingcar_menu");
        }

        return mav;
    }


    //예약 목록 조회 api
    @GetMapping("/admin/counsel/menu")
    public String reservation_list(Model model) {
        List<Reservation> reservationList = reservationService.findAllReservations();
        model.addAttribute("reservationList", reservationList);

        return "admin_counsel_menu";
    }



    // 할인가 적용하기 메뉴로 입장
    @GetMapping("/admin/discount/menu")
    public String discount_menu(Model model) {

        return "admin_discount_menu";
    }


//    @GetMapping("/admin/detail/{date_time_id}")
//    public String get_admin_detail(Model model,  @PathVariable String date_time_id) throws Exception {
//
//        CampingcarDateTime2 campingcarDateTime2 = campingcarDateTimeService2.findByDateTimeId(Long.parseLong(date_time_id));
//        model.addAttribute("campingcarDateTime2",campingcarDateTime2);
//        System.out.println(campingcarDateTime2.getDateTimeId());
//
//        return "admin_detail";
//    }


}
