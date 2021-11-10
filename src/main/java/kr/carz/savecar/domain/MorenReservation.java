package kr.carz.savecar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "MorenReservation")
public class MorenReservation extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false)
    private String carNo;

    @Column(nullable = true)
    private String kilometer;

    @Column(nullable = false)
    private String reservationName;

    @Column(nullable = false)
    private String reservationPhone;

    @Column(nullable = true)
    private String reservationAge;

    @Column(nullable = false)
    private String reservationDate;

    @Column(nullable = false)
    private String reservationTime;

    @Column(nullable = true)
    private String reservationGuarantee;

    @Column(columnDefinition = "LONGTEXT", nullable = true)
    private String reservationDetails;

    @Column(nullable = true)
    private String address;

    @Column(nullable = true)
    private String addressDetail;

    @Column(nullable = true)
    private String carPrice;

    @Column(nullable = true)
    private String carTax;

    @Column(nullable = true)
    private String carAmountTotal;

    @Column(nullable = true)
    private String carDeposit;

    @Column(nullable = true)
    private String reservationStatus;


    @Builder
    public MorenReservation(String carNo, String kilometer, String reservationName, String reservationPhone, String reservationAge,
                            String reservationDate, String reservationTime, String reservationGuarantee,
                            String reservationDetails, String address, String addressDetail, String carPrice, String carTax,
                            String carAmountTotal, String carDeposit, String reservationStatus) {
        this.carNo = carNo;
        this.kilometer = kilometer;
        this.reservationName = reservationName;
        this.reservationPhone = reservationPhone;
        this.reservationAge = reservationAge;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.reservationGuarantee = reservationGuarantee;
        this.reservationDetails = reservationDetails;
        this.address = address;
        this.addressDetail = addressDetail;
        this.carPrice = carPrice;
        this.carTax = carTax;
        this.carAmountTotal = carAmountTotal;
        this.carDeposit = carDeposit;
        this.reservationStatus = reservationStatus;
    }
}
