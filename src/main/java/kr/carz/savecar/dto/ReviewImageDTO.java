package kr.carz.savecar.dto;

import kr.carz.savecar.domain.LongTermRent;
import kr.carz.savecar.domain.LongTermRentImage;
import kr.carz.savecar.domain.Review;
import kr.carz.savecar.domain.ReviewImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewImageDTO {

    private Review review;
    private String imageUrl;

    public ReviewImage toEntity() {

        return ReviewImage.builder()
                .review(review)
                .imageUrl(imageUrl)
                .build();

    }
}
