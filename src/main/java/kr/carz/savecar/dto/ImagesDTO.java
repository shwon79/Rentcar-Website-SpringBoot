package kr.carz.savecar.dto;

import kr.carz.savecar.domain.CampingCarReservation;
import kr.carz.savecar.domain.Images;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ImagesDTO {

    private String title;
    private String url;

    public Images toEntity() {
        return Images.builder()
                .title(title)
                .url(url)
                .build();

    }
}
