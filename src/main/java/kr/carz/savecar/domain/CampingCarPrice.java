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
    private String fourdays;
    private String fivedays;
    private String sevendays;
    private String tendays;
    private String fifteendays;
    private String monthly;
    private String deposit;
    private String yearmodel;
}
