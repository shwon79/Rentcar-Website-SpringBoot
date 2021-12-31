package kr.carz.savecar.dto;

import kr.carz.savecar.domain.CampingCarReservation;
import kr.carz.savecar.domain.MorenReservation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
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

    public CampingCarReservationDTO(String rentDate, String rentTime, String returnDate, String returnTime, String name, String phone, String depositor, String detail, Integer total, Integer deposit, Integer agree, Integer reservation, String day, Integer totalHalf, String carType, Integer extraTime, String orderCode) {
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
