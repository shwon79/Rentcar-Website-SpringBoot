package kr.carz.savecar.service;

import kr.carz.savecar.domain.LongTermRent;
import kr.carz.savecar.domain.LongTermRentImage;
import kr.carz.savecar.domain.Review;
import kr.carz.savecar.domain.ReviewImage;
import kr.carz.savecar.dto.LongTermRentImageDTO;
import kr.carz.savecar.dto.ReviewImageDTO;
import kr.carz.savecar.repository.LongTermRentImageRepository;
import kr.carz.savecar.repository.ReviewImageRepository;

import java.util.List;
import java.util.Optional;

public class ReviewImageService {

    private final ReviewImageRepository reviewImageRepository;

    public ReviewImageService(ReviewImageRepository reviewImageRepository){
        this.reviewImageRepository = reviewImageRepository;
    }

    public List<ReviewImage> findAll(){
        return reviewImageRepository.findAll();
    }

    public Optional<ReviewImage> findById(Long imageId){
        return reviewImageRepository.findById(imageId);
    }
    public List<ReviewImage> findByReview(Review review){
        return reviewImageRepository.findByReview(review);
    }
    public Long saveDTO(ReviewImageDTO dto){
        return reviewImageRepository.save(dto.toEntity()).getImageId();
    }

    public void delete(ReviewImage image){
        reviewImageRepository.delete(image);
    }
}