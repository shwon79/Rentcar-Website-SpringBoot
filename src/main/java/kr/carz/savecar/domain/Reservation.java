package kr.carz.savecar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Reservation")
public class Reservation extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNo;

    @Column(columnDefinition = "LONGTEXT", nullable = true)
    private String detail;

    @Column(nullable = true)
    private String title;

    @Column(nullable = true)
    private String product;

    @Column(nullable = true)
    private String category1; // 국산, 외제차의 분류

    @Column(nullable = true)
    private String category2; // 중형차, 경차 등 큰 분류

    @Column(nullable = true)
    private String car_name;

    @Column(nullable = true)
    private String mileage;

    @Column(nullable = true)
    private String deposit;

    @Column(nullable = true)
    private String option;

    @Column(nullable = true)
    private String price;

    @Column(nullable = true)
    private String age_limit;

    @Column(nullable = true)
    private String car_num;

    @Column(nullable = true)
    private String region;

    @Column(nullable = true)
    private String resDate;

    @Builder
    public Reservation(String name, String phoneNo, String detail, String title, String product,
                       String category1, String category2, String car_name, String mileage,
                       String deposit, String option, String price, String age_limit, String car_num,
                       String region, String resDate) {
      
        this.name = name;
        this.phoneNo = phoneNo;
        this.detail = detail;
        this.title = title;
        this.product = product;
        this.category1 = category1;
        this.category2 = category2;
        this.car_name = car_name;
        this.mileage = mileage;
        this.deposit = deposit;
        this.option = option;
        this.price = price;
        this.age_limit = age_limit;
        this.car_num = car_num;
        this.region = region;
        this.resDate = resDate;
    }
}