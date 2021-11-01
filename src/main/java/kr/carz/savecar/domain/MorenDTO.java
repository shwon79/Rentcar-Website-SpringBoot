package kr.carz.savecar.domain;

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
    private Long monthlyrentIdx;
    private List<String> carImageList;

    public MorenDTO(String carIdx, String carCategory, String carName,
                    String carNo, String carExteriorColor, String carGubun,
                    String carDisplacement, String carMileaget, String carColor,
                    String carOld, String carEngine, String carAttribute01,
                    String carPrice, String orderEnd, Long monthlyrentIdx,
                    List<String> carImageList) {
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
        this.monthlyrentIdx = monthlyrentIdx;
        this.carImageList = carImageList;
    }

}
