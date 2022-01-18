package kr.carz.savecar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RealTimeDTO {

    private String carType;
    private String kilometer;
    private String reserve_able;
    private String rentTerm;

}
