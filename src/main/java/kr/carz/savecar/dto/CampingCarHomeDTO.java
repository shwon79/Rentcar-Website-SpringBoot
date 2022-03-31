package kr.carz.savecar.dto;

import kr.carz.savecar.domain.CampingCarHome;
import kr.carz.savecar.domain.CampingCarMainText;
import kr.carz.savecar.domain.CampingCarPrice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CampingCarHomeDTO {

    private String title;
    private String description;
    private int sequence;
    private int columnNum;

    public CampingCarHome toEntity() {

        return CampingCarHome.builder()
                .title(title)
                .description(description)
                .sequence(sequence)
                .columnNum(columnNum)
                .build();

    }
}
