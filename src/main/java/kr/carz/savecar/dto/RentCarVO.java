package kr.carz.savecar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentCarVO {

    // 공통
    private String category1; // 국산, 외제차의 분류
    private String category2; // 중형차, 경차 등 큰 분류
    private String name;

    private String cost_for_others;
    private String age_limit;

    private String nameMoren;
    private Long start;
    private Long end;

    private MultipartFile file;
    private int isTwoYearExist;  // 1이면 24개월 有, 2이면 24개월 無

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
    private double cost_for_2k;
    private double cost_for_2_5k;
    private double cost_for_3k;
    private double cost_for_4k;
    private double cost_for_2_5k_price;
    private double cost_for_3k_price;
    private double cost_for_4k_price;

    // yearly
    private double cost_for_20k;
    private double cost_for_30k;
    private double cost_for_40k;
    private double cost_for_20k_price;
    private double cost_for_30k_price;
    private double cost_for_40k_price;

    // twoYearly
    private double cost_for_20Tk;
    private double cost_for_30Tk;
    private double cost_for_40Tk;
    private double cost_for_20Tk_price;
    private double cost_for_30Tk_price;
    private double cost_for_40Tk_price;
}
