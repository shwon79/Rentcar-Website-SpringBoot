package kr.carz.savecar.controller;

import kr.carz.savecar.dto.MorenReservationApplyDTO;
import kr.carz.savecar.dto.ReservationSaveDTO;
import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.MorenReservationService;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ReservationController {
    private final ReservationService reservationService;
    private final MorenReservationService morenReservationService;

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

    @Autowired
    public ReservationController(ReservationService reservationService, MorenReservationService morenReservationService) {
        this.reservationService = reservationService;
        this.morenReservationService = morenReservationService;
    }


    // 예약 저장 api
//    @RequestMapping(value = "/reservation/apply", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @PostMapping("/reservation/apply")
    @ResponseBody
    public void save(HttpServletResponse res, @RequestBody ReservationSaveDTO dto) throws IOException {

        Message coolsms = new Message(api_key, api_secret);
        HashMap<String, String> params = new HashMap<>();
        HashMap<String, String> params2 = new HashMap<>();


        /* 세이브카에 예약확인 문자 전송 */
        params.put("to", admin1+", "+admin2+", "+admin3); // 01033453328 추가
        params.put("from", admin3);
        params.put("type", "LMS");


        /* 고객에게 예약확인 문자 전송 */
        params2.put("to", dto.getPhoneNo());
        params2.put("from", admin3);  // 16613331 테스트하기
        params2.put("type", "LMS");


        if (dto.getTitle().equals("간편상담신청")){
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
                    + "지역: " + dto.getMileage() + "\n"
                    + "예상대여일자: " + dto.getRegion() + "\n"
                    + "요청사항: " + dto.getDetail() + "\n\n");
        }
        else if (dto.getTitle().equals("월렌트실시간")){
            params.put("text", "[" + dto.getTitle() + "]\n"
                    + "문의자 이름: " + dto.getName() + "\n"
                    + "연락처: " + dto.getPhoneNo() + "\n"
                    + "보험연령: " + dto.getAge_limit()+ "\n"
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
                    + "보험연령: " + dto.getAge_limit()+ "\n"
                    + "차량명: " + dto.getCar_name() + "\n"
                    + "차량번호: " + dto.getCar_num() + "\n"
                    + "년식: " + dto.getCarAge() + "\n"
                    + "대여기간: " + dto.getProduct() + "\n"
                    + "약정 주행거리: " + dto.getMileage() + "\n"
                    + "보증금: " + dto.getDeposit() + "\n"
                    + "총렌트료: " + dto.getPrice() + "\n"
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
        else if (dto.getTitle().equals("캠핑카렌트")){
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
