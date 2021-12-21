package kr.carz.savecar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "CampingCarReservation")
public class CampingCarReservation extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private Integer agree;

    @Column(nullable = true)
    private String carType;

    @Column(nullable = true)
    private String day;

    @Column(nullable = true)
    private Integer deposit;

    @Column(nullable = true)
    private String depositor;

    @Column(columnDefinition = "LONGTEXT", nullable = true)
    private String detail;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private String phone;

    @Column(nullable = true)
    private String rentDate;

    @Column(nullable = true)
    private String rentTime;

    @Column(nullable = true)
    private Integer reservation;

    @Column(nullable = true)
    private String returnDate;

    @Column(nullable = true)
    private String returnTime;

    @Column(nullable = true)
    private Integer total;

    @Column(nullable = true)
    private Integer totalHalf;



    @Builder
    public CampingCarReservation(String rentDate, String rentTime, String returnDate, String returnTime, String name, String phone, String depositor, String detail, Integer total, Integer deposit, Integer agree, Integer reservation, String day, Integer totalHalf, String carType) {
        this.agree = agree;
        this.carType = carType;
        this.day = day;
        this.deposit = deposit;
        this.depositor = depositor;
        this.detail = detail;
        this.name = name;
        this.phone = phone;
        this.rentDate = rentDate;
        this.rentTime = rentTime;
        this.reservation = reservation;
        this.returnDate = returnDate;
        this.returnTime = returnTime;
        this.total = total;
        this.totalHalf = totalHalf;
    }
}
