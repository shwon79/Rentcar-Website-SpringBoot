package kr.carz.savecar.dto;

import kr.carz.savecar.domain.CampingCarPrice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
public class CampingCarPriceDTO {

    private String carNum;
    private String carCode;
    private String yearmodel;
    private String fuel;
    private String gearBox;
    private String license;
    private String personnel;
    private String basic_option;
    private String facility;
    private String camper_price;
    private String rent_policy;
    private String rent_insurance;
    private String rent_rule;
    private String refund_policy;
    private String driver_license;


    public CampingCarPrice toEntity() {
        return CampingCarPrice.builder()
                .carNum(carNum)
                .carCode(carCode)
                .yearmodel(yearmodel)
                .fuel(fuel)
                .gearBox(gearBox)
                .license(license)
                .personnel(personnel)
                .basic_option(basic_option)
                .facility(facility)
                .camper_price(camper_price)
                .rent_policy(rent_policy)
                .rent_insurance(rent_insurance)
                .rent_rule(rent_rule)
                .refund_policy(refund_policy)
                .driver_license(driver_license)
                .build();

    }
}
