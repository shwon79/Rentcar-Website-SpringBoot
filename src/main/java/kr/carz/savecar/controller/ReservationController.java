package kr.carz.savecar.controller;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.*;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@Controller
public class ReservationController {
    MonthlyRentService monthlyRentService;
    YearlyRentService yearlyRentService;
    ShortRentService shortRentService;
    CampingCarService campingCarService;
    CalendarDateService calendarDateService;
    DateCampingService dateCampingService;
    CampingcarDateTimeService2 campingcarDateTimeService2;
    LoginService loginService;
    CampingCarPriceService campingCarPriceService;
    ReservationService reservationService;
    CalendarTimeService calendarTimeService;


    @Autowired
    public ReservationController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService,
                                 ShortRentService shortRentService, CampingCarService campingCarService, CalendarDateService calendarDateService,
                                 DateCampingService dateCampingService, CampingcarDateTimeService2 campingcarDateTimeService2,
                                 LoginService loginService, CampingCarPriceService campingCarPriceService, ReservationService reservationService,
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
        this.reservationService = reservationService;
        this.calendarTimeService = calendarTimeService;
    }

    private String phone_to = "01058283328, 01033453328, 01052774113";
    private String phone_from = "01052774113";



    //예약 목록 조회 api
    @GetMapping("/reservation/list")
    public String reservation_list(Model model) {
        List<Reservation> reservationList = reservationService.findAllReservations();
        model.addAttribute("reservationList", reservationList);

        return "reservation_list";
    }

    // 상담신청 예약 저장 및 문자발송 api
    @PostMapping("/reservation/apply")
    @ResponseBody
    public Long save(@RequestBody ReservationSaveDto dto){

        String api_key = "NCS0P5SFAXLOJMJI";
        String api_secret = "FLLGUBZ7OTMQOXFSVE6ZWR2E010UNYIZ";
        Message coolsms = new Message(api_key, api_secret);
        HashMap<String, String> params = new HashMap<String, String>();
        HashMap<String, String> params2 = new HashMap<String, String>();


        /* 세이브카에 예약확인 문자 전송 */
        params.put("to", phone_to); // 01033453328 추가
        params.put("from", "01052774113");
        params.put("type", "LMS");


        /* 고객에게 예약확인 문자 전송 */

        params2.put("to", dto.getPhoneNo());
        params2.put("from", phone_from);  // 16613331 테스트하기
        params2.put("type", "LMS");


        if (dto.getTitle().equals("간편상담신청")){
            params.put("text", "[" + dto.getTitle() + "]\n"
                    + "문의자 이름: " + dto.getName() + "\n"
                    + "연락처: " + dto.getPhoneNo() + "\n"
                    + "차량명: " + dto.getCar_name() + "\n"
                    + "지역: " + dto.getMileage() + "\n"
                    + "예상대여일자: " + dto.getOption() + "\n"
                    + "요청사항: " + dto.getDetail() + "\n\n");

            params2.put("text", "[상담신청이 완료되었습니다]" + "\n"
                    + "문의자 이름: " + dto.getName() + "\n"
                    + "차량명: " + dto.getCar_name() + "\n"
                    + "지역: " + dto.getMileage() + "\n"
                    + "예상대여일자: " + dto.getOption() + "\n"
                    + "요청사항: " + dto.getDetail() + "\n\n");
        }
        else if (dto.getTitle().equals("월렌트, 12개월렌트")){
            params.put("text", "[" + dto.getTitle() + "]\n"
                    + "예약자 이름: " + dto.getName() + "\n"
                    + "연락처: " + dto.getPhoneNo() + "\n"
                    + "요청사항: " + dto.getDetail() + "\n\n"
                    + "렌트상품: " + dto.getProduct() + "\n"
                    + "차종: " + dto.getCategory1() + "\n"
                    + "차분류: " + dto.getCategory2() + "\n"
                    + "차명: " + dto.getCar_name() + "\n"
                    + "주행거리: " + dto.getMileage() + "\n"
                    + "사이트에서 조회된 렌트료: " + dto.getPrice() + "\n");

            params2.put("text", "[상담신청이 완료되었습니다]" + "\n"
                    + "예약자 이름: " + dto.getName() + "\n"
                    + "요청사항: " + dto.getDetail() + "\n\n"
                    + "렌트상품: " + dto.getProduct() + "\n"
                    + "차종: " + dto.getCategory1() + "\n"
                    + "차분류: " + dto.getCategory2() + "\n"
                    + "차명: " + dto.getCar_name() + "\n"
                    + "주행거리: " + dto.getMileage() + "\n"
                    + "사이트에서 조회된 렌트료: " + dto.getPrice() + "\n");
        }
        else if (dto.getTitle().equals("누구나장기렌트")){
            params.put("text", "[" + dto.getTitle() + "]\n"
                    + "예약자 이름: " + dto.getName() + "\n"
                    + "연락처: " + dto.getPhoneNo() + "\n"
                    + "요청사항: " + dto.getDetail() + "\n\n"
                    + "렌트상품: " + dto.getProduct() + "\n"
                    + "차종: " + dto.getCategory2() + "\n"
                    + "차명: " + dto.getCar_name() + "\n"
                    + "옵션: " + dto.getOption() + "\n"
                    + "약정주행거리: " + dto.getMileage() + "\n"
                    + "보증금: " + dto.getDeposit() + "\n");

            params2.put("text", "[상담신청이 완료되었습니다]" + "\n"
                    + "예약자 이름: " + dto.getName() + "\n"
                    + "요청사항: " + dto.getDetail() + "\n\n"
                    + "렌트상품: " + dto.getProduct() + "\n"
                    + "차종: " + dto.getCategory2() + "\n"
                    + "차명: " + dto.getCar_name() + "\n"
                    + "옵션: " + dto.getOption() + "\n"
                    + "약정주행거리: " + dto.getMileage() + "\n"
                    + "보증금: " + dto.getDeposit() + "\n");
        }
        else {
            params.put("text", "[" + dto.getTitle() + "]\n"
                    + "예약자 이름: " + dto.getName() + "\n"
                    + "연락처: " + dto.getPhoneNo() + "\n"
                    + "요청사항: " + dto.getDetail() + "\n\n"
                    + "렌트상품: 캠핑카 - " + dto.getProduct() + "\n");

            params2.put("text", "[상담신청이 완료되었습니다]" + "\n"
                    + "예약자 이름: " + dto.getName() + "\n"
                    + "요청사항: " + dto.getDetail() + "\n\n"
                    + "렌트상품: 캠핑카 - " + dto.getProduct() + "\n");
        }
        params.put("app_version", "test app 1.2");
        params2.put("app_version", "test app 1.2");


        /* 세이브카에게 문자 전송 */

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString()); //전송 결과 출력
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }

        /* 고객에게 예약확인 문자 전송 */

        try {
            JSONObject obj2 = (JSONObject) coolsms.send(params2);
            System.out.println(obj2.toString()); //전송 결과 출력
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }

        return reservationService.save(dto);
    }



    // 캠핑카 예약 대기 데이터 저장 및 문자발송 api
    @PostMapping("/campingcar/reserve")
    @ResponseBody
    public String save(HttpServletResponse res, @RequestBody CampingcarDateTime2 dto)  throws IOException {


        System.out.println(dto.getRentDate());
        System.out.println(dto.getRentTime());
        System.out.println(dto.getReturnDate());
        System.out.println(dto.getReturnTime());
        System.out.println(dto.getAgree()); // 1
        System.out.println(dto.getDeposit());
        System.out.println(dto.getDepositor());
        System.out.println(dto.getDetail());
        System.out.println(dto.getName());
        System.out.println(dto.getPhone());
        System.out.println(dto.getReservation()); // 1
        System.out.println(dto.getTotal());
        System.out.println(dto.getDay());

        try {
            // 대여일자, 대여시간
            String[] rent_date = dto.getRentDate().split("월 ");
            String rent_month = rent_date[0];
            System.out.println(rent_month);

            String[] rent_day_list = rent_date[1].split("일");
            String rent_day = rent_day_list[0];
            System.out.println(rent_day);


            // 반납일자, 반납시간
            String[] return_date = dto.getReturnDate().split("월 ");
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
            CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(dto.getCarType());

            CalendarTime calendarRentTime = calendarTimeService.findCalendarTimeByDateIdAndCarNameAndReserveTime(calendarDate, campingCarPrice, dto.getRentTime());
            CalendarTime calendarReturnTime = calendarTimeService.findCalendarTimeByDateIdAndCarNameAndReserveTime(returnCalendarDate, campingCarPrice, dto.getReturnTime());


            if (calendarReturnTime.getReserveTime().equals("17시")){

                for (Long i = calendarRentTime.getTimeId(); i <= calendarReturnTime.getTimeId(); i++){
                    CalendarTime timeIndiv = calendarTimeService.findCalendarTimeByTimeId(i);
                    if (timeIndiv.getReserveComplete().equals("1")){
                        throw new Exception();
                    } else {
                        timeIndiv.setReserveComplete("1");
                    }
                }
            } else {
                for (Long i = calendarRentTime.getTimeId(); i <= calendarReturnTime.getTimeId() + 1; i++){
                    CalendarTime timeIndiv = calendarTimeService.findCalendarTimeByTimeId(i);
                    if (timeIndiv.getReserveComplete().equals("1")){
                        throw new Exception();
                    } else {
                        timeIndiv.setReserveComplete("1");
                    }
                }
            }



            for (Long i = calendarDate.getDateId(); i <= returnCalendarDate.getDateId(); i++) {
                // CalendarDate 날짜 객체 가져오기
                CalendarDate calendarDateIndiv = calendarDateService.findCalendarDateByDateId(i);

                // DateCamping 에서 날짜랑 차정보로 하루 예약 정보 찾기
                DateCamping dateCamping = dateCampingService.findByDateIdAndCarName(calendarDateIndiv, campingCarPrice);
                // DateCamping 하루 예약 정보 수정, campingcarDateTime 예약리스트 예약 정보 수정
                if (dateCamping.getReserved().equals("1")){
                    throw new Exception();
                } else {
                    dateCamping.setReserved("1");
                }
            }


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
            params.put("to", phone_to); // 01033453328 추가
            params.put("from", "01052774113");
            params.put("type", "LMS");


            /* 고객에게 예약확인 문자 전송 */

            params2.put("to", dto.getPhone());
            params2.put("from", phone_from);  // 16613331 테스트하기
            params2.put("type", "LMS");

            params.put("text", "[캠핑카 실시간 예약]\n"
                    + "성함: " + dto.getName() + "\n"
                    + "전화번호: " + dto.getPhone() + "\n"
                    + "차량명: " + dto.getCarType() + "\n"
                    + "입금자명: " + dto.getDepositor() + "\n"
                    + "대여날짜: " + dto.getRentDate() + "\n"
                    + "대여시간: " + dto.getRentTime() + "\n"
                    + "반납날짜: " + dto.getReturnDate() + "\n"
                    + "반납시간: " + dto.getReturnTime() + "\n"
                    + "이용날짜: " + dto.getDay() + "\n"
                    + "총금액: " + dto.getTotal() + "\n"
                    + "선결제금액: " + dto.getTotalHalf() + "\n"
                    + "요청사항: " + dto.getDetail() + "\n\n");

            params2.put("text", "[캠핑카 예약 대기 신청이 완료되었습니다.]" + "\n"
                    + "성함: " + dto.getName() + "\n"
                    + "전화번호: " + dto.getPhone() + "\n"
                    + "차량명: " + dto.getCarType() + "\n"
                    + "대여날짜: " + dto.getRentDate() + "\n"
                    + "대여시간: " + dto.getRentTime() + "\n"
                    + "반납날짜: " + dto.getReturnDate() + "\n"
                    + "반납시간: " + dto.getReturnTime() + "\n"
                    + "입금자명: " + dto.getDepositor() + "\n"
                    + "이용날짜: " + dto.getDay() + "\n"
                    + "총금액: " + dto.getTotal() + "\n"
                    + "선결제금액: " + dto.getTotalHalf() + "\n"
                    + "요청사항: " + dto.getDetail() + "\n\n"
                    + "* 예약 확정 시 담당자가 따로 안내연락드립니다." + "\n\n");


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


            campingcarDateTimeService2.save2(dto);




        } catch (Exception e) {
            e.printStackTrace();

            // 예약 실패. alert
            res.setContentType("text/html; charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('이용할 수 없는 날짜입니다.'); location.href='/admin/main'; </script>");
            out.flush();
        }

        return "paying";
    }



    //캠핑카 예약 확정 문자 발송 api
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
            params.put("to", phone_to); // 01033453328 추가
            params.put("from", phone_from);
            params.put("type", "LMS");


            /* 고객에게 예약확인 문자 전송 */

            params2.put("to", campingcarDateTime.getPhone());
            params2.put("from", phone_from);  // 16613331 테스트하기
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
                URL url = new URL("http://savecar.kr");
                HttpURLConnection http = (HttpURLConnection) url.openConnection();

                AdminController admin = new AdminController(http);

                org.json.JSONObject jsonData = new org.json.JSONObject();
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


    // 상담신청 예약 취소 및 문자발송 api
    @GetMapping("/reservation/cancel/{date_time_id}")
    @ResponseBody
    public void cancel(@PathVariable Long date_time_id){

        String api_key = "NCS0P5SFAXLOJMJI";
        String api_secret = "FLLGUBZ7OTMQOXFSVE6ZWR2E010UNYIZ";
        Message coolsms = new Message(api_key, api_secret);
        HashMap<String, String> params = new HashMap<String, String>();
        HashMap<String, String> params2 = new HashMap<String, String>();

        CampingcarDateTime2 campingcarDateTime = campingcarDateTimeService2.findByDateTimeId(date_time_id);


        /* 세이브카에 예약확인 문자 전송 */
        params.put("to", phone_to); // 01033453328 추가
        params.put("from", "01052774113");
        params.put("type", "LMS");


        /* 고객에게 예약확인 문자 전송 */
        params2.put("to", campingcarDateTime.getPhone());
        params2.put("from", phone_from);  // 16613331 테스트하기
        params2.put("type", "LMS");


        params.put("text", "[" + campingcarDateTime.getName() + "님의 캠핑카 예약이 취소되었습니다]" + "\n"
                + "문의자 이름: " + campingcarDateTime.getName() + "\n"
                + "예약 신청일: " + campingcarDateTime.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "\n"
                + "대여 시작일: " + campingcarDateTime.getRentDate() + "\n"
                + "대여권: " + campingcarDateTime.getDay() + "\n"
                + "차량명: " + campingcarDateTime.getCarType() + "\n"
                + "전화번호: " + campingcarDateTime.getPhone() + "\n\n");

        params2.put("text", "[" + campingcarDateTime.getName() + "님의 캠핑카 예약이 취소되었습니다]" + "\n"
                + "예약 신청일: " + campingcarDateTime.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "\n"
                + "차량명: " + campingcarDateTime.getCarType() + "\n"
                + "대여 시작일: " + campingcarDateTime.getRentDate() + "\n"
                + "대여권: " + campingcarDateTime.getDay() + "\n"
//                + "전화번호: " + campingcarDateTime.getPhone() + "\n\n"
        );

        params.put("app_version", "test app 1.2");
        params2.put("app_version", "test app 1.2");


        /* 세이브카에게 문자 전송 */

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString()); //전송 결과 출력
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }

        /* 고객에게 예약확인 문자 전송 */

        try {
            JSONObject obj2 = (JSONObject) coolsms.send(params2);
            System.out.println(obj2.toString()); //전송 결과 출력
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }

    }



}
