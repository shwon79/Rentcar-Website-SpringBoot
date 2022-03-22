package kr.carz.savecar.dto;

import kr.carz.savecar.domain.LongTermRent;
import kr.carz.savecar.domain.LongTermRentImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LongTermRentImageDTO {

    private LongTermRent longTermRent;
    private String imageUrl;

    public LongTermRentImage toEntity() {

        return LongTermRentImage.builder()
                .longTermRent(longTermRent)
                .imageUrl(imageUrl)
                .build();

    }
}
