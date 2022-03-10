package kr.carz.savecar.dto;

import kr.carz.savecar.domain.YearlyRent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class YearlyRentDTO {

    private String category1; // 국산, 외제차의 분류
    private String category2; // 중형차, 경차 등 큰 분류
    private String deposit;
    private String name;
    private double cost_for_20k;
    private double cost_for_30k;
    private double cost_for_40k;
    private String cost_for_others;
    private String age_limit;
    private String cost_per_km;

    private String nameMoren;
    private Long start;
    private Long end;

    private String credit;
    private String img_url;

    public YearlyRent toEntity() {
        return YearlyRent.builder()
                .category1(category1)
                .category2(category2)
                .deposit(deposit)
                .name(name)
                .cost_for_20k(cost_for_20k)
                .cost_for_30k(cost_for_30k)
                .cost_for_40k(cost_for_40k)
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
