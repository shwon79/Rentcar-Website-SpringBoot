package kr.carz.savecar.dto;

import kr.carz.savecar.domain.LongTermRent;
import kr.carz.savecar.domain.MonthlyRent;
import kr.carz.savecar.domain.RealTimeRentCar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LongTermRentDTO {

    private String carName;
    private String carNum;
    private String carColor;
    private String carYearModel;
    private String contractPeriod;
    private String contractKm;
    private String contractPrice;
    private String contractDeposit;
    private String contractMaintenance;
    private String newOld;
    private String fuel;

    public LongTermRent toEntity() {

        return LongTermRent.builder()
                .carName(carName)
                .carNum(carNum)
                .carColor(carColor)
                .carYearModel(carYearModel)
                .contractPeriod(contractPeriod)
                .contractKm(contractKm)
                .contractPrice(contractPrice)
                .contractDeposit(contractDeposit)
                .contractMaintenance(contractMaintenance)
                .newOld(newOld)
                .fuel(fuel)
                .build();

    }
}
