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


    //예약 목록 조회 api
    @GetMapping("/reservation/list")
    public String reservation_list(Model model) {
        List<Reservation> reservationList = reservationService.findAllReservations();
        model.addAttribute("reservationList", reservationList);

        return "reservation_list";
    }

    // 예약 저장 api
    @PostMapping("/reservation/apply")
    @ResponseBody
    public Long save(@RequestBody ReservationSaveDto dto){

        String api_key = "NCS0P5SFAXLOJMJI";
        String api_secret = "FLLGUBZ7OTMQOXFSVE6ZWR2E010UNYIZ";
        Message coolsms = new Message(api_key, api_secret);
        HashMap<String, String> params = new HashMap<String, String>();
        HashMap<String, String> params2 = new HashMap<String, String>();


        /* 세이브카에 예약확인 문자 전송 */
        params.put("to", "01058283328, 01033453328, 01052774113"); // 01033453328 추가
        params.put("from", "01052774113");
        params.put("type", "LMS");


        /* 고객에게 예약확인 문자 전송 */

        params2.put("to", dto.getPhoneNo());
        params2.put("from", "01052774113");  // 16613331 테스트하기
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

//            CalendarTime calendarRentTime = calendarTimeService.findCalendarTimeByDateIdAndCarNameAndReserveTime(calendarDate, campingCarPrice, campingcarDateTime.getRentTime());
//            Integer return_total_time = Integer.parseInt(campingcarDateTime.getReturnTime()) + Integer.parseInt(campingcarDateTime.getExtraTime());
//            CalendarTime calendarReturnTime = calendarTimeService.findCalendarTimeByDateIdAndCarNameAndReserveTime(returnCalendarDate, campingCarPrice, Integer.toString(return_total_time));


//            if (calendarReturnTime.getTimeId().equals("17시")){
//                for (Long i = calendarRentTime.getTimeId(); i <= calendarReturnTime.getTimeId(); i++){
//
//
//                }
//            }



//            for (Long i = calendarDate.getDateId(); i <= returnCalendarDate.getDateId(); i++) {
//                // CalendarDate 날짜 객체 가져오기
//                CalendarDate calendarDateIndiv = calendarDateService.findCalendarDateByDateId(i);
//
//                if(i == returnCalendarDate.getDateId()) {
//
//                } else {
//                    // CalendarTime에서 날짜로 시간 정보 찾기
//                    List<CalendarTime> calendarTime = calendarTimeService.findCalendarTimeByDateIdAndCarName(calendarDateIndiv, campingCarPrice);
//
//                    for (int j = 0; j < calendarTime.size(); j++) {
//                        calendarTime.get(j).setReserve_complete("1");
//                    }
//                }
//                // DateCamping 에서 날짜랑 차정보로 하루 예약 정보 찾기
//                DateCamping dateCamping = dateCampingService.findByDateIdAndCarName(calendarDateIndiv, campingCarPrice);
//                // DateCamping 하루 예약 정보 수정, campingcarDateTime 예약리스트 예약 정보 수정
//                dateCamping.setReserved("1");
//            }


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
            out.println("<script>alert('예약이 확정되었습니다.'); </script>");
            out.flush();


        } catch (NullPointerException e){
            // 예약 실패. alert
            res.setContentType("text/html; charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('이용할 수 없는 날짜입니다.'); </script>");
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
