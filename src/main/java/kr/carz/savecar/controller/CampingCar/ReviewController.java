package kr.carz.savecar.controller.CampingCar;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.dto.CampingCarReservationDTO;
import kr.carz.savecar.dto.ReviewDTO;
import kr.carz.savecar.dto.ReviewImageDTO;
import kr.carz.savecar.dto.ReviewTextVO;
import kr.carz.savecar.service.*;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ReviewController {
    private final S3Service s3Service;
    private final ReviewService reviewService;
    private final ReviewImageService reviewImageService;
    private final CampingCarPriceService campingCarPriceService;

    @Autowired
    public ReviewController(S3Service s3Service,
                            ReviewService reviewService, ReviewImageService reviewImageService,
                            CampingCarPriceService campingCarPriceService) {
        this.s3Service = s3Service;
        this.reviewService = reviewService;
        this.reviewImageService = reviewImageService;
        this.campingCarPriceService = campingCarPriceService;
    }


    @PostMapping(value="/camping/review", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public void postCampingCarReview(MultipartHttpServletRequest req) throws Exception  {

        List<MultipartFile> multipartFileList = req.getFiles("file");

        List<MultipartFile> videoList = req.getFiles("video");
        String videoURL = "";
        for(int i=0; i<videoList.size(); i++){
            if(i > 0){
                throw new Exception("You can only upload one video.");
            }
            videoURL = s3Service.upload(videoList.get(i));
        }

        ReviewDTO reviewDTO = new ReviewDTO(req.getParameter("carName"), req.getParameter("text"), req.getParameter("nickName"),
                                            req.getParameter("startDate"), req.getParameter("endDate"), videoList, req.getParameter("password"));

        CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(req.getParameter("carName"));
        Long reviewId = reviewService.saveDTO(reviewDTO, campingCarPrice, videoURL);
        Optional<Review> reviewWrapper = reviewService.findByReviewId(reviewId);

        if(reviewWrapper.isPresent()) {
            Review review = reviewWrapper.get();
            for (int i = 0; i < multipartFileList.size(); i++) {
                String imgPath = s3Service.upload(multipartFileList.get(i));
                ReviewImageDTO reviewImageDTO = new ReviewImageDTO(review, imgPath);
                reviewImageService.saveDTO(reviewImageDTO);
            }
        }
    }



    @PutMapping(value="/camping/review/text/{reviewId}")
    @ResponseBody
    public void putCampingCarReviewText(HttpServletResponse res, @RequestBody ReviewTextVO reviewTextVO, @PathVariable Long reviewId) throws Exception  {

        JSONObject jsonObject = new JSONObject();
        Optional<Review> reviewWrapper = reviewService.findByReviewId(reviewId);
        if(reviewWrapper.isPresent()) {
            Review review = reviewWrapper.get();
            CampingCarPrice campingCarPrice = campingCarPriceService.findCampingCarPriceByCarName(reviewTextVO.getCarName());

            reviewService.updateTextVo(review, reviewTextVO, campingCarPrice);
            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    @DeleteMapping(value="/camping/review/{reviewId}")
    @ResponseBody
    public void deleteCampingCarReview(HttpServletResponse res, @PathVariable Long reviewId) throws Exception {

        JSONObject jsonObject = new JSONObject();

        Optional<Review> reviewWrapper = reviewService.findByReviewId(reviewId);
        if(reviewWrapper.isPresent()){
            reviewService.deleteReview(reviewWrapper.get());
            jsonObject.put("result", 1);
        } else {
            jsonObject.put("result", 0);
        }

        PrintWriter pw = res.getWriter();
        pw.print(jsonObject);
        pw.flush();
        pw.close();
    }


    @PostMapping(value="/camping/review/image", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public void postCampingReviewImage(MultipartHttpServletRequest req) throws Exception {

        List<MultipartFile> multipartFileList = req.getFiles("file");

        Optional<Review> reviewWrapper = reviewService.findByReviewId(Long.parseLong(req.getParameter("reviewId")));

        if(reviewWrapper.isPresent()) {
            Review review = reviewWrapper.get();
            for (MultipartFile multipartFile : multipartFileList) {
                String imgPath = s3Service.upload(multipartFile);

                ReviewImageDTO reviewImageDTO = new ReviewImageDTO(review, imgPath);
                reviewImageService.saveDTO(reviewImageDTO);
            }
        } else {
            throw new Exception("이미지를 등록할 기준이 되는 리뷰가 없습니다.");
        }
    }


    @DeleteMapping(value="/camping/review/image/{imageId}")
    @ResponseBody
    public void deleteCampingReviewImage(HttpServletResponse res, @PathVariable Long imageId) throws Exception {

        JSONObject jsonObject = new JSONObject();

        Optional<ReviewImage> reviewImageWrapper = reviewImageService.findById(imageId);
        if(reviewImageWrapper.isPresent()){
            reviewImageService.delete(reviewImageWrapper.get());
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
