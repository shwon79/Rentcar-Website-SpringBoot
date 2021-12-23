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

    @Column(columnDefinition = "LONGTEXT")
    private String camper_price;

    @Column(columnDefinition = "LONGTEXT")
    private String europe_basic_option;

    @Column(columnDefinition = "LONGTEXT")
    private String limousine_basic_option;

    @Column(columnDefinition = "LONGTEXT")
    private String travel_basic_option;

    @Column(columnDefinition = "LONGTEXT")
    private String europe_facility;

    @Column(columnDefinition = "LONGTEXT")
    private String limousine_facility;

    @Column(columnDefinition = "LONGTEXT")
    private String travel_facility;

    @Column(columnDefinition = "LONGTEXT")
    private String rent_policy;

    @Column(columnDefinition = "LONGTEXT")
    private String rent_insurance;

    @Column(columnDefinition = "LONGTEXT")
    private String driver_license;

    @Column(columnDefinition = "LONGTEXT")
    private String rent_rule;

    @Column(columnDefinition = "LONGTEXT")
    private String refund_policy;

}