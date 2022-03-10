package kr.carz.savecar.controller.Rent;

import kr.carz.savecar.controller.ReservationController;
import kr.carz.savecar.domain.LongTermRent;
import kr.carz.savecar.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LongTermRentController {

    private final LongTermRentService longTermRentService;

    @Autowired
    public LongTermRentController(LongTermRentService longTermRentService) {
        this.longTermRentService = longTermRentService;
    }

    @GetMapping("/rent/long_term")
    public String rent_long_term(Model model) {

        List<LongTermRent> longTermRentList = longTermRentService.findAll();
        model.addAttribute("longTermRentList", longTermRentList);

        return "rent_longterm/main";
    }


}