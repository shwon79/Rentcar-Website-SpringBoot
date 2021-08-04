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

    public CampingcarDateTimeDto(String rentDate, String rentTime, String returnDate, String returnTime) {
        this.rentDate = rentDate;
        this.rentTime = rentTime;
        this.returnDate = returnDate;
        this.returnTime = returnTime;
    }

    public CampingcarDateTime toEntity() {
        return CampingcarDateTime.builder()
                .rentDate(rentDate)
                .rentTime(rentTime)
                .returnDate(returnDate)
                .returnTime(returnTime)
                .build();

    }
}
