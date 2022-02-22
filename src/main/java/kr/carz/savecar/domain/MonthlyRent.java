package kr.carz.savecar.domain;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
@Data
@Table(name = "MonthlyRent")
public class MonthlyRent implements Comparable<MonthlyRent>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String category1; // 국산, 외제차의 분류
    private String category2; // 중형차, 경차 등 큰 분류

    @Column(unique = true)
    private String name;

    private String deposit;
    private String cost_for_2k;
    private String cost_for_2_5k;
    private String cost_for_3k;
    private String cost_for_4k;
    private String cost_for_others;
    private String age_limit;
    private String cost_per_km;

    private String nameMoren;
    private Long start;
    private Long end;

    private String credit;
    private String img_url;


    @Override
    public int compareTo(@NotNull MonthlyRent o) {
        return this.name.compareTo(o.name);
    }
}
