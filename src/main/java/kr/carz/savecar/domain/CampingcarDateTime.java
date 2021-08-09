package kr.carz.savecar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "CampingcarDateTime")
public class CampingcarDateTime extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false)
    private String rentDate;

    @Column(nullable = false)
    private String rentTime;

    @Column(nullable = false)
    private String returnDate;

    @Column(nullable = false)
    private String returnTime;


    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(columnDefinition = "LONGTEXT", nullable = true)
    private String detail;

    @Column(nullable = false)
    private String depositor;


    @Column(nullable = false)
    private Integer total;

    @Column(nullable = false)
    private Integer deposit;

    @Column(nullable = false)
    private String agree;

    @Column(nullable = false)
    private String reservation;




    @Builder
    public CampingcarDateTime(String rentDate, String rentTime, String returnDate, String returnTime, String name, String phone, String depositor, String detail, Integer total,Integer deposit,String agree,String reservation) {
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
    }
}
