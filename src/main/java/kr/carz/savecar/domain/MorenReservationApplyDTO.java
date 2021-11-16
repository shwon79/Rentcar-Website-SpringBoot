package kr.carz.savecar.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MorenReservationApplyDTO {

    private Long id;
    private String carNo;      // 차량번호
    private String kilometer;  // 약정주행거리
    private String reservationName;   // 예약자 이름(입금자명)
    private String reservationPhone;  // 예약자 전화번호
    private String reservationDate;   // 예약 날짜
    private String reservationTime;   // 예약 시간
    private String address;                // 배차요청 주소
    private String addressDetail;          // 배차요청 상세주소
    private String rentTerm;   // 렌트 기간
    private String carAmountTotal;         // 총렌트료
    private String carDeposit;             // 보증금
    private String reservationDetails;     // 요청사항
    private String costPerKm;              // 초과금액

    public MorenReservationApplyDTO(String carNo, String kilometer, String reservationName, String reservationPhone,
                                    String reservationDate, String reservationTime,
                                    String address, String addressDetail, Long id, String rentTerm,
                                    String carAmountTotal, String carDeposit, String reservationDetails,
                                    String costPerKm) {
        this.id = id;
        this.carNo = carNo;
        this.kilometer = kilometer;
        this.reservationName = reservationName;
        this.reservationPhone = reservationPhone;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.address = address;
        this.addressDetail = addressDetail;
        this.rentTerm = rentTerm;
        this.carAmountTotal = carAmountTotal;
        this.carDeposit = carDeposit;
        this.reservationDetails = reservationDetails;
        this.costPerKm = costPerKm;
    }

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
                .build();

    }
}
