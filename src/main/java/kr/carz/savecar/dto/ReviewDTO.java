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

    private List<MultipartFile> imageList;
    private List<MultipartFile> video;
    private String password;

    public Review toEntity(CampingCarPrice carNameEntity, ArrayList<String> imageList, String videoURL) {

        Integer imageListSize = imageList.size();

        return Review.builder()
                .carName(carNameEntity)
                .text(text)
                .nickName(nickName)
                .startDate(startDate)
                .endDate(endDate)
                .image1((imageListSize > 0) ? imageList.get(0) : null)
                .image2((imageListSize > 1) ? imageList.get(1) : null)
                .image3((imageListSize > 2) ? imageList.get(2) : null)
                .image4((imageListSize > 3) ? imageList.get(3) : null)
                .image5((imageListSize > 4) ? imageList.get(4) : null)
                .image6((imageListSize > 5) ? imageList.get(5) : null)
                .image7((imageListSize > 6) ? imageList.get(6) : null)
                .image8((imageListSize > 7) ? imageList.get(7) : null)
                .image9((imageListSize > 8) ? imageList.get(8) : null)
                .image10((imageListSize > 9) ? imageList.get(9) : null)
                .video(videoURL)
                .password(password)
                .build();

    }
}
