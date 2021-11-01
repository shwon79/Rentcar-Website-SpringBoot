package kr.carz.savecar.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountDTO {

    private String carNo;
    private String discount;

    public DiscountDTO(String carNo, String discount) {
        this.carNo = carNo;
        this.discount = discount;
    }
}
