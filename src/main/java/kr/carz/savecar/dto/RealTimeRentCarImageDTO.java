package kr.carz.savecar.dto;

import kr.carz.savecar.domain.RealTimeRentCar;
import kr.carz.savecar.domain.RealTimeRentCarImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RealTimeRentCarImageDTO {

    private RealTimeRentCar realTimeRentCar;
    private String imageUrl;

    public RealTimeRentCarImage toEntity() {

        return RealTimeRentCarImage.builder()
                .realTimeRentCar(realTimeRentCar)
                .imageUrl(imageUrl)
                .build();

    }
}
