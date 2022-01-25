package kr.carz.savecar.controller.Admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReactController {

    @GetMapping("/react")
    public String getReact(){
        return "Hello React ! I'm Spring Boot !";
    }

}
