package kr.carz.savecar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "MonthlyRent")
public class MonthlyRent implements Comparable<MonthlyRent>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @OneToOne
    @JoinColumn(name = "yearlyRent")
    private YearlyRent yearlyRent; // 12개월 외래키

    @OneToOne
    @JoinColumn(name = "twoYearlyRent")
    private TwoYearlyRent twoYearlyRent; // 12개월 외래키

    private String category1; // 국산, 외제차의 분류
    private String category2; // 중형차, 경차 등 큰 분류

    @Column(unique = true)
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

    @Builder
    public MonthlyRent(YearlyRent yearlyRent, TwoYearlyRent twoYearlyRent, String category1, String category2, String name, String deposit, Float cost_for_2k
            , Float cost_for_2_5k, Float cost_for_3k, Float cost_for_4k, String cost_for_others, String age_limit, String cost_per_km, String nameMoren
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
        int thisPriority, oPriority;

        if(this.name.equals("경형")){
            thisPriority = 0;
        }else if(this.name.equals("준중형")){
            thisPriority = 1;
        }else if(this.name.equals("중형")){
            thisPriority = 2;
        }else if(this.name.equals("중대형")){
            thisPriority = 3;
        }else if(this.name.equals("대형")){
            thisPriority = 4;
        }else if(this.name.equals("소중형SUV")){
            thisPriority = 5;
        }else if(this.name.equals("중형SUV")){
            thisPriority = 6;
        }else if(this.name.equals("중대형SUV")){
            thisPriority = 7;
        }else if(this.name.equals("대형SUV")){
            thisPriority = 8;
        }else if(this.name.equals("승합")){
            thisPriority = 9;
        }else {
            thisPriority = 10;
        }

        if(o.name.equals("경형")){
            oPriority = 0;
        }else if(o.name.equals("준중형")){
            oPriority = 1;
        }else if(o.name.equals("중형")){
            oPriority = 2;
        }else if(o.name.equals("중대형")){
            oPriority = 3;
        }else if(o.name.equals("대형")){
            oPriority = 4;
        }else if(o.name.equals("소중형SUV")){
            oPriority = 5;
        }else if(o.name.equals("중형SUV")){
            oPriority = 6;
        }else if(o.name.equals("중대형SUV")){
            oPriority = 7;
        }else if(o.name.equals("대형SUV")){
            oPriority = 8;
        }else if(o.name.equals("승합")){
            oPriority = 9;
        }else {
            oPriority = 10;
        }

//        if(thisPriority == oPriority){
//            return this.name.compareTo(o.name);
//        }


        return Integer.compare(thisPriority, oPriority);
    }
}
