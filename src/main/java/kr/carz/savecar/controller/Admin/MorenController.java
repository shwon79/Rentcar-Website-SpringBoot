package kr.carz.savecar.controller.Admin;

import kr.carz.savecar.controller.ReservationController;
import kr.carz.savecar.domain.*;
import kr.carz.savecar.dto.MorenReservationDTO;
import kr.carz.savecar.service.*;
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
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MorenController {
    private final MorenReservationService morenReservationService;
    private final ReservationController reservationController;

    @Autowired
    public MorenController(MorenReservationService morenReservationService, ReservationController reservationController) {
        this.morenReservationService = morenReservationService;
        this.reservationController = reservationController;
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


    // 월렌트 실시간 모렌 예약 메뉴로 입장
    @GetMapping("/admin/moren/reservation/menu")
    public ModelAndView get_moren_reservation_menu() {

        ModelAndView mav = new ModelAndView();

        List<MorenReservation> morenReservationList = morenReservationService.findAllMorenReservations();
        mav.addObject("morenReservationList", morenReservationList);
        mav.setViewName("admin/moren_reservation_menu");

        mav.addObject("byTime", Comparator.comparing(MorenReservation::getCreatedDate).reversed());

        return mav;
    }

    // 월렌트 실시간 모렌 디테일 페이지로 입장
    @GetMapping("/admin/moren/reservation/detail/{reservationId}")
    public ModelAndView get_moren_reservation_menu(HttpServletResponse res, @PathVariable Long reservationId) throws IOException {

        ModelAndView mav = new ModelAndView();

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

        return mav;
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

        assert morenReservation != null;
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

                // 메시지 보내기
                String delivery_text;
                if (dto.getPickupPlace().equals("방문")) {

                    delivery_text = "방문/배차: " + dto.getPickupPlace() + "\n";
                } else {
                    delivery_text = "방문/배차: " + dto.getPickupPlace() + "\n"
                            + "배차요청주소: " + dto.getAddress() + "\n"
                            + "배차요청상세주소: " + dto.getAddressDetail() + "\n";
                }

                reservationController.send_message(admin1+", "+admin2+", "+admin3, dto.getReservationPhone(),
                        "[모렌 예약 확정 처리 완료]\n"
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
                                + "요청사항: " + dto.getReservationDetails() + "\n\n",

                        "[세이브카 렌트카 예약이 확정되었습니다]" + "\n"
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

                jsonObject_return.put("result", 1);

            } catch (Exception e) {
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
                StringBuilder response = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                bufferedReader.close();
                System.out.println("응답값 : " + response);

                // 메시지 보내기
                String delivery_text;
                if (morenReservation.getPickupPlace().equals("방문")){
                    delivery_text = "방문/배차: " + morenReservation.getPickupPlace() + "\n";
                } else {
                    delivery_text = "방문/배차: " + morenReservation.getPickupPlace() + "\n"
                            + "배차요청주소: " + morenReservation.getAddress() + "\n"
                            + "배차요청상세주소: " + morenReservation.getAddressDetail() + "\n";
                }

                reservationController.send_message(admin1+", "+admin2+", "+admin3, dto.getReservationPhone(),
                        "[모렌 예약 취소 처리 완료]\n"
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
                                + "요청사항: " + dto.getReservationDetails() + "\n\n",

                        "[세이브카 렌트카 예약이 취소되었습니다]" + "\n"
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

                jsonObject_return.put("result", 1);

            } catch (Exception e) {
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
                    StringBuilder response = new StringBuilder();

                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    bufferedReader.close();
                    System.out.println("응답값 : " + response);

                    jsonObject_return.put("result", 1);

                } catch (Exception e) {
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

}
