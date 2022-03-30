package kr.carz.savecar.dto;

import kr.carz.savecar.domain.CampingCarHome;
import kr.carz.savecar.domain.CampingCarHomeImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CampingCarHomeImageDTO {

    private Long homeId;
    private int sequence;
    private MultipartFile file;

    public CampingCarHomeImage toEntity(CampingCarHome campingCarHome, String imageUrl) {

        return CampingCarHomeImage.builder()
                .campingCarHome(campingCarHome)
                .imageUrl(imageUrl)
                .sequence(sequence)
                .build();
    }
}
