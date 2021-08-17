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
    private HttpURLConnection http;

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


            if (calendarReturnTime.getReserveTime().equals("17시")){

                for (Long i = calendarRentTime.getTimeId(); i <= calendarReturnTime.getTimeId(); i++){
                    CalendarTime timeIndiv = calendarTimeService.findCalendarTimeByTimeId(i);
                    timeIndiv.setReserveComplete("1");

                }
            } else {
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



            // 문자전송
            String api_key = "NCS0P5SFAXLOJMJI";
            String api_secret = "FLLGUBZ7OTMQOXFSVE6ZWR2E010UNYIZ";
            Message coolsms = new Message(api_key, api_secret);
            HashMap<String, String> params = new HashMap<String, String>();
            HashMap<String, String> params2 = new HashMap<String, String>();


            /* 세이브카에 예약확인 문자 전송 */
            params.put("to", "01058283328"); // 01033453328 추가
            params.put("from", "01052774113");
            params.put("type", "LMS");


            /* 고객에게 예약확인 문자 전송 */

            params2.put("to", campingcarDateTime.getPhone());
            params2.put("from", "01052774113");  // 16613331 테스트하기
            params2.put("type", "LMS");

            params.put("text", "[캠핑카 예약 확정]\n"
                    + "성함: " + campingcarDateTime.getName() + "\n"
                    + "전화번호: " + campingcarDateTime.getPhone() + "\n"
                    + "차량명: " + campingcarDateTime.getCarType() + "\n"
                    + "입금자명: " + campingcarDateTime.getDepositor() + "\n"
                    + "대여날짜: " + campingcarDateTime.getRentDate() + "\n"
                    + "대여시간: " + campingcarDateTime.getRentTime() + "\n"
                    + "반납날짜: " + campingcarDateTime.getReturnDate() + "\n"
                    + "반납시간: " + campingcarDateTime.getReturnTime() + "\n"
                    + "이용날짜: " + campingcarDateTime.getDay() + "\n"
                    + "총금액: " + campingcarDateTime.getTotal() + "\n"
                    + "선결제금액: " + campingcarDateTime.getTotalHalf() + "\n"
                    + "요청사항: " + campingcarDateTime.getDetail() + "\n\n");

            params2.put("text", "[캠핑카 예약이 확정되었습니다.]" + "\n"
                    + "성함: " + campingcarDateTime.getName() + "\n"
                    + "전화번호: " + campingcarDateTime.getPhone() + "\n"
                    + "차량명: " + campingcarDateTime.getCarType() + "\n"
                    + "입금자명: " + campingcarDateTime.getDepositor() + "\n"
                    + "대여날짜: " + campingcarDateTime.getRentDate() + "\n"
                    + "대여시간: " + campingcarDateTime.getRentTime() + "\n"
                    + "반납날짜: " + campingcarDateTime.getReturnDate() + "\n"
                    + "반납시간: " + campingcarDateTime.getReturnTime() + "\n"
                    + "이용날짜: " + campingcarDateTime.getDay() + "\n"
                    + "총금액: " + campingcarDateTime.getTotal() + "\n"
                    + "선결제금액: " + campingcarDateTime.getTotalHalf() + "\n"
                    + "요청사항: " + campingcarDateTime.getDetail() + "\n\n");


            params.put("app_version", "test app 1.2");
            params2.put("app_version", "test app 1.2");


            /* 세이브카에게 문자 전송 */

            try {
                org.json.simple.JSONObject obj = (org.json.simple.JSONObject) coolsms.send(params);
                System.out.println(obj.toString()); //전송 결과 출력
            } catch (CoolsmsException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getCode());
            }

            /* 고객에게 예약확인 문자 전송 */

            try {
                org.json.simple.JSONObject obj2 = (org.json.simple.JSONObject) coolsms.send(params2);
                System.out.println(obj2.toString()); //전송 결과 출력
            } catch (CoolsmsException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getCode());
            }




            try {
                URL url = new URL("https://webhook.site/9e375c3c-2d6f-452b-b135-e665d46b596c");
                HttpURLConnection http = (HttpURLConnection) url.openConnection();

                AdminController admin = new AdminController(http);

                JSONObject jsonData = new JSONObject();
                jsonData.put("rent_month",rent_month);
                jsonData.put("rent_day",rent_day);
                jsonData.put("return_month",return_month);
                jsonData.put("return_day",return_day);
                jsonData.put("year","2021");
                jsonData.put("name",campingcarDateTime.getName());
                jsonData.put("phone",campingcarDateTime.getPhone());
                jsonData.put("car_type",campingcarDateTime.getCarType());
                jsonData.put("depositor",campingcarDateTime.getDepositor()); // 입금자명
                jsonData.put("detail",campingcarDateTime.getDetail());  // 요청사항
                jsonData.put("rent_time",campingcarDateTime.getRentTime());  // 대여시작시간
                jsonData.put("total",campingcarDateTime.getTotal());  // 총렌트료
                jsonData.put("total_half",campingcarDateTime.getTotalHalf());  // 보증금(입금금액)
                jsonData.put("create_date",campingcarDateTime.getCreatedDate());  // 예약 신청날짜

                admin.request("POST", "Content-Type", "application/json;charset=UTF-8", jsonData);
//                admin.response();


            } catch (IOException e){
                e.printStackTrace();
            }



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
