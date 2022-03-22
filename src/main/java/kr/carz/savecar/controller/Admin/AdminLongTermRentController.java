package kr.carz.savecar.controller.Admin;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.dto.LongTermRentDTO;
import kr.carz.savecar.dto.ReviewDTO;
import kr.carz.savecar.service.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminLongTermRentController {
    private final LongTermRentService longTermRentService;
    private final LongTermRentImageService longTermRentImageService;

    @Autowired
    public AdminLongTermRentController(LongTermRentService longTermRentService, LongTermRentImageService longTermRentImageService) {
        this.longTermRentService = longTermRentService;
        this.longTermRentImageService = longTermRentImageService;
    }

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


    @PutMapping("/admin/longTerm/{longTermRentId}}")
    @ResponseBody
    public void put_rent_car_price_monthly(HttpServletResponse res, @RequestBody LongTermRentDTO longTermRentDTO, @PathVariable Long longTermRentId) throws IOException {

        JSONObject jsonObject = new JSONObject();

        Optional<LongTermRent> longTermRentWrapper = longTermRentService.findById(longTermRentId);
        if(longTermRentWrapper.isPresent()){
            longTermRentService.updateByDTO(longTermRentWrapper.get(), longTermRentDTO);

            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    @DeleteMapping("/admin/longTerm/{longTermRentId}}")
    @ResponseBody
    public void delete_rent_car_price(HttpServletResponse res, @PathVariable Long longTermRentId) throws IOException {

        JSONObject jsonObject = new JSONObject();

        Optional<LongTermRent> longTermRentWrapper = longTermRentService.findById(longTermRentId);

        if(longTermRentWrapper.isPresent()) {
            longTermRentService.delete(longTermRentWrapper.get());
            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    @GetMapping("/admin/longTerm/detail/{longTermId}")
    public String rent_long_term_detail(Model model, @PathVariable Long longTermId) throws Exception {

        Optional<LongTermRent> longTermRentWrapper = longTermRentService.findById(longTermId);
        if(longTermRentWrapper.isPresent()){
            LongTermRent longTermRent = longTermRentWrapper.get();
            List<LongTermRentImage> longTermRentImageList = longTermRentImageService.findByLongTermRent(longTermRent);
            model.addAttribute("longTermRent", longTermRent);
            model.addAttribute("longTermRentImageList", longTermRentImageList);
        } else {
            throw new Exception("해당하는 차량을 찾을 수 없습니다.");
        }

        return "admin/longTerm_detail";
    }



    @PostMapping(value="/admin/longTerm/image", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public void postCampingCarReview(MultipartHttpServletRequest req) throws Exception  {

//        List<MultipartFile> multipartFileList = req.getFiles("file");
//        ArrayList<String> imageUrlList = new ArrayList<>();
//
//        for(int i=0; i<multipartFileList.size(); i++){
//            String imgPath = s3Service.upload(multipartFileList.get(i));
//            imageUrlList.add(imgPath);
//        }
//
//        List<MultipartFile> videoList = req.getFiles("video");
//        String videoURL = "";
//        for(int i=0; i<videoList.size(); i++){
//            if(i > 0){
//                throw new Exception("You can only upload one video.");
//            }
//            videoURL = s3Service.upload(videoList.get(i));
//        }
//
//        ReviewDTO reviewDTO = new ReviewDTO(req.getParameter("carName"), req.getParameter("text"), req.getParameter("nickName"), req.getParameter("startDate"), req.getParameter("endDate"),
//                multipartFileList, videoList, req.getParameter("password"));
//
//        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(req.getParameter("carName"));
//        reviewService.saveDTO(reviewDTO, campingCarPrice, imageUrlList, videoURL);
    }
}
