package kr.carz.savecar.controller.Admin;

import kr.carz.savecar.domain.Reservation;
import kr.carz.savecar.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

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

        mav.addObject("currentPage", pageable.getPageNumber());
        mav.addObject("pageSize", pageable.getPageSize());

        mav.addObject("startPage", (pageable.getPageNumber() / 5) * 5 + 1);
        mav.addObject("endPage", Integer.min((pageable.getPageNumber() / 5 + 1) * 5, reservationPage.getTotalPages()));

        mav.addObject("totalPages", reservationPage.getTotalPages());
        mav.addObject("reservationList", reservationPage.getContent());

        mav.setViewName("admin/counsel_menu");

        return mav;
    }


}
