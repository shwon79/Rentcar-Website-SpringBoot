package kr.carz.savecar.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RealTimeDto {

    private String carType;
    private String kilometer;
    private String reserve_able;

    public RealTimeDto(String carType, String kilometer, String reserve_able) {
        this.carType = carType;
        this.kilometer = kilometer;
        this.reserve_able = reserve_able;
    }

}
