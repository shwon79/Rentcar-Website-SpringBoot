package kr.carz.savecar.dto;

import kr.carz.savecar.domain.Discount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountSaveDTO {

    private Long discountId;
    private String carNo;
    private String carName;
    private double discount; // 할인 퍼센트
    private String description;
    private int priceDisplay;

    public Discount toEntity() {
        return Discount.builder()
                .carNo(carNo)
                .carName(carName)
                .discount(discount)
                .description(description)
                .priceDisplay(priceDisplay)
                .build();

    }
}
