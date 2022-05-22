package kr.carz.savecar.domain;

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
    @Column(name = "longTermRentId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long longTermRentId;

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

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Builder
    public LongTermRent(Long longTermRentId,String carName,String carNum, String carColor, String carYearModel,String contractPeriod, String contractKm, String contractPrice
                        ,String contractDeposit, String contractMaintenance, String newOld, String fuel, String description) {
        this.longTermRentId = longTermRentId;
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
        this.fuel = fuel;
        this.description = description;
    }
}
