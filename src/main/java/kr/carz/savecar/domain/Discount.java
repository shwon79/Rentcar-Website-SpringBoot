package kr.carz.savecar.domain;


import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CampingCar")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String carNo;

    private String discount; // 할인금액

    @Builder
    public Discount(String carNo, String discount) {
        this.carNo = carNo;
        this.discount = discount;
    }
}
