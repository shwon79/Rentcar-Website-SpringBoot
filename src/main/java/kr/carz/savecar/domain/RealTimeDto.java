package kr.carz.savecar.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RealTimeDto {

    private String carType;
    private String kilometer;
    private String reserve_able;
    private String rentTerm;

    public RealTimeDto(String carType, String kilometer, String reserve_able, String rentTerm) {
        this.carType = carType;
        this.kilometer = kilometer;
        this.reserve_able = reserve_able;
        this.rentTerm = rentTerm;
    }

}
