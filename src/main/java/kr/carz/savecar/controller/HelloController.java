package kr.carz.savecar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/rent/month")
    public String rent_month() {
        return "rent_month";
    }

    @GetMapping("/rent/long_term")
    public String rent_long_term() {
        return "rent_long_term";
    }

    @GetMapping("/price")
    public String price() {
        return "price";
    }

    @GetMapping("/travel")
    public String camping_travel() {
        return "camping_travel";
    }

    @GetMapping("/liomousine")
    public String camping_liomousine() {
        return "camping_liomousine";
    }

    @GetMapping("/europe")
    public String camping_europe() {
        return "camping_europe";
    }
}
