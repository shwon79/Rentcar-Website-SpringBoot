package kr.carz.savecar.controller;

import kr.carz.savecar.domain.ReservationSaveDto;
import kr.carz.savecar.service.MonthlyRentService;
import kr.carz.savecar.service.ReservationService;
import kr.carz.savecar.service.YearlyRentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReservationController {
    private final ReservationService reservationService;
    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @GetMapping("/reservation/list")
    @ResponseBody
    public String reservation_list() {
        return "reservation_list";
    }


    @PostMapping("/reservation/apply")
    @ResponseBody
    public Long save(@RequestBody ReservationSaveDto dto){
        return reservationService.save(dto);
    }
}
