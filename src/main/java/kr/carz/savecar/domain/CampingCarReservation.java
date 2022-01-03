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

    private Integer agree;
    private String carType;
    private String day;
    private Integer deposit;
    private String depositor;

    @Column(columnDefinition = "LONGTEXT")
    private String detail;
    private String name;
    private String phone;
    private String rentDate;
    private String rentTime;
    private Integer reservation;
    private String returnDate;
    private String returnTime;
    private Integer total;
    private Integer totalHalf;
    private Integer extraTime;
    private String orderCode;

    @Builder
    public CampingCarReservation(String rentDate, String rentTime, String returnDate, String returnTime, String name, String phone, String depositor, String detail, Integer total, Integer deposit, Integer agree, Integer reservation, String day, Integer totalHalf, String carType, Integer extraTime, String orderCode) {
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
        this.extraTime = extraTime;
        this.orderCode = orderCode;
    }
}
