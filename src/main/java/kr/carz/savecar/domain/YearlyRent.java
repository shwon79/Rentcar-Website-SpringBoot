package kr.carz.savecar.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "YearlyRent")
public class YearlyRent implements Comparable<YearlyRent>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @OneToOne(mappedBy = "yearlyRent")
    private MonthlyRent monthlyRent;

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

    @Builder
    public YearlyRent(String category1, String category2, String name, String deposit
            , double cost_for_20k, double cost_for_30k, double cost_for_40k, String cost_for_others, String age_limit, String cost_per_km, String nameMoren
            , Long start, Long end, String credit, String img_url) {
        this.category1 = category1;
        this.category2 = category2;
        this.name = name;
        this.deposit = deposit;

        this.cost_for_20k = cost_for_20k;
        this.cost_for_30k = cost_for_30k;
        this.cost_for_40k = cost_for_40k;
        this.cost_for_others = cost_for_others;
        this.age_limit = age_limit;
        this.cost_per_km = cost_per_km;
        this.nameMoren = nameMoren;
        this.start = start;
        this.end = end;
        this.credit = credit;
        this.img_url = img_url;
    }

    @Override
    public int compareTo(YearlyRent o) {
        return this.name.compareTo(o.name);
    }
}
