package kr.carz.savecar.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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


    public MorenDTO(String carIdx, String carCategory, String carName,
                    String carNo, String carExteriorColor, String carGubun,
                    String carDisplacement, String carMileaget, String carColor,
                    String carOld, String carEngine, String carAttribute01,
                    String carPrice, String orderEnd, Long rentIdx,
                    List<String> carImageList, String discount, String discountDescription,
                    String costPerKm, String credit, String carCode,
                    String kilometer, String deposit, String rentTerm, String selectAge) {
        this.carIdx = carIdx;
        this.carCategory = carCategory;
        this.carName = carName;
        this.carNo = carNo;
        this.carExteriorColor = carExteriorColor;
        this.carGubun = carGubun;
        this.carDisplacement = carDisplacement;
        this.carMileaget = carMileaget;
        this.carColor = carColor;
        this.carOld = carOld;
        this.carEngine = carEngine;
        this.carAttribute01 = carAttribute01;
        this.carPrice = carPrice;
        this.orderEnd = orderEnd;
        this.rentIdx = rentIdx;
        this.carImageList = carImageList;
        this.discount = discount;
        this.discountDescription = discountDescription;
        this.costPerKm = costPerKm;
        this.credit = credit;
        this.carCode = carCode;
        this.kilometer = kilometer;
        this.deposit = deposit;
        this.rentTerm = rentTerm;
        this.selectAge = selectAge;
    }

}
