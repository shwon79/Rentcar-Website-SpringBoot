package kr.carz.savecar.controller.Admin;

import kr.carz.savecar.domain.LongTermRent;
import kr.carz.savecar.domain.LongTermRentImage;
import kr.carz.savecar.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LongTermRentCarController {
    private final LongTermRentService longTermRentService;

    @Autowired
    public LongTermRentCarController(LongTermRentService longTermRentService) {
        this.longTermRentService = longTermRentService;
    }

    // TODO: 누구나 장기 POST, PUT, DELETE 만들기
    @GetMapping("/admin/longTerm/register")
    public String rent_long_term_register() {

        return "admin/longTerm_register";
    }
    @GetMapping("/admin/longTerm/main")
    public String rent_long_term_main(Model model) {

        List<LongTermRent> longTermRentList = longTermRentService.findAll();
        model.addAttribute("longTermRentList", longTermRentList);

        return "admin/longTerm_main";
    }
    @GetMapping("/admin/longTerm/detail/{longTermId}")
    public String rent_long_term_detail() {

        return "admin/longTerm_detail";
    }
}
