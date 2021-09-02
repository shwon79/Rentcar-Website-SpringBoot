package kr.carz.savecar.domain;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "TwoYearlyRent")
public class TwoYearlyRent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String category1; // 국산, 외제차의 분류
    private String category2; // 중형차, 경차 등 큰 분류
    private String deposit;
    private String name;
    private String cost_for_20Tk;
    private String cost_for_30Tk;
    private String cost_for_40Tk;
    private String cost_for_others;
    private String age_limit;
    private String cost_per_km;
}
