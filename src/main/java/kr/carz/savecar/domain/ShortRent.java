package kr.carz.savecar.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ShortRent")
public class ShortRent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String category1; // 국산, 외제차의 분류
    private String name;
    private String cost_for_three_days_600;
    private String cost_for_three_days_900;
    private String cost_for_three_days_1200;
    private String cost_for_three_days_insurance;
    private String cost_for_three_days_age;
    private String extra_cost_per_day_200;
    private String extra_cost_per_day_300;
    private String extra_cost_per_day_400;
    private String extra_cost_per_day_insurance;
    private String extra_cost_per_day_age;
    private String cost_per_km;
}
