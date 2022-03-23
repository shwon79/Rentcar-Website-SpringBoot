package kr.carz.savecar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ReviewImage")
public class ReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "reviewId")
    private Review review;
    private String imageUrl;

    @Builder
    public ReviewImage(Long imageId, Review review, String imageUrl) {
        this.imageId = imageId;
        this.review = review;
        this.imageUrl = imageUrl;
    }
}
