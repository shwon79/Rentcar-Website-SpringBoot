package kr.carz.savecar.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class MorenReservationDTO {

    private String carNo;
    private String kilometer;
    private String reservationName;
    private String reservationPhone;
    private String reservationAge;
    private String reservationDate;
    private String reservationTime;
    private String reservationGuarantee;
    private String reservationDetails;
    private String address;
    private String addressDetail;
    private String carPrice;
    private String carTax;
    private String carAmountTotal;
    private String carDeposit;

    public MorenReservationDTO(String carNo, String kilometer, String reservationName, String reservationPhone, String reservationAge,
                               String reservationDate, String reservationTime, String reservationGuarantee, String reservationDetails,
                               String address, String addressDetail, String carPrice, String carTax, String carAmountTotal, String carDeposit) {
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
    }

    public MorenReservation toEntity() {
        return MorenReservation.builder()
                .carNo(carNo)
                .kilometer(kilometer)
                .reservationName(reservationName)
                .reservationPhone(reservationPhone)
                .reservationAge(reservationAge)
                .reservationDate(reservationDate)
                .reservationTime(reservationTime)
                .reservationGuarantee(reservationGuarantee)
                .reservationDetails(reservationDetails)
                .address(address)
                .addressDetail(addressDetail)
                .carPrice(carPrice)
                .carTax(carTax)
                .carAmountTotal(carAmountTotal)
                .carDeposit(carDeposit)
                .build();

    }
}
