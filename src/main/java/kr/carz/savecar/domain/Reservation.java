package kr.carz.savecar.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;

@Entity
@Data
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


    @Builder
    public Reservation(String name, String phoneNo, String detail, String product,
                       String category1, String category2, String car_name, String mileage,
                       String deposit, String option) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.detail = detail;
        this.product = product;
        this.category1 = category1;
        this.category2 = category2;
        this.car_name = car_name;
        this.mileage = mileage;
        this.deposit = deposit;
        this.option = option;
    }
}
