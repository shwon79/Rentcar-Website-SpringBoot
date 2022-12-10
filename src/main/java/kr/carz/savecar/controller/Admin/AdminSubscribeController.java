package kr.carz.savecar.controller.Admin;

import kr.carz.savecar.domain.Subscribe;
import kr.carz.savecar.domain.SubscribeImage;
import kr.carz.savecar.dto.*;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminSubscribeController {
    private final SubscribeService subscribeService;
    private final SubscribeImageService subscribeImageService;
    private final S3Service s3Service;

    @Autowired
    public AdminSubscribeController(SubscribeService subscribeService, SubscribeImageService subscribeImageService,
                                    S3Service s3Service) {
        this.subscribeService = subscribeService;
        this.subscribeImageService = subscribeImageService;
        this.s3Service = s3Service;
    }

    @GetMapping("/admin/subscribe/register")
    public String rent_subscribe_register() {

        return "admin/subscribe_register";
    }
    @GetMapping("/admin/subscribe/main")
    public String rent_subscribe_main(Model model) {

        List<Subscribe> subscribeList = subscribeService.findAll();
        Collections.sort(subscribeList);

        model.addAttribute("subscribeList", subscribeList);

        return "admin/subscribe_main";
    }

    @PostMapping(value="/admin/subscribe", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public void postAdminSubscribe(MultipartHttpServletRequest req) throws Exception {

        SubscribeDTO subscribeDTO = new SubscribeDTO(req.getParameter("carName"),req.getParameter("carNum"),req.getParameter("carColor"),
                                                 req.getParameter("carYearModel"),req.getParameter("contractPeriod"),req.getParameter("contractKm"),
                                                    req.getParameter("contractPrice"),req.getParameter("contractDeposit"),req.getParameter("contractMaintenance"),
                                                    req.getParameter("newOld"),req.getParameter("fuel"),10000,req.getParameter("description"));
        Long subscribeId = subscribeService.saveDTO(subscribeDTO);

        List<MultipartFile> multipartFileList = req.getFiles("file");

        Optional<Subscribe> subscribeOptional = subscribeService.findById(subscribeId);

        if(subscribeOptional.isPresent()) {
            Subscribe subscribe = subscribeOptional.get();

            for (MultipartFile multipartFile : multipartFileList) {
                String imgPath = s3Service.upload(multipartFile);

                SubscribeImageDTO subscribeImageDTO = new SubscribeImageDTO(subscribe, imgPath);
                subscribeImageService.saveDTO(subscribeImageDTO);
            }
        } else {
            throw new Exception("문제가 발생하였습니다.");
        }
    }


    @PutMapping("/admin/subscribe/{subscribeId}")
    @ResponseBody
    public void put_subscribe_rent(HttpServletResponse res, @RequestBody SubscribeDTO subscribeDTO, @PathVariable Long subscribeId) throws IOException {

        JSONObject jsonObject = new JSONObject();

        Optional<Subscribe> subscribeOptional = subscribeService.findById(subscribeId);
        if(subscribeOptional.isPresent()){
            subscribeService.updateByDTO(subscribeOptional.get(), subscribeDTO);

            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    @DeleteMapping("/admin/subscribe/{subscribeId}")
    @ResponseBody
    public void delete_rent_car_price(HttpServletResponse res, @PathVariable Long subscribeId) throws IOException {

        JSONObject jsonObject = new JSONObject();

        Optional<Subscribe> subscribeOptional = subscribeService.findById(subscribeId);

        if(subscribeOptional.isPresent()) {
            Subscribe subscribe = subscribeOptional.get();
            List<SubscribeImage> subscribeImageList = subscribeImageService.findBySubscribe(subscribe);
            for(SubscribeImage subscribeImage : subscribeImageList){
                subscribeImageService.delete(subscribeImage);
            }

            subscribeService.delete(subscribeOptional.get());
            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    @GetMapping("/admin/subscribe/detail/{subscribeId}")
    public String rent_subscribe_detail(Model model, @PathVariable Long subscribeId) throws Exception {

        Optional<Subscribe> subscribeOptional = subscribeService.findById(subscribeId);
        if(subscribeOptional.isPresent()){
            Subscribe subscribe = subscribeOptional.get();
            List<SubscribeImage> subscribeImageList = subscribeImageService.findBySubscribe(subscribe);
            model.addAttribute("subscribe", subscribe);
            model.addAttribute("subscribeImageList", subscribeImageList);
        } else {
            throw new Exception("해당하는 차량을 찾을 수 없습니다.");
        }

        return "admin/subscribe_detail";
    }

    @GetMapping("/admin/subscribe/detail/image/{subscribeId}")
    public String rent_subscribe_detail_image(Model model, @PathVariable Long subscribeId) throws Exception {

        Optional<Subscribe> subscribeOptional = subscribeService.findById(subscribeId);
        if(subscribeOptional.isPresent()){
            Subscribe subscribe = subscribeOptional.get();
            List<SubscribeImage> subscribeImageList = subscribeImageService.findBySubscribe(subscribe);
            model.addAttribute("subscribe", subscribe);
            model.addAttribute("subscribeImageList", subscribeImageList);
        } else {
            throw new Exception("해당하는 차량을 찾을 수 없습니다.");
        }

        return "admin/subscribe_detail_image";
    }


    @PostMapping(value="/admin/subscribe/image", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public void postSubscribeImage(MultipartHttpServletRequest req) throws Exception {

        List<MultipartFile> multipartFileList = req.getFiles("file");

        Optional<Subscribe> subscribeOptional = subscribeService.findById(Long.parseLong(req.getParameter("subscribeId")));

        if(subscribeOptional.isPresent()) {
            Subscribe subscribe = subscribeOptional.get();
            for (MultipartFile multipartFile : multipartFileList) {
                String imgPath = s3Service.upload(multipartFile);

                SubscribeImageDTO dto = new SubscribeImageDTO(subscribe, imgPath);
                subscribeImageService.saveDTO(dto);
            }
        } else {
            throw new Exception("이미지를 등록할 기준이 되는 차량이 없습니다.");
        }
    }


    @DeleteMapping(value="/admin/subscribe/image/{imageId}")
    @ResponseBody
    public void deleteSubscribeImage(HttpServletResponse res, @PathVariable Long imageId) throws Exception {

        JSONObject jsonObject = new JSONObject();

        Optional<SubscribeImage> subscribeImageOptional = subscribeImageService.findById(imageId);
        if(subscribeImageOptional.isPresent()){
            subscribeImageService.delete(subscribeImageOptional.get());
            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    @PutMapping(value = "/admin/subscribe/sequence")
    @ResponseBody
    public void put_subscribe_sequence(HttpServletResponse res, @RequestBody ImagesVO imagesVO) throws IOException {

        JSONObject jsonObject = new JSONObject();

        int problemFlg = 0;
        for(ImageTitleVO imageTitleVO : imagesVO.getImageTitleList()){

            Optional<Subscribe> subscribeOptional = subscribeService.findById(imageTitleVO.getImageId());
            if (subscribeOptional.isPresent()) {

                Subscribe subscribe = subscribeOptional.get();
                Long originalId = subscribe.getSubscribeId();

                if(originalId == imageTitleVO.getTitle()) continue;

                subscribe.setSequence(imageTitleVO.getTitle());
                subscribeService.save(subscribe);
            } else {
                problemFlg = 1;
            }
        }

        if(problemFlg == 0){
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
