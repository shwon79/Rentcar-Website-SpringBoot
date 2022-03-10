package kr.carz.savecar.dto;

import kr.carz.savecar.domain.CampingCarMainText;
import kr.carz.savecar.domain.CampingCarPrice;
import kr.carz.savecar.domain.Images;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CampingCarMainTextDTO {

    private String carName;
    private int title;
    private String url;
    private String isUploaded;
    private MultipartFile file;

    public CampingCarMainText toEntity(CampingCarPrice carName) {

        return CampingCarMainText.builder()
                .carName(carName)
                .title(title)
                .url(url)
                .isUploaded(isUploaded)
                .build();

    }
}
