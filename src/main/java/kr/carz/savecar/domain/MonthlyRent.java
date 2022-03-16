package kr.carz.savecar.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicUpdate
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MonthlyRent")
public class MonthlyRent implements Comparable<MonthlyRent> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @OneToOne
    @JoinColumn(name = "yearlyRent")
    private YearlyRent yearlyRent; // 12개월 외래키

    @OneToOne
    @JoinColumn(name = "twoYearlyRent")
    private TwoYearlyRent twoYearlyRent; // 24개월 외래키

    private String category1; // 국산, 외제차의 분류
    private String category2; // 중형차, 경차 등 큰 분류

    @Column(unique = true)
    private String name;

    private String deposit;
    private double cost_for_2k;
    private double cost_for_2_5k;
    private double cost_for_3k;
    private double cost_for_4k;
    private double cost_for_2_5k_price;
    private double cost_for_3k_price;
    private double cost_for_4k_price;
    private String cost_for_others;
    private String age_limit;
    private String cost_per_km;

    private String nameMoren;
    private Long start;
    private Long end;

    private String credit;
    private String img_url;


    @OneToMany(mappedBy = "monthlyRent", targetEntity=RealTimeRentCar.class)
    private List<RealTimeRentCar> realTimeRentList = new ArrayList<>();

    public void addCampingCarPriceRates(RealTimeRentCar realTimeRent)
    {
        realTimeRent.setMonthlyRent(this);
        this.realTimeRentList.add(realTimeRent);
    }
    @Builder
    public MonthlyRent(YearlyRent yearlyRent, TwoYearlyRent twoYearlyRent, String category1, String category2, String name, String deposit, double cost_for_2k
            , double cost_for_2_5k, double cost_for_3k, double cost_for_4k, double cost_for_2_5k_price, double cost_for_3k_price, double cost_for_4k_price
            , String cost_for_others, String age_limit, String cost_per_km, String nameMoren
            , Long start, Long end, String credit, String img_url) {
        this.yearlyRent = yearlyRent;
        this.twoYearlyRent = twoYearlyRent;
        this.category1 = category1;
        this.category2 = category2;
        this.name = name;
        this.deposit = deposit;

        this.cost_for_2k = cost_for_2k;
        this.cost_for_2_5k = cost_for_2_5k;
        this.cost_for_3k = cost_for_3k;
        this.cost_for_4k = cost_for_4k;
        this.cost_for_2_5k_price = cost_for_2_5k_price;
        this.cost_for_3k_price = cost_for_3k_price;
        this.cost_for_4k_price = cost_for_4k_price;
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
    public int compareTo(MonthlyRent o) {
        return this.name.compareTo(o.name);
    }
}
