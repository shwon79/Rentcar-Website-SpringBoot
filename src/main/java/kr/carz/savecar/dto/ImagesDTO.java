package kr.carz.savecar.dto;

import kr.carz.savecar.domain.CampingCarPrice;
import kr.carz.savecar.domain.Images;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class ImagesDTO {

    private String carName;
    private String title;
    private String url;
    private String isUploaded;
    private MultipartFile file;

    public Images toEntity(CampingCarPrice carName) {

        return Images.builder()
                .carName(carName)
                .title(title)
                .url(url)
                .isUploaded(isUploaded)
                .build();

    }
}
