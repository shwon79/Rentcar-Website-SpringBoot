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
    private final RealTimeRentCarService realTimeRentCarService;

    @Autowired
    public DiscountController(DiscountService discountService, RealTimeRentCarService realTimeRentCarService) {
        this.discountService = discountService;
        this.realTimeRentCarService = realTimeRentCarService;
    }

    public void updateDiscount(String carNo,double discount, String description){
        List<RealTimeRentCar> realTimeRentCarList =  realTimeRentCarService.findByCarNo(carNo);
        for(RealTimeRentCar realTimeRentCar : realTimeRentCarList){
            realTimeRentCar.setDiscount(discount);
            realTimeRentCar.setDescription(description);

            realTimeRentCarService.save(realTimeRentCar);
        }
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

        updateDiscount(discountDTO.getCarNo(), discountDTO.getDiscount(), discountDTO.getDescription());

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    // 할인가 수정하기 api
    @PutMapping("/admin/discount/{discountId}")
    @ResponseBody
    public void update_discount(HttpServletResponse res, @RequestBody DiscountSaveDTO discountDTO, @PathVariable Long discountId) throws IOException {

        JSONObject jsonObject = new JSONObject();

        // 이미 db에 등록된 차량인지 확인
        Optional<Discount> original_discount = discountService.findDiscountByDiscountId(discountId);

        if(original_discount.isPresent()){
            original_discount.get().setCarName(discountDTO.getCarName());
            original_discount.get().setDiscount(discountDTO.getDiscount());
            original_discount.get().setDescription(discountDTO.getDescription());
            original_discount.get().setPriceDisplay(discountDTO.getPriceDisplay());
            discountService.save(original_discount.get());

            updateDiscount(discountDTO.getCarNo(), discountDTO.getDiscount(), discountDTO.getDescription());
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
    @DeleteMapping("/admin/discount/{discountId}")
    @ResponseBody
    public void delete_discount(HttpServletResponse res, @PathVariable Long discountId) throws IOException {

        JSONObject jsonObject = new JSONObject();

        // 이미 db에 등록된 차량인지 확인
        Optional<Discount> original_discount = discountService.findDiscountByDiscountId(discountId);

        if(original_discount.isPresent()){
            discountService.delete(original_discount.get());
            updateDiscount(original_discount.get().getCarNo(), 0, null);
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
