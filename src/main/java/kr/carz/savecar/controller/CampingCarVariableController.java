package kr.carz.savecar.controller;

import kr.carz.savecar.domain.CampingCarVariable;
import kr.carz.savecar.domain.Reservation;
import kr.carz.savecar.domain.ReservationSaveDto;
import kr.carz.savecar.service.CampingCarVariableService;
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

import java.util.HashMap;
import java.util.List;

@Controller
public class CampingCarVariableController {
    private final CampingCarVariableService campingCarVariableService;

    @Autowired
    public CampingCarVariableController(CampingCarVariableService campingCarVariableService) {
        this.campingCarVariableService = campingCarVariableService;
    }


    //예약 목록 조회 api
//    @GetMapping("/reservation/list")
//    public String reservation_list(Model model) {
//        List<CampingCarVariable> campingCarVariableList = campingCarVariableService.findCampingCarVariables();
//        model.addAttribute("campingCarVariableList", campingCarVariableList);
//
//        System.out.println(campingCarVariableList.get(0));
//
//        return "reservation_list";
//    }

}
