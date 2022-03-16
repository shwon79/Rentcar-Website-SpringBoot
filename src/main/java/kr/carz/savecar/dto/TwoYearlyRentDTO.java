package kr.carz.savecar.dto;

import kr.carz.savecar.domain.Admin;
import kr.carz.savecar.domain.TwoYearlyRent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TwoYearlyRentDTO {

    private String category1; // 국산, 외제차의 분류
    private String category2; // 중형차, 경차 등 큰 분류
    private String deposit;
    private String name;
    private double cost_for_20Tk;
    private double cost_for_30Tk;
    private double cost_for_40Tk;
    private double cost_for_20Tk_price;
    private double cost_for_30Tk_price;
    private double cost_for_40Tk_price;
    private String cost_for_others;
    private String age_limit;
    private String cost_per_km;

    private String nameMoren;
    private Long start;
    private Long end;

    private String credit;
    private String img_url;


    public TwoYearlyRent toEntity() {
        return TwoYearlyRent.builder()
                .category1(category1)
                .category2(category2)
                .deposit(deposit)
                .name(name)
                .cost_for_20Tk(cost_for_20Tk)
                .cost_for_30Tk(cost_for_30Tk)
                .cost_for_40Tk(cost_for_40Tk)
                .cost_for_20Tk_price(cost_for_20Tk_price)
                .cost_for_30Tk_price(cost_for_30Tk_price)
                .cost_for_40Tk_price(cost_for_40Tk_price)
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
