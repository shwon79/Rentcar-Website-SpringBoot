package kr.carz.savecar.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
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

}