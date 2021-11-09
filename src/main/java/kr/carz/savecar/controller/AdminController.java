package kr.carz.savecar.controller;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.service.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

@Controller
public class AdminController {
    MonthlyRentService monthlyRentService;
    YearlyRentService yearlyRentService;
    ShortRentService shortRentService;
    CampingCarService campingCarService;
    LoginService loginService;
    ReservationService reservationService;
    DiscountService discountService;

    @Autowired
    public AdminController(MonthlyRentService monthlyRentService, YearlyRentService yearlyRentService,
                           ShortRentService shortRentService, CampingCarService campingCarService,
                           LoginService loginService, ReservationService reservationService,
                           DiscountService discountService) {
        this.monthlyRentService = monthlyRentService;
        this.yearlyRentService = yearlyRentService;
        this.shortRentService = shortRentService;
        this.campingCarService = campingCarService;
        this.loginService = loginService;
        this.reservationService = reservationService;
        this.discountService = discountService;
    }

    @GetMapping("/admin/login")
    public String login(Model model) {
        return "admin_login";
    }


    //로그인
    @RequestMapping(value = "/admin/logininfo", method= RequestMethod.POST)
    @ResponseBody
    public ModelAndView post_login_info(HttpServletResponse res, HttpServletRequest req) throws IOException {

        ModelAndView mav = new ModelAndView();

        try {
            Login user = loginService.findLoginByIdAndPwd(req.getParameter("id"), req.getParameter("pwd"));
            System.out.println(user.getId());  // exception 발생코드임, 건들지 말기 => 다른 방식 찾기

            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            // admin view로 넘기기
//            List<CampingcarDateTime2> campingcarDateTimeList = campingcarDateTimeService2.findAllReservations();
//            mav.addObject("campingcarDateTimeList",campingcarDateTimeList);
            mav.setViewName("admin_campingcar_menu");

        } catch (NullPointerException e){

            res.setContentType("text/html; charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('아이디 또는 비밀번호가 틀렸습니다.'); </script>");
            out.flush();

            // 다시 login page로 back
            mav.setViewName("admin_login");
        }
        return mav;
    }


    // 로그아웃
    @GetMapping(value = "/admin/logout")
    @ResponseBody
    public ModelAndView get_admin_logout(HttpServletResponse res, HttpServletRequest req) throws IOException {

        ModelAndView mav = new ModelAndView();

        HttpSession session = req.getSession();
        session.removeAttribute("user");
        session.invalidate();

        res.setContentType("text/html; charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.println("<script>alert('로그아웃이 완료되었습니다.'); </script>");
        out.flush();

        mav.setViewName("admin_login");

        return mav;
    }



    // [관리자 메인페이지] 캠핑카 예약내역 메뉴로 입장
    @GetMapping(value = "/admin/campingcar/menu")
    @ResponseBody
    public ModelAndView get_admin_main(HttpServletResponse res, HttpServletRequest req) throws IOException {

        ModelAndView mav = new ModelAndView();
        HttpSession session = req.getSession();

        if((Login)session.getAttribute("user") == null){

            res.setContentType("text/html; charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('로그인 정보가 없습니다.'); </script>");
            out.flush();

            mav.setViewName("admin_login");
        } else {

            // admin view로 넘기기
//            List<CampingcarDateTime2> campingcarDateTimeList = campingcarDateTimeService2.findAllReservations();

//            mav.addObject("campingcarDateTimeList",campingcarDateTimeList);
            mav.setViewName("admin_campingcar_menu");
        }

        return mav;
    }


    //상담 메뉴로 입장
    @GetMapping("/admin/counsel/menu")
    public String get_counsel_menu(Model model) {
        List<Reservation> reservationList = reservationService.findAllReservations();
        model.addAttribute("reservationList", reservationList);

        return "admin_counsel_menu";
    }



    // 할인가 적용하기 메뉴로 입장
    @GetMapping("/admin/discount/menu")
    public String get_discount_menu(Model model) {

        List<Discount> discountList = discountService.findAllDiscounts();
        model.addAttribute("discountList", discountList);

        return "admin_discount_menu";
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
            Discount discount = new Discount();
            discount.setCarNo(discountDTO.getCarNo());
            discount.setDiscount(discountDTO.getDiscount());
            discount.setDescription(discountDTO.getDescription());
            discountService.save(discount);

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
    public void update_discount(HttpServletResponse res, @PathVariable String carNo, @PathVariable String discount) throws IOException {

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

    // 할인가 description 수정하기 api
    @RequestMapping(value = "/admin/description/{carNo}/{description}", produces = "application/json; charset=UTF-8", method = RequestMethod.PUT)
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
    @GetMapping("/admin/discount/delete/{carNo}")
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


    // 월렌트 데이터 가져오기 api
    @PostMapping("/admin/moren/reservation")
    @ResponseBody
    public void moren_reservation(HttpServletResponse res, @RequestBody DiscountSaveDTO discountDTO) throws IOException {

        JSONObject jsonObject = new JSONObject();

        // 이미 db에 등록된 차량인지 확인
        Optional<Discount> original_discount = discountService.findDiscountByCarNo(discountDTO.getCarNo());
        if(original_discount.isPresent()){
            jsonObject.put("result", 0);
        } else {
            Discount discount = new Discount();
            discount.setCarNo(discountDTO.getCarNo());
            discount.setDiscount(discountDTO.getDiscount());
            discount.setDescription(discountDTO.getDescription());
            discountService.save(discount);

            jsonObject.put("result", 1);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }



//    @GetMapping("/admin/detail/{date_time_id}")
//    public String get_admin_detail(Model model,  @PathVariable String date_time_id) throws Exception {
//
//        CampingcarDateTime2 campingcarDateTime2 = campingcarDateTimeService2.findByDateTimeId(Long.parseLong(date_time_id));
//        model.addAttribute("campingcarDateTime2",campingcarDateTime2);
//        System.out.println(campingcarDateTime2.getDateTimeId());
//
//        return "admin_detail";
//    }


}
