package kr.carz.savecar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Explanation")
public class Explanation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(columnDefinition = "LONGTEXT", nullable = true)
    private String camper_price;

    @Column(columnDefinition = "LONGTEXT", nullable = true)
    private String europe_basic_option;

    @Column(columnDefinition = "LONGTEXT", nullable = true)
    private String limousine_basic_option;

    @Column(columnDefinition = "LONGTEXT", nullable = true)
    private String travel_basic_option;

    @Column(columnDefinition = "LONGTEXT", nullable = true)
    private String europe_facility;

    @Column(columnDefinition = "LONGTEXT", nullable = true)
    private String limousine_facility;

    @Column(columnDefinition = "LONGTEXT", nullable = true)
    private String travel_facility;

    @Column(columnDefinition = "LONGTEXT", nullable = true)
    private String rent_policy;

    @Column(columnDefinition = "LONGTEXT", nullable = true)
    private String rent_insurance;

    @Column(columnDefinition = "LONGTEXT", nullable = true)
    private String rent_rule;

    @Column(columnDefinition = "LONGTEXT", nullable = true)
    private String refund_policy;

    @Builder
    public Explanation(String camper_price, String europe_basic_option, String limousine_basic_option, String travel_basic_option,
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
}