package kr.carz.savecar.controller.Admin;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class CounselController {
    ReservationService reservationService;

    @Autowired
    public CounselController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @GetMapping("/admin/counsel/menu")
    public ModelAndView get_counsel_menu() {

        ModelAndView mav = new ModelAndView();

        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = currentDateTime.minusDays(15);

        List<Reservation> reservationList = reservationService.findByCreatedDateAfter(startDateTime);
        mav.addObject("reservationList", reservationList);

        mav.setViewName("admin/counsel_menu");

        return mav;
    }


}
