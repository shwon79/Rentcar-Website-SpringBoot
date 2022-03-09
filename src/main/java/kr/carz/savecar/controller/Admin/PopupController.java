package kr.carz.savecar.controller.Admin;

import kr.carz.savecar.domain.CampingCarPrice;
import kr.carz.savecar.domain.ValuesForWeb;
import kr.carz.savecar.dto.ValuesForWebDTO;
import kr.carz.savecar.dto.ValuesVO;
import kr.carz.savecar.service.CampingCarPriceService;
import kr.carz.savecar.service.ValuesForWebService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
public class PopupController {
    private final ValuesForWebService valuesForWebService;
    private final CampingCarPriceService campingCarPriceService;

    @Autowired
    public PopupController(ValuesForWebService valuesForWebService, CampingCarPriceService campingCarPriceService) {
        this.valuesForWebService = valuesForWebService;
        this.campingCarPriceService = campingCarPriceService;
    }

    // [관리자 메인페이지] 팝업 내용설정 메뉴로 입장
    @GetMapping(value = "/admin/popup/menu")
    @ResponseBody
    public ModelAndView get_popup_menu() {

        ModelAndView mav = new ModelAndView();
        List<CampingCarPrice> campingCarList = campingCarPriceService.findAllCampingCarPrice();

        mav.addObject("campingCarList", campingCarList);
        mav.setViewName("admin/popup_menu");

        return mav;
    }


    @PostMapping("/admin/popup/value")
    @ResponseBody
    public void postAdminValue(HttpServletResponse res, @RequestBody ValuesVO valuesVO) throws IOException {

        List<ValuesForWebDTO> valuesForWebDTOList = valuesVO.getValuesList();

        for (ValuesForWebDTO valuesForWebDTO : valuesForWebDTOList) {

            Optional<ValuesForWeb> valuesForCheck = valuesForWebService.findValueByTitle(valuesForWebDTO.getTitle());
            if (valuesForCheck.isPresent()) {
                ValuesForWeb originalValuesForWeb = valuesForCheck.get();
                originalValuesForWeb.setValue(valuesForWebDTO.getValue());
                valuesForWebService.save(originalValuesForWeb);
            } else {
                valuesForWebService.saveDTO(valuesForWebDTO);
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


}
