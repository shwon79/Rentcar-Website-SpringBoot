package kr.carz.savecar.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "LongTermRent")
public class LongTermRent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

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


    @Builder
    public LongTermRent(Long imageId,String carName,String carNum, String carColor, String carYearModel,String contractPeriod, String contractKm, String contractPrice
                        ,String contractDeposit, String contractMaintenance, String newOld) {
        this.imageId = imageId;
        this.carName = carName;
        this.carNum = carNum;
        this.carColor = carColor;
        this.carYearModel = carYearModel;
        this.contractPeriod = contractPeriod;
        this.contractKm = contractKm;
        this.contractPrice = contractPrice;
        this.contractDeposit = contractDeposit;
        this.contractMaintenance = contractMaintenance;
        this.newOld = newOld;
    }
}
