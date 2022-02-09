package kr.carz.savecar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MorenDTO {

    private String carIdx;
    private String carCategory;
    private String carName;
    private String carNo;
    private String carExteriorColor;
    private String carGubun;
    private String carDisplacement;
    private String carMileaget;
    private String carColor;
    private String carOld;
    private String carEngine;
    private String carAttribute01;
    private String carPrice;
    private String orderEnd;
    private Long rentIdx;
    private List<String> carImageList;
    private String discount;
    private String discountDescription;
    private String costPerKm;
    private String credit;
    private String carCode;
    private String kilometer;
    private String deposit;
    private String rentTerm;
    private String selectAge;


}
