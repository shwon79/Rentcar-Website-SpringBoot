package kr.carz.savecar.domain;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CampingCar")
public class CampingCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String category1; // 트래벌, 리무진, 유럽의 분류
    private String category2; // 디럭스, 스위트의 분류
    private String season; // 성수기, 비수기
    private String rent_period;
    private String price;
    private String deposit;
    private String rent_for_month;
}
