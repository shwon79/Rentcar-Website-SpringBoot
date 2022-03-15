package kr.carz.savecar.controller.Rent;

import kr.carz.savecar.domain.LongTermRent;
import kr.carz.savecar.domain.LongTermRentImage;
import kr.carz.savecar.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class LongTermRentController {

    private final LongTermRentService longTermRentService;
    private final LongTermRentImageService longTermRentImageService;

    @Autowired
    public LongTermRentController(LongTermRentService longTermRentService, LongTermRentImageService longTermRentImageService) {
        this.longTermRentService = longTermRentService;
        this.longTermRentImageService = longTermRentImageService;
    }

    // TODO: 누구나 장기 POST, PUT, DELETE 만들기
    @GetMapping("/rent/long_term")
    public String rent_long_term(Model model) {

        List<LongTermRent> longTermRentList = longTermRentService.findAll();
        List<List<LongTermRentImage>> imageList = new ArrayList<>();

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

}