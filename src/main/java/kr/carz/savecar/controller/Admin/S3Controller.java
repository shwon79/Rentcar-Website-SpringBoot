package kr.carz.savecar.controller.Admin;

import kr.carz.savecar.domain.Images;
import kr.carz.savecar.service.ImagesService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@Controller
public class S3Controller {
    private final ImagesService imagesService;

    @Autowired
    public S3Controller(ImagesService imagesService) {
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

}
