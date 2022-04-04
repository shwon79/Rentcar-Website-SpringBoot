package kr.carz.savecar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "RealTimeRentCar")
public class RealTimeRentCar extends BaseTimeEntity {

    @Id
    @Column(name = "RealTimeRentId")
    private Long realTimeRentId;

    @ManyToOne
    @JoinColumn(name = "monthlyRent")
    private MonthlyRent monthlyRent; // 가격 외래키

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
    private int priceDisplay;
    private int isExpected;


//    @OneToMany(mappedBy = "realTimeRent", targetEntity=RealTimeRentImage.class)
//    private List<RealTimeRentImage> realTimeRentImageList = new ArrayList<>();
//
//    public void addRealtimeRent(RealTimeRentImage realTimeRentImage)
//    {
//        realTimeRentImage.setRealTimeRentCar(this);
//        this.realTimeRentImageList.add(realTimeRentImage);
//    }


    @Builder
    public RealTimeRentCar(Long realTimeRentId, MonthlyRent monthlyRent, String carIdx, String carCategory, String carName, String carDetail, String carNo, String carExteriorColor, String carGubun
                        , String carDisplacement, String carMileaget, String carColor, String carOld, String carEngine, String carAttribute01, String orderEnd
                        , String costPerKm, String carCode, double discount, String description, int isExpected, int priceDisplay) {
        this.realTimeRentId = realTimeRentId;
        this.monthlyRent = monthlyRent;
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
        this.costPerKm = costPerKm;
        this.carCode = carCode;
        this.discount = discount;
        this.description = description;
        this.isExpected = isExpected;
        this.priceDisplay = priceDisplay;
    }
}
