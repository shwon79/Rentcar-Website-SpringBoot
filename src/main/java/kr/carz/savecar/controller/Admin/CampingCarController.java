package kr.carz.savecar.controller.Admin;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.dto.*;
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

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Controller
public class CampingCarController {
    private final ExplanationService explanationService;
    private final CampingcarReservationService campingcarReservationService;
    private final CalendarTimeService calendarTimeService;
    private final CampingCarPriceService campingCarPriceService;
    private final CalendarDateService calendarDateService;
    private final DateCampingService dateCampingService;

    @Autowired
    public CampingCarController(ExplanationService explanationService, CampingcarReservationService campingcarReservationService,
                                CalendarTimeService calendarTimeService, CampingCarPriceService campingCarPriceService,
                                CalendarDateService calendarDateService, DateCampingService dateCampingService) {
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



    // [관리자 메인페이지] 캠핑카 예약내역 메뉴로 입장
    @GetMapping(value = "/admin/campingcar/menu")
    @ResponseBody
    public ModelAndView get_admin_main() {

        ModelAndView mav = new ModelAndView();

        List<CampingCarReservation> campingCarReservationList = campingcarReservationService.findAllReservations();
        mav.addObject("campingCarReservationList", campingCarReservationList);
        mav.setViewName("admin/campingcar_menu");

        return mav;
    }

    // [관리자 메인페이지] 캠핑카 예약내역 detail 페이지로 입장
    @GetMapping(value = "/admin/campingcar/detail/{reservationId}")
    @ResponseBody
    public ModelAndView get_admin_campincar_detail(@PathVariable Long reservationId) {

        ModelAndView mav = new ModelAndView();

        Optional<CampingCarReservation> campingCarReservation = campingcarReservationService.findById(reservationId);
        campingCarReservation.ifPresent(carReservation -> mav.addObject("campingCarReservation", carReservation));
        mav.setViewName("admin/campingcar_detail");

        return mav;
    }



    // [관리자 메인페이지] 캠핑카 기본설정 메뉴로 입장
    @GetMapping(value = "/admin/campingcar/setting/menu")
    @ResponseBody
    public ModelAndView get_setting_main() {

        ModelAndView mav = new ModelAndView();
        Optional<Explanation> explanation = explanationService.findById((long) 0);

        explanation.ifPresent(value -> mav.addObject("explanation", value));
        mav.setViewName("admin/campingcar_setting_menu");

        return mav;
    }



    // [관리자 메인페이지] 캠핑카 가격 메뉴로 입장
    @GetMapping(value = "/admin/campingcar/price/menu")
    @ResponseBody
    public ModelAndView get_campingcar_price_main() {

        ModelAndView mav = new ModelAndView();

        List<CampingCarPrice> campingCarPriceList = campingCarPriceService.findCampingCarPrice();

        mav.addObject("campingCarPriceList", campingCarPriceList);
        mav.setViewName("admin/campingcar_price_menu");

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

        CampingCarReservation campingCarReservation = new CampingCarReservation();
        if(campingCarReservationWrapper.isPresent()){
            campingCarReservation = campingCarReservationWrapper.get();
        }


        // 문자전송
        Message coolsms = new Message(api_key, api_secret);
        HashMap<String, String> params = new HashMap<>();
        HashMap<String, String> params2 = new HashMap<>();

        /* 세이브카에 예약확인 문자 전송 */
        params.put("to", admin1+", "+admin2+", "+admin3);
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
                    for (CalendarTime calendarTime : calendarTimeForCheckList) {
                        if (calendarTime.getReserveComplete().equals("1")) {
                            one_cnt += 1;
                        }
                    }

                    if (one_cnt != calendarTimeForCheckList.size()) {
                        if(calendarTimeForCheckList.get(0).getReserveComplete().equals("1")){
                            dateCampingList.get(i).setReserved("2");
                        } else {
                            dateCampingList.get(i).setReserved("1");
                        }
                    } else {
                        dateCampingList.get(i).setReserved("2");
                    }
                } else if (i == dateCampingListSize - 1 && !campingCarReservation.getReturnTime().equals("18시")) {
                    calendarTimeList = calendarTimeService.findByDateIdAndCarNameAndReserveTimeLessThanEqual(dateCamping.getDateId(), campingCarPrice, campingCarReservation.getReturnTime());
                    List<CalendarTime> calendarTimeForCheckList = calendarTimeService.findByDateIdAndCarNameAndReserveTimeGreaterThan(dateCamping.getDateId(), campingCarPrice, campingCarReservation.getRentTime());

                    int one_cnt = 0;
                    for (CalendarTime calendarTime : calendarTimeForCheckList) {
                        if (calendarTime.getReserveComplete().equals("1")) {
                            one_cnt += 1;
                        }
                    }

                    if (one_cnt != calendarTimeForCheckList.size()) {
                        if(calendarTimeForCheckList.get(calendarTimeForCheckList.size()-1).getReserveComplete().equals("1")){
                            dateCampingList.get(i).setReserved("2");
                        } else {
                            dateCampingList.get(i).setReserved("1");
                        }
                    } else {
                        dateCampingList.get(i).setReserved("2");
                    }
                } else {
                    calendarTimeList = calendarTimeService.findCalendarTimeByDateIdAndCarName(dateCamping.getDateId(), campingCarPrice);
                    dateCampingList.get(i).setReserved("2");
                }

                for (CalendarTime calendarTime : calendarTimeList) {
                    calendarTime.setReserveComplete("1");
                    calendarTimeService.save(calendarTime);
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
                    for (CalendarTime calendarTime : calendarTimeForCheckList) {
                        if (calendarTime.getReserveComplete().equals("1")) {
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
                    for (CalendarTime calendarTime : calendarTimeForCheckList) {
                        if (calendarTime.getReserveComplete().equals("1")) {
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

                for (CalendarTime calendarTime : calendarTimeList) {
                    calendarTime.setReserveComplete("0");
                    calendarTimeService.save(calendarTime);
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
                StringBuilder response = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null){
                    response.append(line);
                }
                bufferedReader.close();
                System.out.println("응답값 : " + response);

            } catch (Exception e){
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
                    StringBuilder response = new StringBuilder();

                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    bufferedReader.close();
                    System.out.println("응답값 : " + response);

                } catch (Exception e) {
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


    //    @RequestMapping(value = "/admin/campingcar/setting", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @PostMapping("/admin/campingcar/setting")
    @ResponseBody
    public void post_admin_setting(HttpServletResponse res, @RequestBody ExplanationDTO explanationDTO) throws IOException {

        Optional<Explanation> explanation_optional = explanationService.findById((long) 0);
        if(explanation_optional.isPresent()) {
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
        }


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }

    @PutMapping("/admin/campingcar/price/by/{carType}")
    @ResponseBody
    public void put_admin_campingcar_price(HttpServletResponse res, @RequestBody CampingCarPriceDTO campingCarPriceDTO, @PathVariable String carType) throws IOException {

        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(carType);

        campingCarPrice.setCarNum(campingCarPriceDTO.getCarNum());
        campingCarPrice.setCarCode(campingCarPriceDTO.getCarCode());
        campingCarPrice.setSeason(campingCarPriceDTO.getSeason());
        campingCarPrice.setOnedays(campingCarPriceDTO.getOnedays());
        campingCarPrice.setTwodays(campingCarPriceDTO.getTwodays());
        campingCarPrice.setThreedays(campingCarPriceDTO.getThreedays());
        campingCarPrice.setFourdays(campingCarPriceDTO.getFourdays());
        campingCarPrice.setFivedays(campingCarPriceDTO.getFivedays());
        campingCarPrice.setSixdays(campingCarPriceDTO.getSixdays());
        campingCarPrice.setSevendays(campingCarPriceDTO.getSevendays());
        campingCarPrice.setEightdays(campingCarPriceDTO.getEightdays());
        campingCarPrice.setNinedays(campingCarPriceDTO.getNinedays());
        campingCarPrice.setTendays(campingCarPriceDTO.getTendays());
        campingCarPrice.setElevendays(campingCarPriceDTO.getElevendays());
        campingCarPrice.setTwelvedays(campingCarPriceDTO.getTwelvedays());
        campingCarPrice.setThirteendays(campingCarPriceDTO.getThirteendays());
        campingCarPrice.setFourteendays(campingCarPriceDTO.getFourteendays());
        campingCarPrice.setFifteendays(campingCarPriceDTO.getFifteendays());
        campingCarPrice.setSixteendays(campingCarPriceDTO.getSixteendays());
        campingCarPrice.setSeventeendays(campingCarPriceDTO.getSeventeendays());
        campingCarPrice.setEighteendays(campingCarPriceDTO.getEighteendays());
        campingCarPrice.setNinetinedays(campingCarPriceDTO.getNinetinedays());
        campingCarPrice.setTwentydays(campingCarPriceDTO.getTwentydays());
        campingCarPrice.setTwentyonedays(campingCarPriceDTO.getTwentyonedays());
        campingCarPrice.setTwentytwodays(campingCarPriceDTO.getTwentytwodays());
        campingCarPrice.setTwentythreedays(campingCarPriceDTO.getTwentythreedays());
        campingCarPrice.setTwentyfourdays(campingCarPriceDTO.getTwentyfourdays());
        campingCarPrice.setTwentyfivedays(campingCarPriceDTO.getTwentyfivedays());
        campingCarPrice.setTwentysixdays(campingCarPriceDTO.getTwentysixdays());
        campingCarPrice.setTwentysevendays(campingCarPriceDTO.getTwentysevendays());
        campingCarPrice.setTwentyeightdays(campingCarPriceDTO.getTwentyeightdays());
        campingCarPrice.setTwentyninedays(campingCarPriceDTO.getTwentyninedays());
        campingCarPrice.setThirtydays(campingCarPriceDTO.getThirtydays());
        campingCarPrice.setDeposit(campingCarPriceDTO.getDeposit());
        campingCarPrice.setYearmodel(campingCarPriceDTO.getYearmodel());

        campingCarPriceService.save(campingCarPrice);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }
}
