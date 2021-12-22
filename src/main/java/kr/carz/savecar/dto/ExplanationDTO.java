package kr.carz.savecar.dto;

import kr.carz.savecar.domain.Discount;
import kr.carz.savecar.domain.Explanation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    public ExplanationDTO(String camper_price, String europe_basic_option, String limousine_basic_option, String travel_basic_option,
                          String europe_facility, String limousine_facility, String travel_facility,
                          String rent_policy, String rent_insurance, String rent_rule, String refund_policy) {
        this.camper_price = camper_price;
        this.europe_basic_option = europe_basic_option;
        this.limousine_basic_option = limousine_basic_option;
        this.travel_basic_option = travel_basic_option;
        this.europe_facility = europe_facility;
        this.limousine_facility = limousine_facility;
        this.travel_facility = travel_facility;
        this.rent_policy = rent_policy;
        this.rent_insurance = rent_insurance;
        this.rent_rule = rent_rule;
        this.refund_policy = refund_policy;
    }

    public Explanation toEntity() {
        return Explanation.builder()
                .camper_price(camper_price)
                .europe_basic_option(europe_basic_option)
                .limousine_basic_option(limousine_basic_option)
                .travel_basic_option(travel_basic_option)
                .europe_facility(europe_facility)
                .limousine_facility(limousine_facility)
                .travel_facility(travel_facility)
                .rent_policy(rent_policy)
                .rent_insurance(rent_insurance)
                .rent_rule(rent_rule)
                .refund_policy(refund_policy)
                .build();

    }

}
