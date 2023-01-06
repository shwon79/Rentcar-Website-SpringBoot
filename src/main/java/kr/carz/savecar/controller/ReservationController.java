package kr.carz.savecar.controller;

import kr.carz.savecar.dto.ReservationSaveDTO;
import kr.carz.savecar.service.ReservationService;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Value("${coolsms.api_key}")
    private String api_key;

    @Value("${coolsms.api_secret}")
    private String api_secret;

    @Value("${phone.admin2}")
    private String admin2;

    @Value("${phone.admin3}")
    private String admin3;

    @Value("${phone.admin4}")
    private String admin4;

    @Value("${kakao.ATA.template.templatecode.realtimerent.employee}")
    private String realtimeRentEmployeeTemplateCode;

    @Value("${kakao.ATA.template.senderkey.realtimerent}")
    private String senderKey;

    // [실패]
    //    @RequestMapping(value = "/reservation/kakao/ATA", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @PostMapping("/reservation/kakao/ATA")
    @ResponseBody
    public void kakao_reservation(HttpServletResponse res, String to_manager_phone, String to_customer_phone, Map<String, String> employerMap, Map<String, String> customerMap, String employerTemplateCode, String customerTemplateCode) throws IOException {

        Message coolsms = new Message(api_key, api_secret);
        HashMap<String, String> params = new HashMap<>();
        HashMap<String, String> params2 = new HashMap<>();

        /* 세이브카에 예약확인 문자 전송 */
        params.put("to", to_manager_phone);  // +", "+admin2+", "+admin3
        params.put("from", admin3);
        params.put("type", "ATA");
        params.put("template_code", employerTemplateCode);
        params.put("sender_key", senderKey);

        /* 고객에게 예약확인 문자 전송 */
        params2.put("to", to_customer_phone);
        params2.put("from", admin3);
        params2.put("type", "ATA");
        params2.put("template_code", customerTemplateCode);
        params2.put("sender_key", senderKey);

        params.putAll(employerMap);
        params2.putAll(customerMap);

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

        org.json.JSONObject jsonObject = new org.json.JSONObject();
        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }

    // [실패]
    @GetMapping("/test/reservation/kakao/ATA")
    @ResponseBody
    public void test_kakao_reservation(HttpServletResponse res) throws IOException {

        Message coolsms = new Message(api_key, api_secret);
        HashMap<String, String> params = new HashMap<>();
        HashMap<String, String> params2 = new HashMap<>();

        /* 세이브카에 예약확인 문자 전송 */
        params.put("to", admin2);  // +", "+admin2+", "+admin3
        params.put("from", admin3);
        params.put("type", "ATA");
        params.put("template_code", realtimeRentEmployeeTemplateCode);
        params.put("sender_key", senderKey);

        /* 고객에게 예약확인 문자 전송 */
        params2.put("to", admin2);
        params2.put("from", admin3);
        params2.put("type", "ATA");
        params2.put("template_code", realtimeRentEmployeeTemplateCode);
        params2.put("sender_key", senderKey);

        params.put("text", "test");
        params2.put("text", "test");
        params.put("only_ata", "true");
        params2.put("only_ata", "true");

        params.put("country", "82");
        params2.put("country", "82");

        params.put("reservationId", "test");
        params.put("reservationName", "test");
        params.put("reservationPhone", "test");
        params.put("selectAge", "test");
        params.put("reservationAge", "test");
        params.put("carName", "test");
        params.put("carNo", "test");
        params.put("reservationDate", "test");
        params.put("reservationTime", "test");
        params.put("rentTerm", "test");
        params.put("kilometer", "test");
        params.put("delivery_text", "test");
        params.put("reservationGuarantee", "test");
        params.put("carAmountTotal", "test");
        params.put("carDeposit", "test");
        params.put("reservationDetails", "test");


        params2.put("reservationId", "test");
        params2.put("reservationName", "test");
        params2.put("reservationPhone", "test");
        params2.put("selectAge", "test");
        params2.put("reservationAge", "test");
        params2.put("carName", "test");
        params2.put("carNo", "test");
        params2.put("reservationDate", "test");
        params2.put("reservationTime", "test");
        params2.put("rentTerm", "test");
        params2.put("kilometer", "test");
        params2.put("delivery_text", "test");
        params2.put("reservationGuarantee", "test");
        params2.put("carAmountTotal", "test");
        params2.put("carDeposit", "test");
        params2.put("reservationDetails", "test");


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

        org.json.JSONObject jsonObject = new org.json.JSONObject();
        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    // 문자 전송 api
    public void send_message(String to_manager_phone, String to_customer_phone, String manager_text_message, String customer_text_message)  {

        // 문자전송
        Message coolsms = new Message(api_key, api_secret);
        HashMap<String, String> params = new HashMap<>();
        HashMap<String, String> params2 = new HashMap<>();

        /* 세이브카에 예약확인 문자 전송 */
        params.put("to", to_manager_phone);
        params.put("from", admin3);
        params.put("type", "LMS");

        /* 고객에게 예약확인 문자 전송 */
        params2.put("to", to_customer_phone);
        params2.put("from", admin3);
        params2.put("type", "LMS");

        params.put("text", manager_text_message);
        params2.put("text", customer_text_message);

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

    // 예약 저장 api
    @PostMapping("/reservation/apply")
    @ResponseBody
    public void save(HttpServletResponse res, @RequestBody ReservationSaveDTO dto) throws IOException {

        Message coolsms = new Message(api_key, api_secret);
        HashMap<String, String> params = new HashMap<>();
        HashMap<String, String> params2 = new HashMap<>();

        /* 세이브카에 예약확인 문자 전송 */
        params.put("to", admin2+", "+admin3 + ", " + admin4);
        params.put("from", admin3);
        params.put("type", "LMS");

        /* 고객에게 예약확인 문자 전송 */
        params2.put("to", dto.getPhoneNo());
        params2.put("from", admin3);
        params2.put("type", "LMS");

        switch (dto.getTitle()) {
            case "간편상담신청":
            case "한눈에상담신청":
                params.put("text", "[" + dto.getTitle() + "]\n"
                        + "문의자 이름: " + dto.getName() + "\n"
                        + "연락처: " + dto.getPhoneNo() + "\n"
                        + "차량명: " + dto.getCar_name() + "\n"
                        + "지역: " + dto.getRegion() + "\n"
                        + "예상대여일자: " + dto.getResDate() + "\n"
                        + "요청사항: " + dto.getDetail() + "\n\n");

                params2.put("text", "[상담신청이 완료되었습니다]" + "\n"
                        + "문의자 이름: " + dto.getName() + "\n"
                        + "차량명: " + dto.getCar_name() + "\n"
                        + "지역: " + dto.getRegion() + "\n"
                        + "예상대여일자: " + dto.getResDate() + "\n"
                        + "요청사항: " + dto.getDetail() + "\n\n");
                break;
            case "구독상담신청":
            case "누구나장기렌트간편상담신청":
            case "월렌트+12개월상담신청":
            case "캠핑카메인상담신청":
                params.put("text", "[" + dto.getTitle() + "]\n"
                        + "문의자 이름: " + dto.getName() + "\n"
                        + "연락처: " + dto.getPhoneNo() + "\n"
                        + "요청사항: " + dto.getDetail() + "\n\n");

                params2.put("text", "[상담신청이 완료되었습니다]" + "\n"
                        + "문의자 이름: " + dto.getName() + "\n"
                        + "요청사항: " + dto.getDetail() + "\n\n");
                break;

            case "n일이내입고예정차량":
                params.put("text", "[" + dto.getTitle() + "]\n"
                        + "문의자 이름: " + dto.getName() + "\n"
                        + "연락처: " + dto.getPhoneNo() + "\n"
                        + "보험연령: " + dto.getAge_limit() + "\n"
                        + "차량명: " + dto.getCar_name() + "\n"
                        + "차량번호: " + dto.getCar_num() + "\n"
                        + "년식: " + dto.getCarAge() + "\n"
                        + "대여기간: " + dto.getProduct() + "\n"
                        + "약정 주행거리: " + dto.getMileage() + "\n"
                        + "보증금: " + dto.getDeposit() + "\n"
                        + "총렌트료[부포]: " + dto.getPrice() + "\n"
                        + "요청사항: " + dto.getDetail() + "\n\n");

                params2.put("text", "[상담신청이 완료되었습니다]" + "\n"
                        + "문의자 이름: " + dto.getName() + "\n"
                        + "연락처: " + dto.getPhoneNo() + "\n"
                        + "보험연령: " + dto.getAge_limit() + "\n"
                        + "차량명: " + dto.getCar_name() + "\n"
                        + "차량번호: " + dto.getCar_num() + "\n"
                        + "년식: " + dto.getCarAge() + "\n"
                        + "대여기간: " + dto.getProduct() + "\n"
                        + "약정 주행거리: " + dto.getMileage() + "\n"
                        + "보증금: " + dto.getDeposit() + "\n"
                        + "총렌트료: " + dto.getPrice() + "\n"
                        + "요청사항: " + dto.getDetail() + "\n\n");
                break;
            case "실시간견적내기":
                params.put("text", "[" + dto.getTitle() + "]\n"
                        + "이름: " + dto.getName() + "\n"
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
                        + "이름: " + dto.getName() + "\n"
                        + "요청사항: " + dto.getDetail() + "\n\n"
                        + "렌트상품: " + dto.getProduct() + "\n"
                        + "차종: " + dto.getCategory1() + "\n"
                        + "차분류: " + dto.getCategory2() + "\n"
                        + "차명: " + dto.getCar_name() + "\n"
                        + "주행거리: " + dto.getMileage() + "\n"
                        + "21세 이상: " + dto.getAge_limit() + "\n"
                        + "사이트에서 조회된 렌트료: " + dto.getPrice() + "\n");
                break;
            case "누구나장기렌트차량상세":
                params.put("text", "[누구나 장기렌트 상담신청]\n"
                        + "▼ 문의자 정보" + "\n"
                        + "예약자 이름: " + dto.getName() + "\n"
                        + "렌트상품: " + dto.getProduct() + "\n"
                        + "연락처: " + dto.getPhoneNo() + "\n"
                        + "요청사항: " + dto.getDetail() + "\n\n"

                        + "▼ 대여 정보" + "\n"
                        + "차량이름: " + dto.getCar_name() + "\n"
                        + "차량번호: " + dto.getCar_num() + "\n"
                        + "차량색상: " + dto.getCategory1() + "\n"
                        + "차량년식: " + dto.getCarAge() + "\n"
                        + "차량연료: " + dto.getCategory2() + "\n"
                        + "계약기간: " + dto.getResDate() + "\n"
                        + "약정주행거리: " + dto.getMileage() + "\n"
                        + "월대여료(VAT별도): " + dto.getPrice() + "\n"
                        + "보증금: " + dto.getDeposit() + "\n"
                        + "서비스: " + dto.getOption() + "\n");

                params2.put("text", "[상담신청이 완료되었습니다]" + "\n"
                        + "▼ 문의자 정보" + "\n"
                        + "예약자 이름: " + dto.getName() + "\n"
                        + "렌트상품: " + dto.getProduct() + "\n"
                        + "요청사항: " + dto.getDetail() + "\n\n"

                        + "▼ 대여 정보" + "\n"
                        + "차량이름: " + dto.getCar_name() + "\n"
                        + "차량번호: " + dto.getCar_num() + "\n"
                        + "차량색상: " + dto.getCategory1() + "\n"
                        + "차량년식: " + dto.getCarAge() + "\n"
                        + "차량연료: " + dto.getCategory2() + "\n"
                        + "계약기간: " + dto.getResDate() + "\n"
                        + "약정주행거리: " + dto.getMileage() + "\n"
                        + "월대여료(VAT별도): " + dto.getPrice() + "\n"
                        + "보증금: " + dto.getDeposit() + "\n"
                        + "서비스: " + dto.getOption() + "\n");
                break;
            case "캠핑카렌트":
                params.put("text", "[" + dto.getTitle() + "]\n"
                        + "예약자 이름: " + dto.getName() + "\n"
                        + "연락처: " + dto.getPhoneNo() + "\n"
                        + "요청사항: " + dto.getDetail() + "\n\n"
                        + "렌트상품: 캠핑카 - " + dto.getProduct() + "\n");

                params2.put("text", "[상담신청이 완료되었습니다]" + "\n"
                        + "예약자 이름: " + dto.getName() + "\n"
                        + "요청사항: " + dto.getDetail() + "\n\n"
                        + "렌트상품: 캠핑카 - " + dto.getProduct() + "\n");
                break;
            default:
                System.out.println("예약하기에 문제가 생겼습니다.");

        }
        params.put("app_version", "test app 1.2");
        params2.put("app_version", "test app 1.2");


        /* 세이브카에게 문자 전송 */
        try {
            JSONObject obj = coolsms.send(params);
            System.out.println(obj.toString()); //전송 결과 출력
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }

        /* 고객에게 예약확인 문자 전송 */
        try {
            JSONObject obj2 = coolsms.send(params2);
            System.out.println(obj2.toString()); //전송 결과 출력
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
        reservationService.save(dto);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


}
