package kr.carz.savecar.dto;

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
public class RealTimeRentCarDTO {

    private MonthlyRent monthlyRent;
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
    private String costPerKm;
    private String carCode;
    private double discount; // 할인 퍼센트
    private String description;
    private int isExpected;
    private int ready_to_return;

    public RealTimeRentCar toEntity() {

        return RealTimeRentCar.builder()
                .monthlyRent(monthlyRent)
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
                .costPerKm(costPerKm)
                .carCode(carCode)
                .discount(discount)
                .description(description)
                .isExpected(isExpected)
                .ready_to_return(ready_to_return)
                .build();

    }
}
