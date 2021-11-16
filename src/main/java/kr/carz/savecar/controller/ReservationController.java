package kr.carz.savecar.controller;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.MorenReservationService;
import kr.carz.savecar.service.ReservationService;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ReservationController {
    private final ReservationService reservationService;
    private final MorenReservationService morenReservationService;

    @Autowired
    public ReservationController(ReservationService reservationService, MorenReservationService morenReservationService) {
        this.reservationService = reservationService;
        this.morenReservationService = morenReservationService;
    }

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


    // 예약 저장 api
    @PostMapping("/reservation/apply")
    @ResponseBody
    public Long save(@RequestBody ReservationSaveDTO dto){

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
        else if (dto.getTitle().equals("월렌트실시간")){
            params.put("text", "[" + dto.getTitle() + "]\n"
                    + "문의자 이름: " + dto.getName() + "\n"
                    + "연락처: " + dto.getPhoneNo() + "\n"
                    + "차량명: " + dto.getCar_name() + "\n"
                    + "차량번호: " + dto.getMileage() + "\n"
                    + "년식: " + dto.getOption() + "\n"
                    + "요청사항: " + dto.getDetail() + "\n\n");

            params2.put("text", "[상담신청이 완료되었습니다]" + "\n"
                    + "문의자 이름: " + dto.getName() + "\n"
                    + "차량명: " + dto.getCar_name() + "\n"
                    + "요청사항: " + dto.getDetail() + "\n\n");
        }
        else if (dto.getTitle().equals("월렌트, 12개월렌트, 24개월렌트")){
            params.put("text", "[" + dto.getTitle() + "]\n"
                    + "예약자 이름: " + dto.getName() + "\n"
                    + "연락처: " + dto.getPhoneNo() + "\n"
                    + "요청사항: " + dto.getDetail() + "\n\n"
                    + "렌트상품: " + dto.getProduct() + "\n"
                    + "차종: " + dto.getCategory1() + "\n"
                    + "차분류: " + dto.getCategory2() + "\n"
                    + "차명: " + dto.getCar_name() + "\n"
                    + "주행거리: " + dto.getMileage() + "\n"
                    + "21세 이상: " + dto.getAge_limit() + "\n"
                    + "사이트에서 조회된 렌트료: " + dto.getPrice() + "\n");

            params2.put("text", "[상담신청이 완료되었습니다]" + "\n"
                    + "예약자 이름: " + dto.getName() + "\n"
                    + "요청사항: " + dto.getDetail() + "\n\n"
                    + "렌트상품: " + dto.getProduct() + "\n"
                    + "차종: " + dto.getCategory1() + "\n"
                    + "차분류: " + dto.getCategory2() + "\n"
                    + "차명: " + dto.getCar_name() + "\n"
                    + "주행거리: " + dto.getMileage() + "\n"
                    + "21세 이상: " + dto.getAge_limit() + "\n"
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


    // 예약 저장 api
    @PostMapping("/moren/reservation/apply")
    @ResponseBody
    public void moren_reserve(HttpServletResponse res, @RequestBody MorenReservationApplyDTO dto) throws IOException{

        JSONObject jsonObject_return = new JSONObject();

        try {
            URL url = new URL("https://www.moderentcar.co.kr/api/mycar/request.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            String orderStartTime = dto.getReservationDate() + " " + dto.getReservationTime();

            String addReservationDate = null;
            String contractTerm = null;
            if (dto.getRentTerm().equals("한달")){
                addReservationDate = AddDate(dto.getReservationDate(), 0, 1, 0);
                contractTerm = "1";
            } else if (dto.getRentTerm().equals("12개월")){
                addReservationDate = AddDate(dto.getReservationDate(), 1, 0, 0);
                contractTerm = "12";
            } else if (dto.getRentTerm().equals("24개월")){
                addReservationDate = AddDate(dto.getReservationDate(), 2, 0, 0);
                contractTerm = "24";
            }
            String orderEndTime = addReservationDate + " " + dto.getReservationTime();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("COMPANY_ID", "1343");
            jsonObject.put("CAR_NUM", dto.getCarNo());
            jsonObject.put("ORDER_TYPE", "new");
            jsonObject.put("ORDER_CUSTOMER_NAME", dto.getReservationName());
            jsonObject.put("ORDER_CUSTOMER_PHONE", dto.getReservationPhone());
            jsonObject.put("ORDER_CUSTOMER_BIRTH", dto.getReservationPhone());
            jsonObject.put("ORDER_START_TIME", orderStartTime);
            jsonObject.put("ORDER_END_TIME", orderEndTime);
            jsonObject.put("ORDER_DELIVERY_PLACE", dto.getAddress());
            jsonObject.put("ORDER_DELIVERY_PLACE_EXTRA", dto.getAddressDetail());
            jsonObject.put("ORDER_CUSTOMER_MEMO", dto.getReservationDetails());
            jsonObject.put("ORDER_PRICE", dto.getCarAmountTotal());
            jsonObject.put("ORDER_PRICE_TAX", "0");
            jsonObject.put("ORDER_DEPOSIT", dto.getCarDeposit());
            jsonObject.put("ORDER_CONTRACT_TERM", contractTerm);

            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(conn.getOutputStream()));
            printWriter.write(jsonObject.toString());
            printWriter.flush();

            // 응답
            BufferedReader bufferedReader = null;
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
//            System.out.println("응답값 : " + response.toString());

            Optional<MorenReservation> morenReservationOptional = morenReservationService.findMorenReservationById(dto.getId());
            MorenReservation morenReservation = morenReservationOptional.get();
            morenReservation.setReservationStatus("1");
            morenReservationService.save(morenReservation);

            jsonObject_return.put("result", 1);
        } catch (Exception e){

            jsonObject_return.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject_return);
        pw.flush();
        pw.close();

    }

}
