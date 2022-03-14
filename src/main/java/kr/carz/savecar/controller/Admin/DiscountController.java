package kr.carz.savecar.controller.Admin;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.dto.DiscountSaveDTO;
import kr.carz.savecar.service.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Optional;

@Controller
public class DiscountController {
    private final DiscountService discountService;

    @Autowired
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }


    // 할인가 적용하기 메뉴로 입장
    @GetMapping("/admin/discount/menu")
    public ModelAndView get_discount_menu() {

        ModelAndView mav = new ModelAndView();

        List<Discount> discountList = discountService.findAllDiscounts();
        mav.addObject("discountList", discountList);
        mav.setViewName("admin/discount_menu");

        return mav;
    }


    // 할인가 적용하기 api
    @PostMapping("/admin/discount")
    @ResponseBody
    public void save_discount(HttpServletResponse res, @RequestBody DiscountSaveDTO discountDTO) throws IOException {

        JSONObject jsonObject = new JSONObject();

        // 이미 db에 등록된 차량인지 확인
        Optional<Discount> original_discount = discountService.findDiscountByCarNo(discountDTO.getCarNo());
        if(original_discount.isPresent()){
            jsonObject.put("result", 0);
        } else {
            discountService.saveDTO(discountDTO);

            jsonObject.put("result", 1);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    // 할인가 수정하기 api
    @RequestMapping(value = "/admin/discount/{carNo}/{discount}", produces = "application/json; charset=UTF-8", method = RequestMethod.PUT)
    @ResponseBody
    public void update_discount(HttpServletResponse res, @PathVariable String carNo, @PathVariable double discount) throws IOException {

        JSONObject jsonObject = new JSONObject();

        // 이미 db에 등록된 차량인지 확인
        Optional<Discount> original_discount = discountService.findDiscountByCarNo(carNo);

        if(original_discount.isPresent()){
            original_discount.get().setDiscount(discount);
            discountService.save(original_discount.get());
            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    // 할인 차명 수정하기 api
    @RequestMapping(value = "/admin/discount/carName/{carNo}/{carName}", produces = "application/json; charset=UTF-8", method = RequestMethod.PUT)
    @ResponseBody
    public void update_car_name(HttpServletResponse res, @PathVariable String carNo, @PathVariable String carName) throws IOException {

        JSONObject jsonObject = new JSONObject();

        // 이미 db에 등록된 차량인지 확인
        Optional<Discount> original_discount = discountService.findDiscountByCarNo(carNo);

        if(original_discount.isPresent()){
            original_discount.get().setCarName(carName);
            discountService.save(original_discount.get());
            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }

    // 할인가 description 수정하기 api
    @RequestMapping(value = "/admin/discount/description/{carNo}/{description}", produces = "application/json; charset=UTF-8", method = RequestMethod.PUT)
    @ResponseBody
    public void update_description(HttpServletResponse res, @PathVariable String carNo, @PathVariable String description) throws IOException {

        JSONObject jsonObject = new JSONObject();

        // 이미 db에 등록된 차량인지 확인
        Optional<Discount> original_discount = discountService.findDiscountByCarNo(carNo);

        if(original_discount.isPresent()){
            original_discount.get().setDescription(description);
            discountService.save(original_discount.get());
            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }

    // 할인가 삭제하기 api
    @DeleteMapping("/admin/discount/{carNo}")
    @ResponseBody
    public void delete_discount(HttpServletResponse res, @PathVariable String carNo) throws IOException {

        JSONObject jsonObject = new JSONObject();

        // 이미 db에 등록된 차량인지 확인
        Optional<Discount> original_discount = discountService.findDiscountByCarNo(carNo);

        if(original_discount.isPresent()){
            discountService.delete(original_discount.get());
            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }



}
