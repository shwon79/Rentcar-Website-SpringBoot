package kr.carz.savecar.controller;

import kr.carz.savecar.domain.CampingCar;
import kr.carz.savecar.domain.Reservation;
import kr.carz.savecar.domain.ReservationSaveDto;
import kr.carz.savecar.service.MonthlyRentService;
import kr.carz.savecar.service.ReservationService;
import kr.carz.savecar.service.YearlyRentService;
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

import java.util.HashMap;
import java.util.List;

@Controller
public class ReservationController {
    private final ReservationService reservationService;
    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
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

        params.put("to", "01058283328"); // 01033453328 추가
        params.put("from", "01058283328");
        params.put("type", "LMS");


        if(dto.getTitle() == null){
            System.out.println("empty");
        } else {
            System.out.println(dto.getTitle());
        }

        if (dto.getTitle().equals("월렌트, 12개월렌트")){
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
        }
        else {
            params.put("text", "[" + dto.getTitle() + "]\n"
                    + "예약자 이름: " + dto.getName() + "\n"
                    + "연락처: " + dto.getPhoneNo() + "\n"
                    + "요청사항: " + dto.getDetail() + "\n\n"
                    + "렌트상품: 캠핑카 - " + dto.getProduct() + "\n");
        }
        params.put("app_version", "test app 1.2");

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString()); //전송 결과 출력
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }

        return reservationService.save(dto);
    }
}
