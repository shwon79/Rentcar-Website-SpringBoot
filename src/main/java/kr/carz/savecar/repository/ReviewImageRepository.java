package kr.carz.savecar.repository;

import kr.carz.savecar.domain.Review;
import kr.carz.savecar.domain.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {

    List<ReviewImage> findAll();
    List<ReviewImage> findByReview(Review review);
}
