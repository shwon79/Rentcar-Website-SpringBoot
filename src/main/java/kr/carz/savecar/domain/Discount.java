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
    private Long carNo;

    private String discount; // 할인금액

    @Builder
    public Discount(Long carNo, String discount) {
        this.carNo = carNo;
        this.discount = discount;
    }
}
