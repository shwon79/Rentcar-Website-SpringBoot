package kr.carz.savecar.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RealTimeDTO {

    private String carType;
    private String kilometer;
    private String reserve_able;
    private String rentTerm;

    public RealTimeDTO(String carType, String kilometer, String reserve_able, String rentTerm) {
        this.carType = carType;
        this.kilometer = kilometer;
        this.reserve_able = reserve_able;
        this.rentTerm = rentTerm;
    }

}