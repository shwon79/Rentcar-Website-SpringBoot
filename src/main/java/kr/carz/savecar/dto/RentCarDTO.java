package kr.carz.savecar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentCarDTO {

    // 공통
    private String category1; // 국산, 외제차의 분류
    private String category2; // 중형차, 경차 등 큰 분류
    private String name;

    private String cost_for_others;
    private String age_limit;

    private String nameMoren;
    private Long start;
    private Long end;

    private String img_url;

    // 다른것
    private String deposit_monthly;
    private String deposit_yearly;
    private String deposit_twoYearly;

    private String cost_per_km_monthly;
    private String cost_per_km_yearly;
    private String cost_per_km_twoYearly;

    private String credit_monthly;
    private String credit_yearly;
    private String credit_twoYearly;

    // monthly
    private Float cost_for_2k;
    private Float cost_for_2_5k;
    private Float cost_for_3k;
    private Float cost_for_4k;

    // yearly
    private Float cost_for_20k;
    private Float cost_for_30k;
    private Float cost_for_40k;

    // twoYearly
    private Float cost_for_20Tk;
    private Float cost_for_30Tk;
    private Float cost_for_40Tk;
}
