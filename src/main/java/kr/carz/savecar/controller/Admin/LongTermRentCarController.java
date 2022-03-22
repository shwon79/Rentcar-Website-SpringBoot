package kr.carz.savecar.controller.Admin;

import kr.carz.savecar.domain.LongTermRent;
import kr.carz.savecar.domain.LongTermRentImage;
import kr.carz.savecar.domain.ValuesForWeb;
import kr.carz.savecar.dto.LongTermRentDTO;
import kr.carz.savecar.dto.ValuesForWebDTO;
import kr.carz.savecar.dto.ValuesVO;
import kr.carz.savecar.service.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @PostMapping("/admin/longTerm")
    @ResponseBody
    public void postAdminLongTerm(HttpServletResponse res, @RequestBody LongTermRentDTO longTermRentDTO) throws IOException {

        longTermRentService.saveDTO(longTermRentDTO);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }

    @GetMapping("/admin/longTerm/detail/{longTermId}")
    public String rent_long_term_detail() {

        return "admin/longTerm_detail";
    }
}
