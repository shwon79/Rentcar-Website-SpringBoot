package kr.carz.savecar.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicUpdate
@Data
@Table(name = "TwoYearlyRent")
public class TwoYearlyRent implements Comparable<TwoYearlyRent> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @OneToOne(mappedBy = "twoYearlyRent")
    private MonthlyRent monthlyRent;

    private String category1; // 국산, 외제차의 분류
    private String category2; // 중형차, 경차 등 큰 분류
    private String deposit;
    private String name;
    private double cost_for_20Tk;
    private double cost_for_30Tk;
    private double cost_for_40Tk;
    private String cost_for_others;
    private String age_limit;
    private String cost_per_km;

    private String nameMoren;
    private Long start;
    private Long end;

    private String credit;
    private String img_url;


    @Builder
    public TwoYearlyRent(String category1, String category2, String name, String deposit
            , double cost_for_20Tk, double cost_for_30Tk, double cost_for_40Tk, String cost_for_others, String age_limit, String cost_per_km, String nameMoren
            , Long start, Long end, String credit, String img_url) {
        this.category1 = category1;
        this.category2 = category2;
        this.name = name;
        this.deposit = deposit;

        this.cost_for_20Tk = cost_for_20Tk;
        this.cost_for_30Tk = cost_for_30Tk;
        this.cost_for_40Tk = cost_for_40Tk;
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
    public int compareTo(TwoYearlyRent o) {
        return this.name.compareTo(o.name);
    }
}
