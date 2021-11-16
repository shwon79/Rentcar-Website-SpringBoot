package kr.carz.savecar.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class MorenReservationDTO {

    private String carNo;      // 차량번호
    private String kilometer;  // 약정주행거리
    private String reservationName;   // 예약자 이름(입금자명)
    private String reservationPhone;  // 예약자 전화번호
    private String reservationAge;    // 예약자 생년월일
    private String reservationDate;   // 예약 날짜
    private String reservationTime;   // 예약 시간
    private String reservationGuarantee;   // 신용카드보유 or 소득증빙
    private String reservationDetails;     // 요청사항
    private String address;                // 배차요청 주소
    private String addressDetail;          // 배차요청 상세주소
    private String carPrice;               // 렌트료
    private String carTax;                 // 부가세
    private String carAmountTotal;         // 총렌트료
    private String carDeposit;             // 보증금
    private String reservationStatus;
    private String rentTerm;
    private String costPerKm;

    public MorenReservationDTO(String carNo, String kilometer, String reservationName, String reservationPhone, String reservationAge,
                               String reservationDate, String reservationTime, String reservationGuarantee, String reservationDetails,
                               String address, String addressDetail, String carPrice, String carTax, String carAmountTotal, String carDeposit,
                               String reservationStatus, String rentTerm, String costPerKm) {
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
        this.rentTerm = rentTerm;
        this.costPerKm = costPerKm;
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
                .reservationStatus(reservationStatus)
                .rentTerm(rentTerm)
                .costPerKm(costPerKm)
                .build();

    }
}
