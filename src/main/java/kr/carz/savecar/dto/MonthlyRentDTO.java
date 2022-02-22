package kr.carz.savecar.dto;

import kr.carz.savecar.domain.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyRentDTO {

    private String category1; // 국산, 외제차의 분류
    private String category2; // 중형차, 경차 등 큰 분류
    private String name;

    private String deposit;
    private Float cost_for_2k;
    private Float cost_for_2_5k;
    private Float cost_for_3k;
    private Float cost_for_4k;
    private String cost_for_others;
    private String age_limit;
    private String cost_per_km;

    private String nameMoren;
    private Long start;
    private Long end;

    private String credit;
    private String img_url;


    public MonthlyRent toEntity(YearlyRent yearlyRent, TwoYearlyRent twoYearlyRent) {

        return MonthlyRent.builder()
                .yearlyRent(yearlyRent)
                .twoYearlyRent(twoYearlyRent)
                .category1(category1)
                .deposit(deposit)
                .cost_for_2k(cost_for_2k)
                .cost_for_2_5k(cost_for_2_5k)
                .cost_for_3k(cost_for_3k)
                .cost_for_4k(cost_for_4k)
                .cost_for_others(cost_for_others)
                .age_limit(age_limit)
                .cost_per_km(cost_per_km)
                .nameMoren(nameMoren)
                .start(start)
                .end(end)
                .credit(credit)
                .img_url(img_url)
                .build();

    }
}
