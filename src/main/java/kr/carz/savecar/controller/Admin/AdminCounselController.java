package kr.carz.savecar.controller.Admin;

import kr.carz.savecar.domain.RealTimeRentCar;
import kr.carz.savecar.domain.Reservation;
import kr.carz.savecar.dto.IdListVO;
import kr.carz.savecar.service.ReservationService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
public class AdminCounselController {
    private final ReservationService reservationService;

    @Autowired
    public AdminCounselController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/admin/counsel/menu")
    public ModelAndView get_counsel_menu(Pageable pageable) {

        ModelAndView mav = new ModelAndView();

        Page<Reservation> reservationPage = reservationService.findAllPageable(pageable);
        List<Reservation> reservationList = reservationPage.getContent();
        HashSet<String> titleSet = new HashSet<>();

        for(Reservation reservation : reservationList){
            titleSet.add(reservation.getTitle());
        }
        List<String> titleList = new ArrayList<>(titleSet);
        Collections.sort(titleList);
        titleList.add(0, "전체");

        mav.addObject("currentPage", pageable.getPageNumber());
        mav.addObject("pageSize", pageable.getPageSize());

        mav.addObject("startPage", (pageable.getPageNumber() / 5) * 5 + 1);
        mav.addObject("endPage", Integer.min((pageable.getPageNumber() / 5 + 1) * 5, reservationPage.getTotalPages()));

        mav.addObject("totalPages", reservationPage.getTotalPages());
        mav.addObject("reservationList", reservationPage.getContent());
        mav.addObject("titleList", titleList);
        mav.addObject("title", "전체");

        mav.setViewName("admin/counsel_menu");

        return mav;
    }


    @GetMapping("/admin/counsel/title/{title}")
    public ModelAndView get_counsel_by_title(Pageable pageable, @PathVariable String title) {

        ModelAndView mav = new ModelAndView();

        Page<Reservation> reservationPage = reservationService.findByTitlePageable(title, pageable);
        List<Reservation> reservationList = reservationPage.getContent();
        HashSet<String> titleSet = new HashSet<>();

        for(Reservation reservation : reservationList){
            titleSet.add(reservation.getTitle());
        }
        List<String> titleList = new ArrayList<>(titleSet);
        Collections.sort(titleList);
        titleList.add(0, "전체");

        mav.addObject("currentPage", pageable.getPageNumber());
        mav.addObject("pageSize", pageable.getPageSize());

        mav.addObject("startPage", (pageable.getPageNumber() / 5) * 5 + 1);
        mav.addObject("endPage", Integer.min((pageable.getPageNumber() / 5 + 1) * 5, reservationPage.getTotalPages()));

        mav.addObject("totalPages", reservationPage.getTotalPages());
        mav.addObject("reservationList", reservationPage.getContent());
        mav.addObject("titleList", titleList);
        mav.addObject("title", title);

        mav.setViewName("admin/counsel_menu");

        return mav;
    }


    @GetMapping("/admin/counsel/detail/{reservationId}")
    public ModelAndView get_counsel_detail(ModelAndView mav, @PathVariable Long reservationId) {

        Optional<Reservation> reservationWrapper = reservationService.findById(reservationId);
        if(reservationWrapper.isPresent()){
            Reservation reservation = reservationWrapper.get();
            mav.addObject("reservation", reservation);
        }
        mav.setViewName("admin/counsel_detail");

        return mav;
    }



    @DeleteMapping("/admin/counsel/{reservationId}")
    @ResponseBody
    public void delete_counsel(HttpServletResponse res, @PathVariable Long reservationId) throws IOException {

        JSONObject jsonObject = new JSONObject();

        Optional<Reservation> reservationWrapper = reservationService.findById(reservationId);

        if(reservationWrapper.isPresent()) {
            Reservation reservation = reservationWrapper.get();
            reservationService.delete(reservation);
            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }

    @DeleteMapping("/admin/counsel/multiple")
    @ResponseBody
    public void delete_counsel_multiple(HttpServletResponse res, @RequestBody IdListVO idListVO) throws IOException {

        JSONObject jsonObject = new JSONObject();

        List<Long> counselIdList = idListVO.getIdList();

        int problemFlg = 0;
        for(Long counselId : counselIdList) {

            Optional<Reservation> reservationWrapper = reservationService.findById(counselId);

            if (reservationWrapper.isPresent()) {
                Reservation reservation = reservationWrapper.get();
                reservationService.delete(reservation);
            } else {
                problemFlg = 1;
            }
        }

        if(problemFlg == 0) {
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
