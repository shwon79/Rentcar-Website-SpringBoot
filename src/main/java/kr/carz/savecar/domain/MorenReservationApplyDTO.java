package kr.carz.savecar.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MorenReservationApplyDTO {

    private Long id;
    private String carNo;      // 차량번호
    private String reservationName;   // 예약자 이름(입금자명)
    private String reservationPhone;  // 예약자 전화번호
    private String reservationDate;   // 예약 날짜
    private String reservationTime;   // 예약 시간
    private String address;                // 배차요청 주소
    private String addressDetail;          // 배차요청 상세주소

    public MorenReservationApplyDTO(String carNo, String reservationName, String reservationPhone,
                                    String reservationDate, String reservationTime, String address, String addressDetail,
                                    Long id) {
        this.id = id;
        this.carNo = carNo;
        this.reservationName = reservationName;
        this.reservationPhone = reservationPhone;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.address = address;
        this.addressDetail = addressDetail;
    }

    public MorenReservation toEntity() {
        return MorenReservation.builder()
                .id(id)
                .carNo(carNo)
                .reservationName(reservationName)
                .reservationPhone(reservationPhone)
                .reservationDate(reservationDate)
                .reservationTime(reservationTime)
                .address(address)
                .addressDetail(addressDetail)
                .build();

    }
}
