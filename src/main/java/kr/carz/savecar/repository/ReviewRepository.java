package kr.carz.savecar.repository;

import kr.carz.savecar.domain.CampingCarPrice;
import kr.carz.savecar.domain.Images;
import kr.carz.savecar.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByCarName(CampingCarPrice carName);
    Optional<Review> findByReviewId(Long reviewId);

    List<Review> findAll();
}
