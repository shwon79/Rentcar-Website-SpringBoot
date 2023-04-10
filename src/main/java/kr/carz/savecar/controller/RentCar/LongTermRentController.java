package kr.carz.savecar.controller.RentCar;

import kr.carz.savecar.domain.DateTime;
import kr.carz.savecar.domain.LongTermRent;
import kr.carz.savecar.domain.LongTermRentImage;
import kr.carz.savecar.dto.LongTermRentDTO;
import kr.carz.savecar.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
public class LongTermRentController implements RentInterface {

    private final LongTermRentService longTermRentService;
    private final LongTermRentImageService longTermRentImageService;

    @Autowired
    public LongTermRentController(LongTermRentService longTermRentService, LongTermRentImageService longTermRentImageService) {
        this.longTermRentService = longTermRentService;
        this.longTermRentImageService = longTermRentImageService;
    }

    @GetMapping("/rent/long_term/main")
    public String rent_main(Model model) {

        List<LongTermRent> longTermRentList = longTermRentService.findAll();
        List<List<LongTermRentImage>> imageList = new ArrayList<>();

        Collections.sort(longTermRentList);
        for(LongTermRent longTermRent : longTermRentList){
            imageList.add(longTermRentImageService.findByLongTermRent(longTermRent));
        }

        model.addAttribute("longTermRentList", longTermRentList);
        model.addAttribute("imageList", imageList);

        return "rent_longterm/main";
    }

    @GetMapping("/rent/long_term/{longTermRentId}")
    public String rent_long_term_detail(Model model, @PathVariable Long longTermRentId) {

        Optional<LongTermRent> longTermRentWrapper = longTermRentService.findById(longTermRentId);
        if(longTermRentWrapper.isPresent()){
            LongTermRent longTermRent = longTermRentWrapper.get();
            List<LongTermRentImage> longTermRentImageList = longTermRentImageService.findByLongTermRent(longTermRent);
            model.addAttribute("longTermRent", longTermRent);
            model.addAttribute("longTermRentImageList", longTermRentImageList);
        }
        return "rent_longterm/detail";
    }

    // 예약신청하기 새 창 띄우기
    @PostMapping("/rent/long_term/detail/form/reservation")
    public String rent_month_detail_form_reservation(ModelMap model, @ModelAttribute LongTermRentDTO longTermRentDTO) {

        model.put("longTermRentDTO",longTermRentDTO);
        model.put("today_format", DateTime.today_date_only());

        return "rent_longterm/reservation_form";
    }


    // 견적서보기 새 창 띄우기
    @PostMapping("/rent/long_term/detail/form/estimate")
    public String rent_month_detail_form_estimate(ModelMap model, @ModelAttribute LongTermRentDTO longTermRentDTO) {
        model.put("longTermRentDTO",longTermRentDTO);

        return "rent_longterm/estimate_form";
    }
}