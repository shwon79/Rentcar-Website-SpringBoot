package kr.carz.savecar.controller.Utils;

import kr.carz.savecar.domain.Rent24;
import kr.carz.savecar.service.Rent24Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class Rent24Controller {

    private final Rent24Service rent24Service;

    @Autowired
    public Rent24Controller(Rent24Service rent24Service) {
        this.rent24Service = rent24Service;
    }

    @GetMapping("/rent/rent24/{id}")
    public String rent_rent24(Model model, @PathVariable Long id) {

        Optional<Rent24> rent24Wrapper = rent24Service.findByRent24Id(id);
        if(rent24Wrapper.isPresent()){
            model.addAttribute("rent24", rent24Wrapper.get());
        }

        return "rent24/detail";
    }

}