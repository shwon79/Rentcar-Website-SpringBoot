package kr.carz.savecar.dto;

import kr.carz.savecar.domain.CampingCarPrice;
import kr.carz.savecar.domain.CampingCarPriceRate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CampingCarPriceRateDTO {

    private String season;
    private String deposit;
    private Float onedays;
    private Float twodays;
    private Float threedays;
    private Float fourdays;
    private Float fivedays;
    private Float sixdays;
    private Float sevendays;
    private Float eightdays;
    private Float ninedays;
    private Float tendays;
    private Float elevendays;
    private Float twelvedays;
    private Float thirteendays;
    private Float fourteendays;
    private Float fifteendays;
    private Float sixteendays;
    private Float seventeendays;
    private Float eighteendays;
    private Float ninetinedays;
    private Float twentydays;
    private Float twentyonedays;
    private Float twentytwodays;
    private Float twentythreedays;
    private Float twentyfourdays;
    private Float twentyfivedays;
    private Float twentysixdays;
    private Float twentysevendays;
    private Float twentyeightdays;
    private Float twentyninedays;
    private Float thirtydays;


    public CampingCarPriceRate toEntity(CampingCarPrice carName) {
        return CampingCarPriceRate.builder()
                .carName(carName)
                .season(season)
                .onedays(onedays)
                .deposit(deposit)
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
                .build();

    }
}
