package kr.carz.savecar.controller.Admin;

import kr.carz.savecar.dto.*;
import kr.carz.savecar.service.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.gson.GsonFactory;


@Controller
public class AdminLoginController {
    private final AdminService adminService;

    @Value("${ga.VIEW_ID}")
    private static String VIEW_ID;

    @Value("${ga.CLIENT_ID}")
    private static String CLIENT_ID;

    @Autowired
    public AdminLoginController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin/index")
    public String index(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        ModelMap model) {

        model.addAttribute("VIEW_ID", VIEW_ID);
        model.addAttribute("CLIENT_ID", CLIENT_ID);

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

    @GetMapping("/admin/ga/VIEW_ID")
    @ResponseBody
    public void getGaViewId(HttpServletResponse res) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("VIEW_ID", VIEW_ID);

       PrintWriter pw = res.getWriter();
       pw.print(jsonObject);
       pw.flush();
       pw.close();
    }

}
