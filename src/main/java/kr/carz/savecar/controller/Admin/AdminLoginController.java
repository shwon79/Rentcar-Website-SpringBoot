package kr.carz.savecar.controller.Admin;

import kr.carz.savecar.dto.*;
import kr.carz.savecar.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


@Controller
public class AdminLoginController {
    private final AdminService adminService;

    @Autowired
    public AdminLoginController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping("/admin/index")
    public String index(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        ModelMap model) {

        model.addAttribute("error",error);
        model.addAttribute("exception",exception);

        return "/admin/index";
    }

    @GetMapping("/admin/signup")
    public String signupForm(ModelMap model) {
        model.addAttribute("admin",new AdminDTO());

        return "/admin/signupForm";
    }

    @PostMapping("/admin/signup")
    public String signup(AdminDTO adminDTO) {
        adminService.signUp(adminDTO);

        return "redirect:/admin/index";
    }

    @GetMapping("/admin/login")
    public String login()
    {
        return "redirect:/admin/index";
    }

}
