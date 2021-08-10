package kr.carz.savecar.domain;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CampingCarPrice")
public class CampingCarPrice {
    @Id
    @Column(name = "car_name")
    private String carName;

    private String onedays;
    private String twodays;
    private String threedays;
    private String fourdays;
    private String fivedays;
    private String sixdays;
    private String sevendays;
    private String eightdays;
    private String ninedays;
    private String tendays;
    private String elevendays;
    private String twelvedays;
    private String thirteendays;
    private String fourteendays;
    private String fifteendays;
    private String sixteendays;
    private String seventeendays;
    private String eighteendays;
    private String ninetinedays;
    private String twentydays;
    private String twentyonedays;
    private String twentytwodays;
    private String twentythreedays;
    private String twentyfourdays;
    private String twentyfivedays;
    private String twentysixdays;
    private String twentysevendays;
    private String twentyeightdays;
    private String twentyninedays;
    private String thirtydays;
    private String deposit;
    private String yearmodel;
}
