package kr.carz.savecar.dto;

import kr.carz.savecar.domain.CampingCarPrice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CampingCarPriceDTO {

    private String carName;
    private String carNum;
    private String carCode;
    private String season;
    private String onedays;
    private String twodays;
    private String threedays;
    private String fourdays;
    private String fivedays;
    private String sixdays;
    private String sevendays;
    private String eightdays;
    private String ninedays;
    private String tendays;
    private String elevendays;
    private String twelvedays;
    private String thirteendays;
    private String fourteendays;
    private String fifteendays;
    private String sixteendays;
    private String seventeendays;
    private String eighteendays;
    private String ninetinedays;
    private String twentydays;
    private String twentyonedays;
    private String twentytwodays;
    private String twentythreedays;
    private String twentyfourdays;
    private String twentyfivedays;
    private String twentysixdays;
    private String twentysevendays;
    private String twentyeightdays;
    private String twentyninedays;
    private String thirtydays;
    private String deposit;
    private String yearmodel;


    public CampingCarPrice toEntity() {
        return CampingCarPrice.builder()
                .carName(carName)
                .carNum(carNum)
                .carCode(carCode)
                .season(season)
                .onedays(onedays)
                .twodays(twodays)
                .threedays(threedays)
                .fourdays(fourdays)
                .fivedays(fivedays)
                .sixdays(sixdays)
                .sevendays(sevendays)
                .eightdays(eightdays)
                .ninedays(ninedays)
                .tendays(tendays)
                .elevendays(elevendays)
                .twelvedays(twelvedays)
                .thirteendays(thirteendays)
                .fourteendays(fourteendays)
                .fifteendays(fifteendays)
                .sixteendays(sixteendays)
                .seventeendays(seventeendays)
                .eighteendays(eighteendays)
                .ninetinedays(ninetinedays)
                .twentydays(twentydays)
                .twentyonedays(twentyonedays)
                .twentytwodays(twentytwodays)
                .twentythreedays(twentythreedays)
                .twentyfourdays(twentyfourdays)
                .twentyfivedays(twentyfivedays)
                .twentysixdays(twentysixdays)
                .twentysevendays(twentysevendays)
                .twentyeightdays(twentyeightdays)
                .twentyninedays(twentyninedays)
                .thirtydays(thirtydays)
                .deposit(deposit)
                .yearmodel(yearmodel)
                .build();

    }
}
