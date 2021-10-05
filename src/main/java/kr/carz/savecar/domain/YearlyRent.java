package kr.carz.savecar.domain;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "YearlyRent")
public class YearlyRent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String category1; // 국산, 외제차의 분류
    private String category2; // 중형차, 경차 등 큰 분류
    private String deposit;
    private String name;
    private String cost_for_20k;
    private String cost_for_30k;
    private String cost_for_40k;
    private String cost_for_others;
    private String age_limit;
    private String cost_per_km;

    private String nameMoren;
    private Long start;
    private Long end;
}
