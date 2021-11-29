package kr.carz.savecar.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Getter
@Setter
public class DiscountSaveDTO {

    private Long discountId;
    private String carNo;
    private String discount; // 할인 퍼센트
    private String description;

    public DiscountSaveDTO(Long discountId, String carNo, String discount, String description) {
        this.discountId = discountId;
        this.carNo = carNo;
        this.discount = discount;
        this.description = description;
    }

    public Discount toEntity() {
        return Discount.builder()
                .discountId(discountId)
                .carNo(carNo)
                .discount(discount)
                .description(description)
                .build();

    }
}
