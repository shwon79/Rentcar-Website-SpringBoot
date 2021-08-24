package kr.carz.savecar.controller;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.*;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.JSONArray;
import org.json.JSONObject;
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
import java.net.URL;
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
    ReservationService reservationService;
    private HttpURLConnection http;

    @Autowired
    public AdminController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService,
                           ShortRentService shortRentService, CampingCarService campingCarService, CalendarDateService calendarDateService,
                           DateCampingService dateCampingService, CampingcarDateTimeService2 campingcarDateTimeService2,
                           LoginService loginService, CampingCarPriceService campingCarPriceService,
                           CalendarTimeService calendarTimeService, ReservationService reservationService) {
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
        this.reservationService = reservationService;
    }

    public AdminController(HttpURLConnection http){
        this.http = http;
    }

    public void request(String method, String headerName, String headerValue, JSONObject jsonData) throws IOException {
        http.setRequestMethod(method);
        http.setRequestProperty(headerName, headerValue);

        http.setDoOutput(true);

        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter((http.getOutputStream())));
        printWriter.write(jsonData.toString());
        printWriter.flush();
    }

    public String response() throws IOException{
        BufferedReader bufferedReader = null;

        int status = http.getResponseCode();

        if(status == HttpURLConnection.HTTP_OK){
            System.out.println("Http Connection OK");
            bufferedReader = new BufferedReader(new InputStreamReader(http.getInputStream()));
        } else {
            System.out.println("Http Connection Bad");
            bufferedReader = new BufferedReader(new InputStreamReader(http.getErrorStream()));
        }

        String line;
        StringBuffer response = new StringBuffer();

        while ((line = bufferedReader.readLine()) != null){
            response.append(line);
        }
        bufferedReader.close();

        System.out.println(response.toString());

        JSONObject jsonObject = new JSONObject(response.toString());

        System.out.println("응답값 : " + jsonObject);

        return jsonObject.toString();

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


    //예약 목록 조회 api
    @GetMapping("/admin/counsel")
    public String reservation_list(Model model) {
        List<Reservation> reservationList = reservationService.findAllReservations();
        model.addAttribute("reservationList", reservationList);

        return "admin_counsel";
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




}
