package kr.carz.savecar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "CampingcarDateTime2")
public class CampingcarDateTime2 extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dateTimeId;


    @Column(nullable = true)
    private String carType;

    @Column(nullable = true)
    private String rentDate;

    @Column(nullable = true)
    private String rentTime;

    @Column(nullable = true)
    private String returnDate;

    @Column(nullable = true)
    private String returnTime;


    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private String phone;

    @Column(columnDefinition = "LONGTEXT", nullable = true)
    private String detail;

    @Column(nullable = true)
    private String depositor;


    @Column(nullable = true)
    private Integer total;

    @Column(nullable = true)
    private Integer deposit;

    @Column(nullable = true)
    private String agree;

    @Column(nullable = true)
    private String reservation;

    @Column(nullable = true)
    private String day;

    @Column(nullable = true)
    private String extraTime;

    @Column(nullable = true)
    private String totalHalf;



    @Builder
    public CampingcarDateTime2(String rentDate, String rentTime, String returnDate, String returnTime, String name, String phone, String depositor, String detail, Integer total, Integer deposit, String agree, String reservation, String day, String extraTime, String totalHalf,String carType) {
        this.rentDate = rentDate;
        this.rentTime = rentTime;
        this.returnDate = returnDate;
        this.returnTime = returnTime;
        this.name = name;
        this.phone = phone;
        this.depositor = depositor;
        this.detail = detail;
        this.total = total;
        this.deposit = deposit;
        this.agree = agree;
        this.reservation = reservation;
        this.day = day;
        this.extraTime = extraTime;
        this.totalHalf = totalHalf;
        this.carType = carType;
    }
}
