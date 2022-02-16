package kr.carz.savecar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "car_name")
    private CampingCarPrice carName; // 캠핑카 id

    @Column(columnDefinition = "LONGTEXT")
    private String text;

    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String image6;
    private String image7;
    private String image8;
    private String image9;
    private String image10;
    private String video;
    private String password;


    @Builder
    public Review(Long reviewId, CampingCarPrice carName, String text, String image1, String image2, String image3, String image4, String image5,
                                                                        String image6, String image7, String image8, String image9, String image10, String video, String password) {
        this.carName = carName;
        this.text = text;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.image5 = image5;
        this.image6 = image6;
        this.image7 = image7;
        this.image8 = image8;
        this.image9 = image9;
        this.image10 = image10;
        this.video = video;
        this.password = password;
    }
}
