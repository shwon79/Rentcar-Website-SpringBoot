package kr.carz.savecar.dto;

import kr.carz.savecar.domain.CampingCarPrice;
import kr.carz.savecar.domain.Images;
import kr.carz.savecar.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    private String carName;
    private String text;
    private String nickName;
    private String startDate;
    private String endDate;

    private List<MultipartFile> video;
    private String password;

    public Review toEntity(CampingCarPrice carNameEntity, String videoURL) {

        return Review.builder()
                .carName(carNameEntity)
                .text(text)
                .nickName(nickName)
                .startDate(startDate)
                .endDate(endDate)
                .video(videoURL)
                .password(password)
                .build();

    }
}
