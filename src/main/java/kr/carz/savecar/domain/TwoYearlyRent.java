package kr.carz.savecar.domain;


import lombok.Data;

import javax.persistence.*;

@Entity
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
    private Float cost_for_20Tk;
    private Float cost_for_30Tk;
    private Float cost_for_40Tk;
    private String cost_for_others;
    private String age_limit;
    private String cost_per_km;

    private String nameMoren;
    private Long start;
    private Long end;

    private String credit;
    private String img_url;

    @Override
    public int compareTo(TwoYearlyRent o) {
        return this.name.compareTo(o.name);
    }
}
