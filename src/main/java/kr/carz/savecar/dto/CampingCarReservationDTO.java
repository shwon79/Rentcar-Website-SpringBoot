package kr.carz.savecar.dto;

import kr.carz.savecar.domain.CampingCarReservation;
import kr.carz.savecar.domain.MorenReservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
public class CampingCarReservationDTO {

    private Integer agree;
    private String carType;
    private String day;
    private Integer deposit;
    private String depositor;
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

    public CampingCarReservation toEntity() {
        return CampingCarReservation.builder()
                .agree(agree)
                .carType(carType)
                .day(day)
                .deposit(deposit)
                .depositor(depositor)
                .detail(detail)
                .name(name)
                .phone(phone)
                .rentDate(rentDate)
                .rentTime(rentTime)
                .reservation(reservation)
                .returnDate(returnDate)
                .returnTime(returnTime)
                .total(total)
                .totalHalf(totalHalf)
                .extraTime(extraTime)
                .orderCode(orderCode)
                .build();

    }
}
