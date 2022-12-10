package kr.carz.savecar.dto;

import kr.carz.savecar.domain.LongTermRent;
import kr.carz.savecar.domain.LongTermRentImage;
import kr.carz.savecar.domain.Subscribe;
import kr.carz.savecar.domain.SubscribeImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscribeImageDTO {

    private Subscribe subscribe;
    private String imageUrl;

    public SubscribeImage toEntity() {

        return SubscribeImage.builder()
                .subscribe(subscribe)
                .imageUrl(imageUrl)
                .build();

    }
}
