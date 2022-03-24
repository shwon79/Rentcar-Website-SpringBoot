package kr.carz.savecar.service;

import kr.carz.savecar.domain.CampingCarPrice;
import kr.carz.savecar.domain.Images;
import kr.carz.savecar.domain.Review;
import kr.carz.savecar.dto.ImagesDTO;
import kr.carz.savecar.dto.ReviewDTO;
import kr.carz.savecar.dto.ReviewTextVO;
import kr.carz.savecar.repository.ImagesRepository;
import kr.carz.savecar.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    public List<Review> findByCarName(CampingCarPrice carName){
        return reviewRepository.findByCarName(carName);
    }
    public Optional<Review> findByReviewId(Long reviewId){
        return reviewRepository.findByReviewId(reviewId);
    }

    public List<Review> findAllReview(){
        return reviewRepository.findAll();
    }

    public Long save(Review review) {
        return reviewRepository.save(review).getReviewId();
    }

    public Long updateTextVo(Review review, ReviewTextVO reviewTextVO, CampingCarPrice campingCarPrice) {

        review.setCarName(campingCarPrice);
        review.setText(reviewTextVO.getText());
        review.setNickName(reviewTextVO.getNickName());
        review.setStartDate(reviewTextVO.getStartDate());
        review.setEndDate(reviewTextVO.getEndDate());
        review.setPassword(reviewTextVO.getPassword());

        return reviewRepository.save(review).getReviewId();
    }

    public Long saveDTO(ReviewDTO dto, CampingCarPrice carName, String videoURL) {

        return reviewRepository.save(dto.toEntity(carName, videoURL)).getReviewId();
    }

    public void delete(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public void deleteReview(Review review) {
        reviewRepository.delete(review);
    }

}