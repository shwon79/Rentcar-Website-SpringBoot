package kr.carz.savecar.domain;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Data
@NoArgsConstructor
@Table(name = "Discount")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discountId;

    private String carNo;
    private String carName;
    private double discount; // 할인 퍼센트
    private String description;

    @Builder
    public Discount(Long discountId, String carNo, String carName, double discount, String description) {
        this.discountId = discountId;
        this.carNo = carNo;
        this.carName = carName;
        this.discount = discount;
        this.description = description;
    }
}
