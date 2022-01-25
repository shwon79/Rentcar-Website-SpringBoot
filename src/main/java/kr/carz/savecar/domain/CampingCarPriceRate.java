package kr.carz.savecar.domain;


import kr.carz.savecar.dto.CampingCarPriceRateDTO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(exclude = {"carName"})
@NoArgsConstructor
@Table(name = "CampingCarPriceRate")
public class CampingCarPriceRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @ManyToOne
    @JoinColumn(name = "car_name")
    private CampingCarPrice carName; // 캠핑카 id

    private String season;   // 0 비성수기  1 성수기
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


    @Builder
    public CampingCarPriceRate(CampingCarPrice carName, String season, String deposit, Float onedays, Float twodays, Float threedays, Float fourdays, Float fivedays, Float sixdays, Float sevendays, Float eightdays, Float ninedays, Float tendays, Float elevendays, Float twelvedays, Float thirteendays, Float fourteendays
            , Float fifteendays, Float sixteendays, Float seventeendays, Float eighteendays, Float ninetinedays, Float twentydays, Float twentyonedays
            , Float twentytwodays, Float twentythreedays, Float twentyfourdays, Float twentyfivedays, Float twentysixdays, Float twentysevendays
            , Float twentyeightdays, Float twentyninedays, Float thirtydays) {
        this.carName = carName;
        this.season = season;
        this.deposit = deposit;
        this.onedays = onedays;
        this.twodays = twodays;
        this.threedays = threedays;
        this.fourdays = fourdays;
        this.fivedays = fivedays;
        this.sixdays = sixdays;
        this.sevendays = sevendays;
        this.eightdays = eightdays;
        this.ninedays = ninedays;
        this.tendays = tendays;
        this.elevendays = elevendays;
        this.twelvedays = twelvedays;
        this.thirteendays = thirteendays;
        this.fourteendays = fourteendays;
        this.fifteendays = fifteendays;
        this.sixteendays = sixteendays;
        this.seventeendays = seventeendays;
        this.eighteendays = eighteendays;
        this.ninetinedays = ninetinedays;
        this.twentydays = twentydays;
        this.twentyonedays = twentyonedays;
        this.twentytwodays = twentytwodays;
        this.twentythreedays = twentythreedays;
        this.twentyfourdays = twentyfourdays;
        this.twentyfivedays = twentyfivedays;
        this.twentysixdays = twentysixdays;
        this.twentysevendays = twentysevendays;
        this.twentyeightdays = twentyeightdays;
        this.twentyninedays = twentyninedays;
        this.thirtydays = thirtydays;
    }

    public void setValueByDto(CampingCarPriceRateDTO dto){
        this.deposit = dto.getDeposit();
        this.onedays = dto.getOnedays();
        this.twodays = dto.getTwodays();
        this.threedays = dto.getThreedays();
        this.fourdays = dto.getFourdays();
        this.fivedays = dto.getFivedays();
        this.sixdays = dto.getSixdays();
        this.sevendays = dto.getSevendays();
        this.eightdays = dto.getEightdays();
        this.ninedays = dto.getNinedays();
        this.tendays = dto.getTendays();
        this.elevendays = dto.getElevendays();
        this.twelvedays = dto.getTwelvedays();
        this.thirteendays = dto.getThirteendays();
        this.fourteendays = dto.getFourteendays();
        this.fifteendays = dto.getFifteendays();
        this.sixteendays = dto.getSixteendays();
        this.seventeendays = dto.getSeventeendays();
        this.eighteendays = dto.getEighteendays();
        this.ninetinedays = dto.getNinetinedays();
        this.twentydays = dto.getTwentydays();
        this.twentyonedays = dto.getTwentyonedays();
        this.twentytwodays = dto.getTwentytwodays();
        this.twentythreedays = dto.getTwentythreedays();
        this.twentyfourdays = dto.getTwentyfourdays();
        this.twentyfivedays = dto.getTwentyfivedays();
        this.twentysixdays = dto.getTwentysixdays();
        this.twentysevendays = dto.getTwentysevendays();
        this.twentyeightdays = dto.getTwentyeightdays();
        this.twentyninedays = dto.getTwentyninedays();
        this.thirtydays = dto.getThirtydays();
    }

}
