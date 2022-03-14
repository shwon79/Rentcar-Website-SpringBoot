package kr.carz.savecar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "RealTimeRent")
public class RealTimeRent {

    // 이미지 리스트
    @Id
    @Column(name = "RealTimeRentId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long realTimeRentId;

    private String carIdx;
    private String carCategory;
    private String carName;
    private String carDetail;
    private String carNo;
    private String carExteriorColor;
    private String carGubun;
    private String carDisplacement;
    private String carMileaget;
    private String carColor;
    private String carOld;
    private String carEngine;
    private String carAttribute01;
    private String orderEnd;
    private Long rentIdx;
    private String costPerKm;
    private String credit;
    private String carCode;
    private String kilometer;
    private String deposit;
    private String rentTerm;
    private String selectAge;


    @Builder
    public RealTimeRent(Long realTimeRentId, String carIdx, String carCategory, String carName, String carDetail, String carNo, String carExteriorColor, String carGubun
                        , String carDisplacement, String carMileaget, String carColor, String carOld, String carEngine, String carAttribute01, String orderEnd
                        , Long rentIdx, String costPerKm, String credit, String carCode, String kilometer, String deposit,  String rentTerm, String selectAge) {
        this.realTimeRentId = realTimeRentId;
        this.carIdx = carIdx;
        this.carCategory = carCategory;
        this.carName = carName;
        this.carDetail = carDetail;
        this.carNo = carNo;
        this.carExteriorColor = carExteriorColor;
        this.carGubun = carGubun;
        this.carDisplacement = carDisplacement;
        this.carMileaget = carMileaget;
        this.carColor = carColor;
        this.carOld = carOld;
        this.carEngine = carEngine;
        this.carAttribute01 = carAttribute01;
        this.orderEnd = orderEnd;
        this.rentIdx = rentIdx;
        this.costPerKm = costPerKm;
        this.credit = credit;
        this.carCode = carCode;
        this.kilometer = kilometer;
        this.deposit = deposit;
        this.rentTerm = rentTerm;
        this.selectAge = selectAge;
    }
}
