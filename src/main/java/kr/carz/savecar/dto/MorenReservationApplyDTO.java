package kr.carz.savecar.dto;

import kr.carz.savecar.domain.MorenReservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MorenReservationApplyDTO {

    private Long id;
    private String carNo;      // 차량번호
    private String kilometer;  // 약정주행거리
    private String reservationName;   // 예약자 이름(입금자명)
    private String reservationPhone;  // 예약자 전화번호
    private String reservationAge;    // 예약자 생년월일
    private String reservationDate;   // 예약 날짜
    private String reservationTime;   // 예약 시간
    private String address;                // 배차요청 주소
    private String addressDetail;          // 배차요청 상세주소
    private String rentTerm;   // 렌트 기간
    private String carAmountTotal;         // 총렌트료
    private String carDeposit;             // 보증금
    private String reservationDetails;     // 요청사항
    private String costPerKm;              // 초과금액
    private String carCode;
    private String pickupPlace;
    private String carName;

    public MorenReservation toEntity() {
        return MorenReservation.builder()
                .id(id)
                .carNo(carNo)
                .kilometer(kilometer)
                .reservationName(reservationName)
                .reservationPhone(reservationPhone)
                .reservationDate(reservationDate)
                .reservationTime(reservationTime)
                .address(address)
                .addressDetail(addressDetail)
                .rentTerm(rentTerm)
                .carAmountTotal(carAmountTotal)
                .carDeposit(carDeposit)
                .reservationDetails(reservationDetails)
                .costPerKm(costPerKm)
                .carCode(carCode)
                .pickupPlace(pickupPlace)
                .carName(carName)
                .build();

    }
}
