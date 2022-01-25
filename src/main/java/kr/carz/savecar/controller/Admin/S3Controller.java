package kr.carz.savecar.controller.Admin;

import kr.carz.savecar.domain.Images;
import kr.carz.savecar.dto.ImagesDTO;
import kr.carz.savecar.service.AdminService;
import kr.carz.savecar.service.ImagesService;
import kr.carz.savecar.service.S3Service;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@Controller
public class S3Controller {
    private final S3Service s3Service;
    private final ImagesService imagesService;

    @Autowired
    public S3Controller(S3Service s3Service, ImagesService imagesService) {
        this.s3Service = s3Service;
        this.imagesService = imagesService;
    }

    @GetMapping("/admin/image/{title}")
    @ResponseBody
    public void getAdminImage(HttpServletResponse res, @PathVariable String title) throws IOException {

        JSONObject jsonObject = new JSONObject();

        Optional<Images> imagesWrapper = imagesService.findImageByTitle(title);

        if(imagesWrapper.isPresent()){
            Images image = imagesWrapper.get();
            jsonObject.put("image_url", image.getUrl());
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }

    @PostMapping("/admin/image")
    @ResponseBody
    public void postAdminImage(HttpServletResponse res, ImagesDTO imagesDTO, MultipartFile file) throws IOException {
        String imgPath = s3Service.upload(file);
        imagesDTO.setUrl(imgPath);

        imagesService.save(imagesDTO);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", 1);

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }
}
