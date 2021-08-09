package kr.carz.savecar.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampingcarDateTimeDto {

    private String rentDate;
    private String rentTime;
    private String returnDate;
    private String returnTime;
    private String name;
    private String phone;
    private String depositor;
    private String detail;
    private Integer total;
    private Integer deposit;
    private String agree;
    private String reservation;

    public CampingcarDateTimeDto(String rentDate, String rentTime, String returnDate, String returnTime, String name, String phone, String depositor, String detail, Integer total,Integer deposit,String agree,String reservation) {
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

    public CampingcarDateTime toEntity() {
        return CampingcarDateTime.builder()
                .rentDate(rentDate)
                .rentTime(rentTime)
                .returnDate(returnDate)
                .returnTime(returnTime)
                .name(name)
                .phone(phone)
                .depositor(depositor)
                .detail(detail)
                .total(total)
                .deposit(deposit)
                .agree(agree)
                .reservation(reservation)
                .build();

    }
}
