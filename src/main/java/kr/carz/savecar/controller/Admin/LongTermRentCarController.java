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
    private final ReservationService reservationService;
    private final MonthlyRentService monthlyRentService;
    private final YearlyRentService yearlyRentService;
    private final TwoYearlyRentService twoYearlyRentService;
    private final S3Service s3Service;

    @Autowired
    public LongTermRentCarController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService,
                                     TwoYearlyRentService twoYearlyRentService, ReservationService reservationService,
                                     S3Service s3Service) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.twoYearlyRentService = twoYearlyRentService;
        this.reservationService = reservationService;
        this.s3Service = s3Service;
    }

    // TODO: 누구나 장기 POST, PUT, DELETE 만들기
    @GetMapping("/admin/longTerm/register")
    public String rent_long_term_register() {

        return "admin/longTerm_register";
    }
    @GetMapping("/admin/longTerm/main")
    public String rent_long_term_main() {

        return "admin/longTerm_main";
    }
    @GetMapping("/admin/longTerm/detail/{longTermId}")
    public String rent_long_term_detail() {

        return "admin/longTerm_detail";
    }
}
