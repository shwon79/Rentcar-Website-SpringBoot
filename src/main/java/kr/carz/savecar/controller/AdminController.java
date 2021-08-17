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
    CalendarTimeService calendarTimeService;


    @Autowired
    public AdminController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService,
                           ShortRentService shortRentService, CampingCarService campingCarService, CalendarDateService calendarDateService,
                           DateCampingService dateCampingService, CampingcarDateTimeService2 campingcarDateTimeService2,
                           LoginService loginService, CampingCarPriceService campingCarPriceService,
                           CalendarTimeService calendarTimeService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.shortRentService = shortRentService;
        this.campingCarService = campingCarService;
        this.calendarDateService = calendarDateService;
        this.dateCampingService = dateCampingService;
        this.campingcarDateTimeService2 = campingcarDateTimeService2;
        this.loginService = loginService;
        this.campingCarPriceService = campingCarPriceService;
        this.calendarTimeService = calendarTimeService;
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


    // 메인페이지
    @GetMapping(value = "/admin/main")
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
            List<CampingcarDateTime2> campingcarDateTimeList = campingcarDateTimeService2.findAllReservations();

            mav.addObject("campingcarDateTimeList",campingcarDateTimeList);
            mav.setViewName("admin");

        }

        return mav;
    }


    // 메인페이지
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



    //캠핑카 예약 확정
    @RequestMapping(value = "/campingcar/reservation/update/{reserveId}", produces = "application/json; charset=UTF-8", method= RequestMethod.GET)
    @ResponseBody
    public ModelAndView get_monthly_rent_category2(HttpServletResponse res, @PathVariable Long reserveId) throws IOException {

        ModelAndView mav = new ModelAndView();

        try {
            CampingcarDateTime2 campingcarDateTime = campingcarDateTimeService2.findByDateTimeId(reserveId);
            campingcarDateTime.setReservation("1");


            // 대여일자, 대여시간
            String[] rent_date = campingcarDateTime.getRentDate().split("월 ");
            String rent_month = rent_date[0];
            System.out.println(rent_month);

            String[] rent_day_list = rent_date[1].split("일");
            String rent_day = rent_day_list[0];
            System.out.println(rent_day);


            // 반납일자, 반납시간
            String[] return_date = campingcarDateTime.getReturnDate().split("월 ");
            String return_month = return_date[0];
            System.out.println(return_month);

            String[] return_day_list = return_date[1].split("일");
            String return_day = return_day_list[0];
            System.out.println(return_day);


            // CalendarDate 날짜 객체 가져오기
            CalendarDate calendarDate = calendarDateService.findCalendarDateByMonthAndDayAndYear(rent_month, rent_day, "2021");
            CalendarDate returnCalendarDate = calendarDateService.findCalendarDateByMonthAndDayAndYear(return_month, return_day, "2021");
            System.out.println(calendarDate.getDateId());
            System.out.println(returnCalendarDate.getDateId());


            // CampingCarPrice 객체 가져오기
            CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(campingcarDateTime.getCarType());

            CalendarTime calendarRentTime = calendarTimeService.findCalendarTimeByDateIdAndCarNameAndReserveTime(calendarDate, campingCarPrice, campingcarDateTime.getRentTime());
            CalendarTime calendarReturnTime = calendarTimeService.findCalendarTimeByDateIdAndCarNameAndReserveTime(returnCalendarDate, campingCarPrice, campingcarDateTime.getReturnTime());

            System.out.println("반납 시간 : "+calendarReturnTime.getReserveTime());

            if (calendarReturnTime.getReserveTime().equals("17시")){
                System.out.println("17시");

                for (Long i = calendarRentTime.getTimeId(); i <= calendarReturnTime.getTimeId(); i++){
                    CalendarTime timeIndiv = calendarTimeService.findCalendarTimeByTimeId(i);
                    timeIndiv.setReserveComplete("1");

                }
            } else {
                System.out.println("17시 else");
                for (Long i = calendarRentTime.getTimeId(); i <= calendarReturnTime.getTimeId() + 1; i++){
                    CalendarTime timeIndiv = calendarTimeService.findCalendarTimeByTimeId(i);
                    timeIndiv.setReserveComplete("1");
                }
            }



            for (Long i = calendarDate.getDateId(); i <= returnCalendarDate.getDateId(); i++) {
                // CalendarDate 날짜 객체 가져오기
                CalendarDate calendarDateIndiv = calendarDateService.findCalendarDateByDateId(i);

                // DateCamping 에서 날짜랑 차정보로 하루 예약 정보 찾기
                DateCamping dateCamping = dateCampingService.findByDateIdAndCarName(calendarDateIndiv, campingCarPrice);
                // DateCamping 하루 예약 정보 수정, campingcarDateTime 예약리스트 예약 정보 수정
                dateCamping.setReserved("1");
            }


            // campingcarDateTime 저장
            Long testLong = campingcarDateTimeService2.save2(campingcarDateTime);
            System.out.println(testLong);

            // dateCamping 저장
//        dateCampingService.save(dateCamping);

            // 이거 왜 안찍혀;;;
            System.out.println("testest");


            // 예약 확정되었습니다. alert
            res.setContentType("text/html; charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('예약이 확정되었습니다.');  location.href='/admin/main';</script>");
            out.flush();


        } catch (NullPointerException e){
            // 예약 실패. alert
            res.setContentType("text/html; charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('이용할 수 없는 날짜입니다.'); location.href='/admin/main'; </script>");
            out.flush();
        } finally {

            // view
            List<CampingcarDateTime2> campingcarDateTimeList = campingcarDateTimeService2.findAllReservations();

            mav.addObject("campingcarDateTimeList",campingcarDateTimeList);
            mav.setViewName("admin");
        }



        return mav;
    }


}
