package kr.carz.savecar.dto;

import kr.carz.savecar.domain.CampingCarPrice;
import kr.carz.savecar.domain.Images;
import kr.carz.savecar.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

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

    private List<MultipartFile> imageList;
    private MultipartFile video;
    private String password;

    public Review toEntity(CampingCarPrice carNameEntity, String [] imageList, String videoURL) {

        return Review.builder()
                .carName(carNameEntity)
                .text(text)
                .nickName(nickName)
                .startDate(startDate)
                .endDate(endDate)
                .image1(imageList[0])
                .image2(imageList[1])
                .image3(imageList[2])
                .image4(imageList[3])
                .image5(imageList[4])
                .image6(imageList[5])
                .image7(imageList[6])
                .image8(imageList[7])
                .image9(imageList[8])
                .image10(imageList[9])
                .video(videoURL)
                .password(password)
                .build();

    }
}
