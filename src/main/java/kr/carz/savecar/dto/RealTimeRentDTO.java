package kr.carz.savecar.dto;

import kr.carz.savecar.domain.RealTimeRent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RealTimeRentDTO {

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

    public RealTimeRent toEntity() {

        return RealTimeRent.builder()
                .carIdx(carIdx)
                .carCategory(carCategory)
                .carName(carName)
                .carDetail(carDetail)
                .carNo(carNo)
                .carExteriorColor(carExteriorColor)
                .carGubun(carGubun)
                .carDisplacement(carDisplacement)
                .carMileaget(carMileaget)
                .carColor(carColor)
                .carOld(carOld)
                .carEngine(carEngine)
                .carAttribute01(carAttribute01)
                .orderEnd(orderEnd)
                .rentIdx(rentIdx)
                .costPerKm(costPerKm)
                .credit(credit)
                .carCode(carCode)
                .kilometer(kilometer)
                .deposit(deposit)
                .rentTerm(rentTerm)
                .selectAge(selectAge)
                .build();

    }
}
