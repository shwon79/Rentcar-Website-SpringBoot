package kr.carz.savecar.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Rent24")
public class Rent24 {
    @Id
    private Long rent24Id;

    private String carTitle;
    private String period;
    private String deposit;
    private String rentPrice;
    private String imgUrl;

    public Rent24(Long rent24Id, String carTitle, String period, String deposit, String rentPrice, String imgUrl) {
        this.rent24Id = rent24Id;
        this.carTitle = carTitle;
        this.period = period;
        this.deposit = deposit;
        this.rentPrice = rentPrice;
        this.imgUrl = imgUrl;
    }
}
