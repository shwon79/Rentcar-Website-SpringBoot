package kr.carz.savecar.controller.Admin;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.dto.LongTermRentDTO;
import kr.carz.savecar.dto.LongTermRentImageDTO;
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
import javax.swing.text.html.Option;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminLongTermRentController {
    private final LongTermRentService longTermRentService;
    private final LongTermRentImageService longTermRentImageService;
    private final S3Service s3Service;

    @Autowired
    public AdminLongTermRentController(LongTermRentService longTermRentService, LongTermRentImageService longTermRentImageService,
                                       S3Service s3Service) {
        this.longTermRentService = longTermRentService;
        this.longTermRentImageService = longTermRentImageService;
        this.s3Service = s3Service;
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

    @PostMapping(value="/admin/longTerm", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public void postAdminLongTerm(MultipartHttpServletRequest req) throws Exception {

        LongTermRentDTO longTermRentDTO = new LongTermRentDTO(req.getParameter("carName"),req.getParameter("carNum"),req.getParameter("carColor"),
                                                 req.getParameter("carYearModel"),req.getParameter("contractPeriod"),req.getParameter("contractKm"),
                                                    req.getParameter("contractPrice"),req.getParameter("contractDeposit"),req.getParameter("contractMaintenance"),
                                                    req.getParameter("newOld"),req.getParameter("fuel"));
        Long longTermRentId = longTermRentService.saveDTO(longTermRentDTO);


        List<MultipartFile> multipartFileList = req.getFiles("file");

        Optional<LongTermRent> longTermRentWrapper = longTermRentService.findById(longTermRentId);

        if(longTermRentWrapper.isPresent()) {
            LongTermRent longTermRent = longTermRentWrapper.get();

            for (int i = 0; i < multipartFileList.size(); i++) {
                String imgPath = s3Service.upload(multipartFileList.get(i));

                LongTermRentImageDTO longTermRentImageDTO = new LongTermRentImageDTO(longTermRent, imgPath);
                longTermRentImageService.saveDTO(longTermRentImageDTO);
            }
        } else {
            throw new Exception("이미지를 등록할 기준이 되는 차량이 없습니다.");
        }
    }


    @PutMapping("/admin/longTerm/{longTermRentId}")
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
    public void postCampingCarReview(MultipartHttpServletRequest req) throws Exception {

        List<MultipartFile> multipartFileList = req.getFiles("file");

        Optional<LongTermRent> longTermRentWrapper = longTermRentService.findById(Long.parseLong(req.getParameter("longTermRentId")));

        if(longTermRentWrapper.isPresent()) {
            LongTermRent longTermRent = longTermRentWrapper.get();
            List<LongTermRentImage> longTermRentImageList = longTermRentImageService.findByLongTermRent(longTermRent);
            for(LongTermRentImage image : longTermRentImageList){
                longTermRentImageService.delete(image);
            }

            for (int i = 0; i < multipartFileList.size(); i++) {
                String imgPath = s3Service.upload(multipartFileList.get(i));

                LongTermRentImageDTO dto = new LongTermRentImageDTO(longTermRent, imgPath);
                longTermRentImageService.saveDTO(dto);
            }
        } else {
            throw new Exception("등록할 기준이 되는 차량이 없습니다.");
        }
    }
}
