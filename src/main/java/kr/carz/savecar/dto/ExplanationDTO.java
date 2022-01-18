package kr.carz.savecar.dto;

import kr.carz.savecar.domain.Discount;
import kr.carz.savecar.domain.Explanation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExplanationDTO {

    private String camper_price;
    private String europe_basic_option;
    private String limousine_basic_option;
    private String travel_basic_option;
    private String europe_facility;
    private String limousine_facility;
    private String travel_facility;
    private String rent_policy;
    private String rent_insurance;
    private String rent_rule;
    private String refund_policy;
    private String driver_license;

}
