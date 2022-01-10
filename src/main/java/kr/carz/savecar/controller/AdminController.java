package kr.carz.savecar.controller;

import kr.carz.savecar.dto.*;
import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.*;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AdminController {
    MonthlyRentService monthlyRentService;
    YearlyRentService yearlyRentService;
    ShortRentService shortRentService;
    CampingCarService campingCarService;
    LoginService loginService;
    ReservationService reservationService;
    DiscountService discountService;
    MorenReservationService morenReservationService;
    ExplanationService explanationService;
    CampingcarReservationService campingcarReservationService;
    CalendarTimeService calendarTimeService;
    CampingCarPriceService campingCarPriceService;
    CalendarDateService calendarDateService;
    DateCampingService dateCampingService;

    @Autowired
    public AdminController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService,
                           ShortRentService shortRentService, CampingCarService campingCarService,
                           LoginService loginService, ReservationService reservationService,
                           DiscountService discountService, MorenReservationService morenReservationService,
                           ExplanationService explanationService, CampingcarReservationService campingcarReservationService,
                           CalendarTimeService calendarTimeService, CampingCarPriceService campingCarPriceService,
                           CalendarDateService calendarDateService, DateCampingService dateCampingService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.shortRentService = shortRentService;
        this.campingCarService = campingCarService;
        this.loginService = loginService;
        this.reservationService = reservationService;
        this.discountService = discountService;
        this.morenReservationService = morenReservationService;
        this.explanationService = explanationService;
        this.campingcarReservationService = campingcarReservationService;
        this.calendarTimeService = calendarTimeService;
        this.campingCarPriceService = campingCarPriceService;
        this.calendarDateService = calendarDateService;
        this.dateCampingService = dateCampingService;
    }

    @Value("${coolsms.api_key}")
    private String api_key;

    @Value("${coolsms.api_secret}")
    private String api_secret;

    @Value("${phone.admin1}")
    private String admin1;

    @Value("${phone.admin2}")
    private String admin2;

    @Value("${phone.admin3}")
    private String admin3;

    @Value("${moren.request_url}")
    private String request_url;

    private static String AddDate(String strDate, int year, int month, int day) throws Exception {
        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date dt = dtFormat.parse(strDate);
        cal.setTime(dt);
        cal.add(Calendar.YEAR, year);
        cal.add(Calendar.MONTH, month);
        cal.add(Calendar.DATE, day);
        return dtFormat.format(cal.getTime());
    }


    public String[] getTwoHundredStrings(StringBuffer inputBuff, String someToken) {
        String[] nameArray = new String[200];

        int currentPos = 0;
        int nextPos;

        for (int i = 0; i < 200; i++) {

            nextPos = inputBuff.indexOf(someToken, currentPos);

            if (nextPos < 0) {
                break;
            }

            String nextName = inputBuff.substring(currentPos, nextPos);

            nameArray[i] = nextName;
            currentPos = nextPos+1;
        }

        return nameArray;
    }



    @GetMapping("/admin/login")
    public String login() {
        return "admin/login";
    }

    //로그인
//    @RequestMapping(value = "/admin/logininfo", method= RequestMethod.POST)
    @PostMapping("/admin/logininfo")
    @ResponseBody
    public ModelAndView post_login_info(HttpServletResponse res, HttpServletRequest req) throws IOException {

        ModelAndView mav = new ModelAndView();

        try {
            Login user = loginService.findLoginByIdAndPwd(req.getParameter("id"), req.getParameter("pwd"));
            System.out.println(user.getId());  // exception 발생코드임, 건들지 말기 => 다른 방식 찾기

            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            List<CampingCarReservation> campingCarReservationList = campingcarReservationService.findAllReservations();
            mav.addObject("campingCarReservationList", campingCarReservationList);
            mav.setViewName("admin/campingcar_menu");

        } catch (NullPointerException e){

            res.setContentType("text/html; charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('아이디 또는 비밀번호가 틀렸습니다.'); </script>");
            out.flush();

            // 다시 login page로 back
            mav.setViewName("admin/login");
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

        mav.setViewName("admin/login");

        return mav;
    }



    // [관리자 메인페이지] 캠핑카 예약내역 메뉴로 입장
    @GetMapping(value = "/admin/campingcar/menu")
    @ResponseBody
    public ModelAndView get_admin_main(HttpServletResponse res, HttpServletRequest req) throws IOException {

        ModelAndView mav = new ModelAndView();
        HttpSession session = req.getSession();

        if(session.getAttribute("user") == null){

            res.setContentType("text/html; charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('로그인 정보가 없습니다.'); </script>");
            out.flush();

            mav.setViewName("admin/login");
        } else {

            List<CampingCarReservation> campingCarReservationList = campingcarReservationService.findAllReservations();
            mav.addObject("campingCarReservationList", campingCarReservationList);
            mav.setViewName("admin/campingcar_menu");
        }

        return mav;
    }

    // [관리자 메인페이지] 캠핑카 예약내역 detail 페이지로 입장
    @GetMapping(value = "/admin/campingcar/detail/{reservationId}")
    @ResponseBody
    public ModelAndView get_admin_campincar_detail(HttpServletResponse res, HttpServletRequest req, @PathVariable Long reservationId) throws IOException {

        ModelAndView mav = new ModelAndView();
        HttpSession session = req.getSession();

        if(session.getAttribute("user") == null){

            res.setContentType("text/html; charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('로그인 정보가 없습니다.'); </script>");
            out.flush();

            mav.setViewName("admin/login");
        } else {

            Optional<CampingCarReservation> campingCarReservation = campingcarReservationService.findById(reservationId);
            if(campingCarReservation.isPresent()){
                mav.addObject("campingCarReservation", campingCarReservation.get());
            }
            mav.setViewName("admin/campingcar_detail");
        }

        return mav;
    }


    //상담 메뉴로 입장
    @GetMapping("/admin/counsel/menu")
    public ModelAndView get_counsel_menu(HttpServletResponse res, HttpServletRequest req) throws IOException {

        ModelAndView mav = new ModelAndView();
        HttpSession session = req.getSession();

        if(session.getAttribute("user") == null){

            res.setContentType("text/html; charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('로그인 정보가 없습니다.'); </script>");
            out.flush();

            mav.setViewName("admin/login");
        } else {

            List<Reservation> reservationList = reservationService.findAllReservations();
            mav.addObject("reservationList", reservationList);
            mav.setViewName("admin/counsel_menu");
        }

        return mav;
    }



    // 할인가 적용하기 메뉴로 입장
    @GetMapping("/admin/discount/menu")
    public ModelAndView get_discount_menu(HttpServletResponse res, HttpServletRequest req) throws IOException {


        ModelAndView mav = new ModelAndView();
        HttpSession session = req.getSession();

        if(session.getAttribute("user") == null){

            res.setContentType("text/html; charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('로그인 정보가 없습니다.'); </script>");
            out.flush();

            mav.setViewName("admin/login");
        } else {
            List<Discount> discountList = discountService.findAllDiscounts();
            mav.addObject("discountList", discountList);
            mav.setViewName("admin/discount_menu");
        }

        return mav;
    }

    // 월렌트 실시간 모렌 예약 메뉴로 입장
    @GetMapping("/admin/moren/reservation/menu")
    public ModelAndView get_moren_reservation_menu(HttpServletResponse res, HttpServletRequest req) throws IOException {

        ModelAndView mav = new ModelAndView();
        HttpSession session = req.getSession();

        if(session.getAttribute("user") == null){

            res.setContentType("text/html; charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('로그인 정보가 없습니다.'); </script>");
            out.flush();

            mav.setViewName("admin/login");
        } else {

            List<MorenReservation> morenReservationList = morenReservationService.findAllMorenReservations();
            mav.addObject("morenReservationList", morenReservationList);
            mav.setViewName("admin/moren_reservation_menu");
        }

        mav.addObject("byTime", Comparator.comparing(MorenReservation::getCreatedDate).reversed());

        return mav;
    }

    // 월렌트 실시간 모렌 디테일 페이지로 입장
    @GetMapping("/admin/moren/reservation/detail/{reservationId}")
    public ModelAndView get_moren_reservation_menu(HttpServletResponse res, HttpServletRequest req,  @PathVariable Long reservationId) throws IOException {

        ModelAndView mav = new ModelAndView();
        HttpSession session = req.getSession();

        if(session.getAttribute("user") == null){

            res.setContentType("text/html; charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('로그인 정보가 없습니다.'); </script>");
            out.flush();

            mav.setViewName("admin/login");
        } else {
            Optional<MorenReservation> morenReservation = morenReservationService.findMorenReservationById(reservationId);
            if (morenReservation.isPresent()){
                mav.addObject("morenReservationDTO", morenReservation.get());
                mav.setViewName("admin/moren_reservation_detail");
            } else {
                res.setContentType("text/html; charset=UTF-8");
                PrintWriter out = res.getWriter();
                out.println("<script>alert('해당 차량 정보를 찾을 수 없습니다.'); </script>");
                out.flush();

                mav.setViewName("admin/moren_reservation_menu");
            }
        }

        return mav;
    }



    // [관리자 메인페이지] 캠핑카 기본설정 메뉴로 입장
    @GetMapping(value = "/admin/setting/menu")
    @ResponseBody
    public ModelAndView get_setting_main(HttpServletResponse res, HttpServletRequest req) throws IOException {

        ModelAndView mav = new ModelAndView();
        HttpSession session = req.getSession();
        Optional<Explanation> explanation = explanationService.findById(Long.valueOf(0));

        if(session.getAttribute("user") == null){

            res.setContentType("text/html; charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('로그인 정보가 없습니다.'); </script>");
            out.flush();

            mav.setViewName("admin/login");
        } else {
            mav.addObject("explanation",explanation.get());
            mav.setViewName("admin/setting_menu");
        }

        return mav;
    }



    // [관리자 메인페이지] 캠핑카 가격 메뉴로 입장
    @GetMapping(value = "/admin/campingcar/price/menu")
    @ResponseBody
    public ModelAndView get_campingcar_price_main(HttpServletResponse res, HttpServletRequest req) throws IOException {

        ModelAndView mav = new ModelAndView();
        HttpSession session = req.getSession();

        if(session.getAttribute("user") == null){

            res.setContentType("text/html; charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('로그인 정보가 없습니다.'); </script>");
            out.flush();

            mav.setViewName("admin/login");
        } else {
            mav.setViewName("admin/campingcar_price_menu");
        }

        return mav;
    }

    // 캠핑카 예약 수정, 확정, 취소하기 api
    @PutMapping(value = "/admin/campingcar/reservation/{reservationId}")
    @ResponseBody
    @Transactional
    public void put_admin_campingcar_reservation(HttpServletResponse res, @PathVariable Long reservationId, @RequestBody CampingCarReservationDTO campingCarReservationDTO) throws Exception {

        JSONObject jsonObject = new JSONObject();
        JSONObject morenJsonObject = new JSONObject();

        Optional<CampingCarReservation> campingCarReservationWrapper = campingcarReservationService.findById(reservationId);
        CampingCarReservation campingCarReservation = campingCarReservationWrapper.get();


        // 문자전송
        Message coolsms = new Message(api_key, api_secret);
        HashMap<String, String> params = new HashMap<>();
        HashMap<String, String> params2 = new HashMap<>();

        /* 세이브카에 예약확인 문자 전송 */
        params.put("to", admin1+", "+admin2+", "+admin3); // 01033453328 추가
        params.put("from", admin3);
        params.put("type", "LMS");

        /* 고객에게 예약확인 문자 전송 */
        params2.put("to", campingCarReservation.getPhone());
        params2.put("from", admin3);
        params2.put("type", "LMS");


        String[] splitedRentDate = campingCarReservation.getRentDate().split("-");
        String[] splitedReturnDate = campingCarReservation.getReturnDate().split("-");

        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(campingCarReservation.getCarType());
        CalendarDate calendarStartDate = calendarDateService.findCalendarDateByMonthAndDayAndYear(splitedRentDate[1], splitedRentDate[2], splitedRentDate[0]);
        CalendarDate calendarEndDate = calendarDateService.findCalendarDateByMonthAndDayAndYear(splitedReturnDate[1], splitedReturnDate[2], splitedReturnDate[0]);
        List<DateCamping> dateCampingList = dateCampingService.findByCarNameAndDateIdGreaterThanEqualAndDateIdLessThanEqual(campingCarPrice, calendarStartDate, calendarEndDate);

        int dateCampingListSize = dateCampingList.size();
        String taskName;
        String orderType;

        if (campingCarReservation.getReservation() == 0 && campingCarReservationDTO.getReservation() == 1) {
            taskName = "확정";
            orderType = "new";
            for (int i = 0; i < dateCampingListSize; i++) {
                List<CalendarTime> calendarTimeList;
                DateCamping dateCamping = dateCampingList.get(i);

                if (i == 0 && !campingCarReservation.getRentTime().equals("10시")) {
                    calendarTimeList = calendarTimeService.findByDateIdAndCarNameAndReserveTimeGreaterThanEqual(dateCamping.getDateId(), campingCarPrice, campingCarReservation.getRentTime());
                    List<CalendarTime> calendarTimeForCheckList = calendarTimeService.findByDateIdAndCarNameAndReserveTimeLessThan(dateCamping.getDateId(), campingCarPrice, campingCarReservation.getRentTime());

                    int one_cnt = 0;
                    for (int j = 0; j < calendarTimeForCheckList.size(); j++) {
                        if (calendarTimeForCheckList.get(j).getReserveComplete().equals("1")) {
                            one_cnt += 1;
                        }
                    }

                    if (one_cnt != calendarTimeForCheckList.size()) {
                        if(calendarTimeForCheckList.get(0).getReserveComplete().equals("1")){
                            dateCampingList.get(i).setReserved("2");
                        } else {
                            dateCampingList.get(i).setReserved("1");
                        }
                    } else if(one_cnt == calendarTimeForCheckList.size()){
                        dateCampingList.get(i).setReserved("2");
                    }
                } else if (i == dateCampingListSize - 1 && !campingCarReservation.getReturnTime().equals("18시")) {
                    calendarTimeList = calendarTimeService.findByDateIdAndCarNameAndReserveTimeLessThanEqual(dateCamping.getDateId(), campingCarPrice, campingCarReservation.getReturnTime());
                    List<CalendarTime> calendarTimeForCheckList = calendarTimeService.findByDateIdAndCarNameAndReserveTimeGreaterThan(dateCamping.getDateId(), campingCarPrice, campingCarReservation.getRentTime());

                    int one_cnt = 0;
                    for (int j = 0; j < calendarTimeForCheckList.size(); j++) {
                        if (calendarTimeForCheckList.get(j).getReserveComplete().equals("1")) {
                            one_cnt += 1;
                        }
                    }

                    if (one_cnt != calendarTimeForCheckList.size()) {
                        if(calendarTimeForCheckList.get(calendarTimeForCheckList.size()-1).getReserveComplete().equals("1")){
                            dateCampingList.get(i).setReserved("2");
                        } else {
                            dateCampingList.get(i).setReserved("1");
                        }
                    } else if(one_cnt == calendarTimeForCheckList.size()){
                        dateCampingList.get(i).setReserved("2");
                    }
                } else {
                    calendarTimeList = calendarTimeService.findCalendarTimeByDateIdAndCarName(dateCamping.getDateId(), campingCarPrice);
                    dateCampingList.get(i).setReserved("2");
                }

                for (int j = 0; j < calendarTimeList.size(); j++) {
                    calendarTimeList.get(j).setReserveComplete("1");
                    calendarTimeService.save(calendarTimeList.get(j));
                }
            }

        } else if (campingCarReservation.getReservation() == 1 && campingCarReservationDTO.getReservation() == 0) {
            taskName = "취소";
            orderType = "cancel";
            morenJsonObject.put("ORDER_CODE", campingCarReservationDTO.getOrderCode());
            for (int i = 0; i < dateCampingListSize; i++) {
                List<CalendarTime> calendarTimeList;
                DateCamping dateCamping = dateCampingList.get(i);

                if (i == 0 && !campingCarReservation.getRentTime().equals("10시")) {
                    calendarTimeList = calendarTimeService.findByDateIdAndCarNameAndReserveTimeGreaterThanEqual(dateCamping.getDateId(), campingCarPrice, campingCarReservation.getRentTime());
                    List<CalendarTime> calendarTimeForCheckList = calendarTimeService.findByDateIdAndCarNameAndReserveTimeLessThan(dateCamping.getDateId(), campingCarPrice, campingCarReservation.getRentTime());

                    int start_chk = 1;
                    for (int j = 0; j < calendarTimeForCheckList.size(); j++) {
                        if (calendarTimeForCheckList.get(j).getReserveComplete().equals("1")) {
                            dateCampingList.get(i).setReserved("1");
                            start_chk = 0;
                            break;
                        }
                    }
                    if (start_chk == 1){
                        dateCampingList.get(i).setReserved("0");
                    }
                } else if (i == dateCampingListSize - 1 && !campingCarReservation.getReturnTime().equals("18시")) {
                    calendarTimeList = calendarTimeService.findByDateIdAndCarNameAndReserveTimeLessThanEqual(dateCamping.getDateId(), campingCarPrice, campingCarReservation.getReturnTime());
                    List<CalendarTime> calendarTimeForCheckList = calendarTimeService.findByDateIdAndCarNameAndReserveTimeGreaterThan(dateCamping.getDateId(), campingCarPrice, campingCarReservation.getReturnTime());

                    int start_chk = 1;
                    for (int j = 0; j < calendarTimeForCheckList.size(); j++) {
                        if (calendarTimeForCheckList.get(j).getReserveComplete().equals("1")) {
                            dateCampingList.get(i).setReserved("1");
                            start_chk = 0;
                            break;
                        }
                    }
                    if (start_chk == 1){
                        dateCampingList.get(i).setReserved("0");
                    }
                } else {
                    calendarTimeList = calendarTimeService.findCalendarTimeByDateIdAndCarName(dateCamping.getDateId(), campingCarPrice);
                    dateCampingList.get(i).setReserved("0");
                }

                for (int j = 0; j < calendarTimeList.size(); j++) {
                    calendarTimeList.get(j).setReserveComplete("0");
                    calendarTimeService.save(calendarTimeList.get(j));
                }
            }
        } else {
            taskName = "수정";
            orderType = "update";
        }


        String contractType = "2";
        String orderStartTime = campingCarReservationDTO.getRentDate() + " " + campingCarReservationDTO.getRentTime().substring(0,2) + ":00";
        String orderEndTime = campingCarReservationDTO.getReturnDate() + " " + campingCarReservationDTO.getReturnTime().substring(0,2) + ":00";

        if(taskName.equals("확정")) {

            try {
                URL url = new URL(request_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();


                CampingCarPrice campingCarPriceForMoren = campingCarPriceService.findCampingCarPriceByCarName(campingCarReservationDTO.getCarType());

                morenJsonObject.put("COMPANY_ID", "1343");
                morenJsonObject.put("CAR_NUM", campingCarPriceForMoren.getCarNum());
                morenJsonObject.put("CAR_CODE", campingCarPriceForMoren.getCarCode());
                morenJsonObject.put("ORDER_TYPE", orderType);
                morenJsonObject.put("ORDER_CONTRACT_TYPE", contractType);
                morenJsonObject.put("ORDER_CUSTOMER_NAME", campingCarReservationDTO.getName());
                morenJsonObject.put("ORDER_CUSTOMER_PHONE", campingCarReservationDTO.getPhone());
                morenJsonObject.put("ORDER_START_TIME", orderStartTime);
                morenJsonObject.put("ORDER_END_TIME", orderEndTime);
                morenJsonObject.put("ORDER_CUSTOMER_MEMO", campingCarReservationDTO.getDetail());
                morenJsonObject.put("ORDER_PRICE", campingCarReservationDTO.getTotal());
                morenJsonObject.put("ORDER_PRICE_TAX", "0");
                morenJsonObject.put("ORDER_DEPOSIT", campingCarReservationDTO.getDeposit());
                morenJsonObject.put("ORDER_CDW", "1");

                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);

                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(conn.getOutputStream()));
                printWriter.write(morenJsonObject.toString());
                printWriter.flush();

                // 응답
                BufferedReader bufferedReader;
                int status = conn.getResponseCode();
                if (status == HttpURLConnection.HTTP_OK){
                    bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }

                String line;
                StringBuffer response = new StringBuffer();

                while ((line = bufferedReader.readLine()) != null){
                    response.append(line);
                }
                bufferedReader.close();
                System.out.println("응답값 : " + response);

                String[] splited_response = getTwoHundredStrings(response, "\"");
                campingCarReservation.setOrderCode(splited_response[9]);

            } catch (Exception e){
                jsonObject.put("result", 0);

                PrintWriter pw = res.getWriter();
                pw.print(jsonObject);
                pw.flush();
                pw.close();

                return;
            }
        } else if(taskName.equals("취소")){

            try {
                URL url = new URL(request_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                CampingCarPrice campingCarPriceForMoren = campingCarPriceService.findCampingCarPriceByCarName(campingCarReservationDTO.getCarType());

                morenJsonObject.put("COMPANY_ID", "1343");
                morenJsonObject.put("CAR_NUM", campingCarPriceForMoren.getCarNum());
                morenJsonObject.put("CAR_CODE", campingCarPriceForMoren.getCarCode());
                morenJsonObject.put("ORDER_TYPE", orderType);
                morenJsonObject.put("ORDER_CUSTOMER_NAME", campingCarReservationDTO.getName());
                morenJsonObject.put("ORDER_CUSTOMER_PHONE", campingCarReservationDTO.getPhone());
                morenJsonObject.put("ORDER_CODE", campingCarReservationDTO.getOrderCode());

                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);

                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(conn.getOutputStream()));
                printWriter.write(morenJsonObject.toString());
                printWriter.flush();

                // 응답
                BufferedReader bufferedReader;
                int status = conn.getResponseCode();
                if (status == HttpURLConnection.HTTP_OK){
                    bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }

                String line;
                StringBuffer response = new StringBuffer();

                while ((line = bufferedReader.readLine()) != null){
                    response.append(line);
                }
                bufferedReader.close();
                System.out.println("응답값 : " + response);

            } catch (Exception e){
                System.out.println(e);
                jsonObject.put("result", 0);

                PrintWriter pw = res.getWriter();
                pw.print(jsonObject);
                pw.flush();
                pw.close();

                return;
            }
        } else {
            if(campingCarReservationDTO.getReservation() == 1) {

                try {
                    URL url = new URL(request_url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    CampingCarPrice campingCarPriceForMoren = campingCarPriceService.findCampingCarPriceByCarName(campingCarReservationDTO.getCarType());

                    morenJsonObject.put("COMPANY_ID", "1343");
                    morenJsonObject.put("CAR_NUM", campingCarPriceForMoren.getCarNum());
                    morenJsonObject.put("CAR_CODE", campingCarPriceForMoren.getCarCode());
                    morenJsonObject.put("ORDER_TYPE", orderType);
                    morenJsonObject.put("ORDER_CONTRACT_TYPE", contractType);
                    morenJsonObject.put("ORDER_CUSTOMER_NAME", campingCarReservationDTO.getName());
                    morenJsonObject.put("ORDER_CUSTOMER_PHONE", campingCarReservationDTO.getPhone());
                    morenJsonObject.put("ORDER_START_TIME", orderStartTime);
                    morenJsonObject.put("ORDER_END_TIME", orderEndTime);
                    morenJsonObject.put("ORDER_CUSTOMER_MEMO", campingCarReservationDTO.getDetail());
                    morenJsonObject.put("ORDER_PRICE", campingCarReservationDTO.getTotal());
                    morenJsonObject.put("ORDER_PRICE_TAX", "0");
                    morenJsonObject.put("ORDER_DEPOSIT", campingCarReservationDTO.getDeposit());
                    morenJsonObject.put("ORDER_CDW", "1");
                    morenJsonObject.put("ORDER_CODE", campingCarReservationDTO.getOrderCode());

                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json; utf-8");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);

                    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(conn.getOutputStream()));
                    printWriter.write(morenJsonObject.toString());
                    printWriter.flush();

                    // 응답
                    BufferedReader bufferedReader;
                    int status = conn.getResponseCode();
                    if (status == HttpURLConnection.HTTP_OK) {
                        bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    } else {
                        bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    }

                    String line;
                    StringBuffer response = new StringBuffer();

                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    bufferedReader.close();
                    System.out.println("응답값 : " + response);

                } catch (Exception e) {
                    System.out.println(e);
                    jsonObject.put("result", 0);

                    PrintWriter pw = res.getWriter();
                    pw.print(jsonObject);
                    pw.flush();
                    pw.close();

                    return;
                }
            }

            jsonObject.put("result", 1);
        }

        campingCarReservation.setAgree(campingCarReservationDTO.getAgree());
        campingCarReservation.setCarType(campingCarReservationDTO.getCarType());
        campingCarReservation.setDay(campingCarReservationDTO.getDay());
        campingCarReservation.setDeposit(campingCarReservationDTO.getDeposit());
        campingCarReservation.setDepositor(campingCarReservationDTO.getDepositor());
        campingCarReservation.setDetail(campingCarReservationDTO.getDetail());
        campingCarReservation.setName(campingCarReservationDTO.getName());
        campingCarReservation.setPhone(campingCarReservationDTO.getPhone());
        campingCarReservation.setRentDate(campingCarReservationDTO.getRentDate());
        campingCarReservation.setRentTime(campingCarReservationDTO.getRentTime());
        campingCarReservation.setReservation(campingCarReservationDTO.getReservation());
        campingCarReservation.setReturnDate(campingCarReservationDTO.getReturnDate());
        campingCarReservation.setReturnTime(campingCarReservationDTO.getReturnTime());
        campingCarReservation.setTotal(campingCarReservationDTO.getTotal());
        campingCarReservation.setTotalHalf(campingCarReservationDTO.getTotalHalf());
        campingCarReservation.setExtraTime(campingCarReservationDTO.getExtraTime());

        campingcarReservationService.save(campingCarReservation);


        if(!taskName.equals("수정")) {
            params.put("text", "[캠핑카 캘린더 예약 " + taskName + "]\n"
                    + "성함: " + campingCarReservation.getName() + "\n"
                    + "전화번호: " + campingCarReservation.getPhone() + "\n"
                    + "차량명: " + campingCarReservation.getCarType() + "\n"
                    + "입금자명: " + campingCarReservation.getDepositor() + "\n"
                    + "대여날짜: " + campingCarReservation.getRentDate() + "\n"
                    + "대여시간: " + campingCarReservation.getRentTime() + "\n"
                    + "반납날짜: " + campingCarReservation.getReturnDate() + "\n"
                    + "반납시간: " + campingCarReservation.getReturnTime() + "\n"
                    + "이용날짜: " + campingCarReservation.getDay() + "\n"
                    + "총금액: " + campingCarReservation.getTotal() + "\n"
                    + "선결제금액: " + campingCarReservation.getTotalHalf() + "\n"
                    + "요청사항: " + campingCarReservation.getDetail() + "\n\n");

            params2.put("text", "[캠핑카 예약이 " + taskName + "되었습니다.]" + "\n"
                    + "성함: " + campingCarReservation.getName() + "\n"
                    + "전화번호: " + campingCarReservation.getPhone() + "\n"
                    + "차량명: " + campingCarReservation.getCarType() + "\n"
                    + "대여날짜: " + campingCarReservation.getRentDate() + "\n"
                    + "대여시간: " + campingCarReservation.getRentTime() + "\n"
                    + "반납날짜: " + campingCarReservation.getReturnDate() + "\n"
                    + "반납시간: " + campingCarReservation.getReturnTime() + "\n"
                    + "입금자명: " + campingCarReservation.getDepositor() + "\n"
                    + "이용날짜: " + campingCarReservation.getDay() + "\n"
                    + "총금액: " + campingCarReservation.getTotal() + "\n"
                    + "선결제금액: " + campingCarReservation.getTotalHalf() + "\n"
                    + "요청사항: " + campingCarReservation.getDetail() + "\n\n");


            params.put("app_version", "test app 1.2");
            params2.put("app_version", "test app 1.2");

            /* 세이브카에게 문자 전송 */
            try {
                org.json.simple.JSONObject obj = coolsms.send(params);
                System.out.println(obj.toString()); //전송 결과 출력
            } catch (CoolsmsException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getCode());
            }

            /* 고객에게 예약확인 문자 전송 */
            try {
                org.json.simple.JSONObject obj2 = coolsms.send(params2);
                System.out.println(obj2.toString()); //전송 결과 출력
            } catch (CoolsmsException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getCode());
            }
        }
        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    // 캠핑카 reservation 삭제 api
    @DeleteMapping("/admin/campingcar/reservation/{reservationId}")
    @ResponseBody
    public void delete_campingcar_reservation(HttpServletResponse res, @PathVariable Long reservationId) throws IOException {

        JSONObject jsonObject = new JSONObject();

        Optional<CampingCarReservation> campingCarReservationWrapper = campingcarReservationService.findById(reservationId);

        if(campingCarReservationWrapper.isPresent()){
            campingcarReservationService.delete(campingCarReservationWrapper.get());
            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    // 할인가 적용하기 api
//    @RequestMapping(value = "/admin/discount", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @PostMapping("/admin/discount")
    @ResponseBody
    public void save_discount(HttpServletResponse res, @RequestBody DiscountSaveDTO discountDTO) throws IOException {

        JSONObject jsonObject = new JSONObject();

        // 이미 db에 등록된 차량인지 확인
        Optional<Discount> original_discount = discountService.findDiscountByCarNo(discountDTO.getCarNo());
        if(original_discount.isPresent()){
            jsonObject.put("result", 0);
        } else {
            Discount discount = new Discount();
            discount.setCarNo(discountDTO.getCarNo());
            discount.setDiscount(discountDTO.getDiscount());
            discount.setDescription(discountDTO.getDescription());
            discountService.save(discount);

            jsonObject.put("result", 1);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    // 할인가 수정하기 api
    @RequestMapping(value = "/admin/discount/{carNo}/{discount}", produces = "application/json; charset=UTF-8", method = RequestMethod.PUT)
    @ResponseBody
    public void update_discount(HttpServletResponse res, @PathVariable String carNo, @PathVariable String discount) throws IOException {

        JSONObject jsonObject = new JSONObject();

        // 이미 db에 등록된 차량인지 확인
        Optional<Discount> original_discount = discountService.findDiscountByCarNo(carNo);

        if(original_discount.isPresent()){
            original_discount.get().setDiscount(discount);
            discountService.save(original_discount.get());
            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }

    // 할인가 description 수정하기 api
    @RequestMapping(value = "/admin/description/{carNo}/{description}", produces = "application/json; charset=UTF-8", method = RequestMethod.PUT)
    @ResponseBody
    public void update_description(HttpServletResponse res, @PathVariable String carNo, @PathVariable String description) throws IOException {

        JSONObject jsonObject = new JSONObject();

        // 이미 db에 등록된 차량인지 확인
        Optional<Discount> original_discount = discountService.findDiscountByCarNo(carNo);

        if(original_discount.isPresent()){
            original_discount.get().setDescription(description);
            discountService.save(original_discount.get());
            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }

    // 할인가 삭제하기 api
    @GetMapping("/admin/discount/delete/{carNo}")
    @ResponseBody
    public void delete_discount(HttpServletResponse res, @PathVariable String carNo) throws IOException {

        JSONObject jsonObject = new JSONObject();

        // 이미 db에 등록된 차량인지 확인
        Optional<Discount> original_discount = discountService.findDiscountByCarNo(carNo);

        if(original_discount.isPresent()){
            discountService.delete(original_discount.get());
            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }



    // 모렌 예약 확정,취소,수정
//    @RequestMapping(value = "/moren/reservation/apply/{reservationId}", produces = "application/json; charset=UTF-8", method = RequestMethod.PUT)
    @PutMapping("/moren/reservation/apply/{reservationId}")
    @ResponseBody
    @Transactional
    public void moren_reserve(HttpServletResponse res, @RequestBody MorenReservationDTO dto, @PathVariable Long reservationId) throws Exception {

        JSONObject jsonObject_return = new JSONObject();
        MorenReservation morenReservation = null;

        Optional<MorenReservation> morenReservationOptional = morenReservationService.findMorenReservationById(reservationId);
        if(morenReservationOptional.isPresent()){
            morenReservation = morenReservationOptional.get();
        }

        // 계약타입
        String contractType;
        if (dto.getRentTerm().equals("한달")) {
            contractType = "3"; //월렌트
        } else {
            contractType = "4"; //장기렌트
        }

        // 대여 날짜, 반납 날짜
        String orderStartTime = dto.getReservationDate() + " " + dto.getReservationTime();
        String addReservationDate = null;
        String contractTerm = null;
        switch (dto.getRentTerm()) {
            case "한달":
                addReservationDate = AddDate(dto.getReservationDate(), 0, 1, 0);
                contractTerm = "1";
                break;
            case "12개월":
                addReservationDate = AddDate(dto.getReservationDate(), 1, 0, 0);
                contractTerm = "12";
                break;
            case "24개월":
                addReservationDate = AddDate(dto.getReservationDate(), 2, 0, 0);
                contractTerm = "24";
                break;
        }
        String orderEndTime = addReservationDate + " " + dto.getReservationTime();

        if(morenReservation.getReservationStatus().equals("0") && dto.getReservationStatus().equals("1")) {

            try {
                URL url = new URL(request_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                JSONObject morenJsonObject = new JSONObject();
                morenJsonObject.put("COMPANY_ID", "1343");
                morenJsonObject.put("CAR_NUM", dto.getCarNo());
                morenJsonObject.put("CAR_CODE", dto.getCarCode());
                morenJsonObject.put("ORDER_TYPE", "new");
                morenJsonObject.put("ORDER_CONTRACT_TYPE", contractType);
                morenJsonObject.put("ORDER_CUSTOMER_NAME", dto.getReservationName());
                morenJsonObject.put("ORDER_CUSTOMER_PHONE", dto.getReservationPhone());
                morenJsonObject.put("ORDER_CUSTOMER_BIRTH", dto.getReservationAge());
                morenJsonObject.put("ORDER_START_TIME", orderStartTime);
                morenJsonObject.put("ORDER_END_TIME", orderEndTime);
                morenJsonObject.put("ORDER_DELIVERY_PLACE", dto.getAddress());
                morenJsonObject.put("ORDER_DELIVERY_PLACE_EXTRA", dto.getAddressDetail());
                morenJsonObject.put("ORDER_CUSTOMER_MEMO", dto.getReservationDetails());
                morenJsonObject.put("ORDER_PRICE", dto.getCarAmountTotal());
                morenJsonObject.put("ORDER_PRICE_TAX", "0");
                morenJsonObject.put("ORDER_DEPOSIT", dto.getCarDeposit());
                morenJsonObject.put("ORDER_CONTRACT_TERM", contractTerm);
                morenJsonObject.put("ORDER_EXTRA_DISTANCE", dto.getKilometer().split("km")[0]);
                morenJsonObject.put("ORDER_EXTRA_DISTANCE_PRICE", dto.getCostPerKm());
                morenJsonObject.put("ORDER_CDW", "1");

                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);

                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(conn.getOutputStream()));
                printWriter.write(morenJsonObject.toString());
                printWriter.flush();

                // 응답
                BufferedReader bufferedReader;
                int status = conn.getResponseCode();
                if (status == HttpURLConnection.HTTP_OK) {
                    bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }

                String line;
                StringBuffer response = new StringBuffer();

                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                bufferedReader.close();
                System.out.println("응답값 : " + response);

                String[] splited_response = getTwoHundredStrings(response, "\"");
                morenReservation.setOrderCode(splited_response[9]);

                Message coolsms = new Message(api_key, api_secret);
                HashMap<String, String> params = new HashMap<>();
                HashMap<String, String> params2 = new HashMap<>();

                /* 세이브카에 예약확인 문자 전송 */
                params.put("to", "01058283328, 01033453328, 01052774113");
                params.put("from", "01052774113");
                params.put("type", "LMS");

                /* 고객에게 예약확인 문자 전송 */
                params2.put("to", dto.getReservationPhone());
                params2.put("from", "01052774113");
                params2.put("type", "LMS");

                String delivery_text;
                if (dto.getPickupPlace().equals("방문")) {

                    delivery_text = "방문/배차: " + dto.getPickupPlace() + "\n";
                } else {
                    delivery_text = "방문/배차: " + dto.getPickupPlace() + "\n"
                            + "배차요청주소: " + dto.getAddress() + "\n"
                            + "배차요청상세주소: " + dto.getAddressDetail() + "\n";
                }

                params.put("text", "[실시간 예약 확정 처리 완료]\n"
                        + "문의자 이름: " + dto.getReservationName() + "\n"
                        + "연락처: " + dto.getReservationPhone() + "\n"
                        + "차량번호: " + dto.getCarNo() + "\n"
                        + "대여일자: " + dto.getReservationDate() + "\n"
                        + "대여시간: " + dto.getReservationTime() + "\n"
                        + "렌트기간: " + dto.getRentTerm() + "\n"
                        + "약정주행거리: " + dto.getKilometer() + "\n"
                        + delivery_text
                        + "생년월일: " + dto.getReservationAge() + "\n"
                        + "총렌트료(부포): " + dto.getCarAmountTotal() + "\n"
                        + "보증금: " + dto.getCarDeposit() + "\n"
                        + "요청사항: " + dto.getReservationDetails() + "\n\n");

                params2.put("text", "[세이브카 렌트카 예약이 확정되었습니다]" + "\n"
                        + "성함: " + dto.getReservationName() + "\n"
                        + "연락처: " + dto.getReservationPhone() + "\n"
                        + "차량번호: " + dto.getCarNo() + "\n"
                        + "대여일자: " + dto.getReservationDate() + "\n"
                        + "렌트기간: " + dto.getRentTerm() + "\n"
                        + "약정주행거리: " + dto.getKilometer() + "\n"
                        + delivery_text
                        + "총렌트료: " + dto.getCarAmountTotal() + "\n"
                        + "보증금: " + dto.getCarDeposit() + "\n"
                        + "요청사항: " + dto.getReservationDetails() + "\n\n"

                        + "* 운전면허증을 촬영하여 문자로 보내주시기 바랍니다.\n");

                params.put("app_version", "test app 1.2");
                params2.put("app_version", "test app 1.2");

                /* 세이브카에게 문자 전송 */
                try {
                    org.json.simple.JSONObject obj = coolsms.send(params);
                    System.out.println(obj.toString()); //전송 결과 출력
                } catch (CoolsmsException e) {
                    System.out.println(e.getMessage());
                    System.out.println(e.getCode());
                }

                /* 고객에게 예약확인 문자 전송 */
                try {
                    org.json.simple.JSONObject obj2 = coolsms.send(params2);
                    System.out.println(obj2.toString()); //전송 결과 출력
                } catch (CoolsmsException e) {
                    System.out.println(e.getMessage());
                    System.out.println(e.getCode());
                }

                jsonObject_return.put("result", 1);

            } catch (Exception e) {
                System.out.println(e);
                jsonObject_return.put("result", 0);
            }

            morenReservation.setReservationStatus("1");

        } else if (morenReservation.getReservationStatus().equals("1") && dto.getReservationStatus().equals("0")){

            try {
                URL url = new URL(request_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                JSONObject morenJsonObject = new JSONObject();
                morenJsonObject.put("COMPANY_ID", "1343");
                morenJsonObject.put("CAR_NUM", dto.getCarNo());
                morenJsonObject.put("CAR_CODE", dto.getCarCode());
                morenJsonObject.put("ORDER_TYPE", "cancel");
                morenJsonObject.put("ORDER_CUSTOMER_NAME", dto.getReservationName());
                morenJsonObject.put("ORDER_CUSTOMER_PHONE", dto.getReservationPhone());
                morenJsonObject.put("ORDER_CODE", dto.getOrderCode());

                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);

                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(conn.getOutputStream()));
                printWriter.write(morenJsonObject.toString());
                printWriter.flush();

                // 응답
                BufferedReader bufferedReader;
                int status = conn.getResponseCode();
                if (status == HttpURLConnection.HTTP_OK) {
                    bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }

                String line;
                StringBuffer response = new StringBuffer();

                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                bufferedReader.close();
                System.out.println("응답값 : " + response);


                Message coolsms = new Message(api_key, api_secret);
                HashMap<String, String> params = new HashMap<>();
                HashMap<String, String> params2 = new HashMap<>();

                /* 세이브카에 예약확인 문자 전송 */
                params.put("to", "01058283328"); // 01033453328 추가
                params.put("from", "01052774113");
                params.put("type", "LMS");

                /* 고객에게 예약확인 문자 전송 */
                params2.put("to", morenReservation.getReservationPhone()); // 여러가지 번호형태 테스트
                params2.put("from", "01052774113");
                params2.put("type", "LMS");

                String delivery_text = "";
                if (morenReservation.getPickupPlace().equals("방문")){
                    delivery_text = "방문/배차: " + morenReservation.getPickupPlace() + "\n";
                } else {
                    delivery_text = "방문/배차: " + morenReservation.getPickupPlace() + "\n"
                            + "배차요청주소: " + morenReservation.getAddress() + "\n"
                            + "배차요청상세주소: " + morenReservation.getAddressDetail() + "\n";
                }

                params.put("text", "[실시간 예약 취소 처리 완료]\n"
                        + "문의자 이름: " + morenReservation.getReservationName() + "\n"
                        + "연락처: " + morenReservation.getReservationPhone() + "\n"
                        + "차량번호: " + morenReservation.getCarNo() + "\n"
                        + "대여일자: " + morenReservation.getReservationDate() + "\n"
                        + "대여시간: " + morenReservation.getReservationTime() + "\n"
                        + "렌트기간: " + morenReservation.getRentTerm() + "\n"
                        + "약정주행거리: " + morenReservation.getKilometer() + "\n"
                        + delivery_text
                        + "생년월일: " + morenReservation.getReservationAge() + "\n"
                        + "신용증빙: " + morenReservation.getReservationGuarantee() + "\n"
                        + "총렌트료(부포): " + morenReservation.getCarAmountTotal() + "\n"
                        + "보증금: " + morenReservation.getCarDeposit() + "\n"
                        + "요청사항: " + morenReservation.getReservationDetails() + "\n\n");

                params2.put("text", "[세이브카 렌트카 예약이 취소되었습니다]" + "\n"
                        + "문의자 이름: " + morenReservation.getReservationName() + "\n"
                        + "연락처: " + morenReservation.getReservationPhone() + "\n"
                        + "차량번호: " + morenReservation.getCarNo() + "\n"
                        + "대여일자: " + morenReservation.getReservationDate() + "\n"
                        + "렌트기간: " + morenReservation.getRentTerm() + "\n"
                        + "약정주행거리: " + morenReservation.getKilometer() + "\n"
                        + delivery_text
                        + "기타증빙사항: " + morenReservation.getReservationGuarantee() + "\n"
                        + "총렌트료: " + morenReservation.getCarAmountTotal() + "\n"
                        + "보증금: " + morenReservation.getCarDeposit() + "\n"
                        + "요청사항: " + morenReservation.getReservationDetails() + "\n\n");
                params.put("app_version", "test app 1.2");
                params2.put("app_version", "test app 1.2");

                /* 세이브카에게 문자 전송 */
                try {
                    org.json.simple.JSONObject obj = coolsms.send(params);
                    System.out.println(obj.toString()); //전송 결과 출력
                } catch (CoolsmsException e) {
                    System.out.println(e.getMessage());
                    System.out.println(e.getCode());
                }

                /* 고객에게 예약확인 문자 전송 */
                try {
                    org.json.simple.JSONObject obj2 = coolsms.send(params2);
                    System.out.println(obj2.toString()); //전송 결과 출력
                } catch (CoolsmsException e) {
                    System.out.println(e.getMessage());
                    System.out.println(e.getCode());
                }

                jsonObject_return.put("result", 1);

            } catch (Exception e) {
                System.out.println(e);
                jsonObject_return.put("result", 0);
            }

            morenReservation.setReservationStatus("0");

        } else {

            if(dto.getReservationStatus().equals("1")) {

                try {
                    URL url = new URL(request_url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    JSONObject morenJsonObject = new JSONObject();
                    morenJsonObject.put("COMPANY_ID", "1343");
                    morenJsonObject.put("CAR_NUM", dto.getCarNo());
                    morenJsonObject.put("CAR_CODE", dto.getCarCode());
                    morenJsonObject.put("ORDER_TYPE", "update");
                    morenJsonObject.put("ORDER_CONTRACT_TYPE", contractType);
                    morenJsonObject.put("ORDER_CUSTOMER_NAME", dto.getReservationName());
                    morenJsonObject.put("ORDER_CUSTOMER_PHONE", dto.getReservationPhone());
                    morenJsonObject.put("ORDER_CUSTOMER_BIRTH", dto.getReservationAge());
                    morenJsonObject.put("ORDER_START_TIME", orderStartTime);
                    morenJsonObject.put("ORDER_END_TIME", orderEndTime);
                    morenJsonObject.put("ORDER_DELIVERY_PLACE", dto.getAddress());
                    morenJsonObject.put("ORDER_DELIVERY_PLACE_EXTRA", dto.getAddressDetail());
                    morenJsonObject.put("ORDER_CUSTOMER_MEMO", dto.getReservationDetails());
                    morenJsonObject.put("ORDER_PRICE", dto.getCarAmountTotal());
                    morenJsonObject.put("ORDER_PRICE_TAX", "0");
                    morenJsonObject.put("ORDER_DEPOSIT", dto.getCarDeposit());
                    morenJsonObject.put("ORDER_CONTRACT_TERM", contractTerm);
                    morenJsonObject.put("ORDER_EXTRA_DISTANCE", dto.getKilometer().split("km")[0]);
                    morenJsonObject.put("ORDER_EXTRA_DISTANCE_PRICE", dto.getCostPerKm());
                    morenJsonObject.put("ORDER_CDW", "1");

                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json; utf-8");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);

                    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(conn.getOutputStream()));
                    printWriter.write(morenJsonObject.toString());
                    printWriter.flush();

                    // 응답
                    BufferedReader bufferedReader;
                    int status = conn.getResponseCode();
                    if (status == HttpURLConnection.HTTP_OK) {
                        bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    } else {
                        bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    }

                    String line;
                    StringBuffer response = new StringBuffer();

                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    bufferedReader.close();
                    System.out.println("응답값 : " + response);

                    jsonObject_return.put("result", 1);

                } catch (Exception e) {
                    System.out.println(e);
                    jsonObject_return.put("result", 0);
                }
            }

            morenReservation.setCarNo(dto.getCarNo());
            morenReservation.setKilometer(dto.getKilometer());
            morenReservation.setReservationName(dto.getReservationName());
            morenReservation.setReservationPhone(dto.getReservationPhone());
            morenReservation.setReservationAge(dto.getReservationAge());
            morenReservation.setReservationDate(dto.getReservationDate());
            morenReservation.setReservationTime(dto.getReservationTime());
            morenReservation.setReservationGuarantee(dto.getReservationGuarantee());
            morenReservation.setReservationDetails(dto.getReservationDetails());
            morenReservation.setAddress(dto.getAddress());
            morenReservation.setAddressDetail(dto.getAddressDetail());
            morenReservation.setCarPrice(dto.getCarPrice());
            morenReservation.setCarTax(dto.getCarTax());
            morenReservation.setCarAmountTotal(dto.getCarAmountTotal());
            morenReservation.setCarDeposit(dto.getCarDeposit());
            morenReservation.setReservationStatus(dto.getReservationStatus());
            morenReservation.setRentTerm(dto.getRentTerm());
            morenReservation.setCostPerKm(dto.getCostPerKm());
            morenReservation.setCarCode(dto.getCarCode());
            morenReservation.setPickupPlace(dto.getPickupPlace());
            morenReservation.setCarName(dto.getCarName());

            jsonObject_return.put("result", 1);
        }

        morenReservationService.save(morenReservation);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject_return);
        pw.flush();
        pw.close();
    }

    // 모렌 reservation 삭제 api
    @DeleteMapping("/moren/reservation/{reservationId}")
    @ResponseBody
    public void delete_moren_reservation(HttpServletResponse res, @PathVariable Long reservationId) throws IOException {

        JSONObject jsonObject = new JSONObject();

        Optional<MorenReservation> morenReservationOptional = morenReservationService.findMorenReservationById(reservationId);
        if(morenReservationOptional.isPresent()){
            morenReservationService.delete(morenReservationOptional.get());
            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }

//    @RequestMapping(value = "/admin/setting", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @PostMapping("/admin/setting")
    @ResponseBody
    public void post_admin_setting(HttpServletResponse res, @RequestBody ExplanationDTO explanationDTO) throws IOException {

        Optional<Explanation> explanation_optional = explanationService.findById(Long.valueOf(0));
        Explanation explanation = explanation_optional.get();
        explanation.setCamper_price(explanationDTO.getCamper_price());
        explanation.setEurope_basic_option(explanationDTO.getEurope_basic_option());
        explanation.setLimousine_basic_option(explanationDTO.getLimousine_basic_option());
        explanation.setTravel_basic_option(explanationDTO.getTravel_basic_option());
        explanation.setEurope_facility(explanationDTO.getEurope_facility());
        explanation.setLimousine_facility(explanationDTO.getLimousine_facility());
        explanation.setTravel_facility(explanationDTO.getTravel_facility());
        explanation.setRent_policy(explanationDTO.getRent_policy());
        explanation.setRent_insurance(explanationDTO.getRent_insurance());
        explanation.setRent_rule(explanationDTO.getRent_rule());
        explanation.setRefund_policy(explanationDTO.getRefund_policy());
        explanation.setDriver_license(explanationDTO.getDriver_license());

        explanationService.save(explanation);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }

    //    @RequestMapping(value = "/admin/campingcar/price", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @PostMapping("/admin/campingcar/price")
    @ResponseBody
    public void post_admin_campingcar_price(HttpServletResponse res, @RequestBody ExplanationDTO explanationDTO) throws IOException {

        Optional<Explanation> explanation_optional = explanationService.findById(Long.valueOf(0));
        Explanation explanation = explanation_optional.get();
        explanation.setCamper_price(explanationDTO.getCamper_price());
        explanation.setEurope_basic_option(explanationDTO.getEurope_basic_option());
        explanation.setLimousine_basic_option(explanationDTO.getLimousine_basic_option());
        explanation.setTravel_basic_option(explanationDTO.getTravel_basic_option());
        explanation.setEurope_facility(explanationDTO.getEurope_facility());
        explanation.setLimousine_facility(explanationDTO.getLimousine_facility());
        explanation.setTravel_facility(explanationDTO.getTravel_facility());
        explanation.setRent_policy(explanationDTO.getRent_policy());
        explanation.setRent_insurance(explanationDTO.getRent_insurance());
        explanation.setRent_rule(explanationDTO.getRent_rule());
        explanation.setRefund_policy(explanationDTO.getRefund_policy());
        explanation.setDriver_license(explanationDTO.getDriver_license());

        explanationService.save(explanation);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }
}
