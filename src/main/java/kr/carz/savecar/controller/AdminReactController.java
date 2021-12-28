package kr.carz.savecar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminReactController {

    @GetMapping("/react")
    public String getReact(){
        return "Hello React ! I'm Spring Boot !";
    }

}
