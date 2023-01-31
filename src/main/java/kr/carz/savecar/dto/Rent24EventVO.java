package kr.carz.savecar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rent24EventVO {

    private String imgUrl;
    private String carTitle;
    private String carPrice;
    private String period;
    private String deposit;
    private String rentPrice;

}
