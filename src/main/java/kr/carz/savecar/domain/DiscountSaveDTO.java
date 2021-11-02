package kr.carz.savecar.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Getter
@Setter
public class DiscountSaveDTO {

    private String carNo;
    private String discount; // 할인금액

    public DiscountSaveDTO(String carNo, String discount) {
        this.carNo = carNo;
        this.discount = discount;
    }

    public Discount toEntity() {
        return Discount.builder()
                .carNo(carNo)
                .discount(discount)
                .build();

    }
}
