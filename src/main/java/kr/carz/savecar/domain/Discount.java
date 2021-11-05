package kr.carz.savecar.domain;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Discount")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discountId;

    private String carNo;
    private String discount; // 할인 퍼센트
    private String description;

    @Builder
    public Discount(Long discountId, String carNo, String discount, String description) {
        this.discountId = discountId;
        this.carNo = carNo;
        this.discount = discount;
        this.description = description;
    }
}
