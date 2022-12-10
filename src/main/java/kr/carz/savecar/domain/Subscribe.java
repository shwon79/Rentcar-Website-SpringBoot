package kr.carz.savecar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Subscribe")
public class Subscribe implements Comparable<Subscribe> {
    @Id
    @Column(name = "subscribeId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscribeId;

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
    private int sequence;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Builder
    public Subscribe(Long subscribeId, String carName, String carNum, String carColor, String carYearModel, String contractPeriod, String contractKm, String contractPrice
                        , String contractDeposit, String contractMaintenance, String newOld, String fuel, String description, int sequence) {
        this.subscribeId = subscribeId;
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
        this.sequence = sequence;
    }

    @Override
    public int compareTo(Subscribe o) {
        return Integer.compare(this.sequence, o.sequence);
    }
}
