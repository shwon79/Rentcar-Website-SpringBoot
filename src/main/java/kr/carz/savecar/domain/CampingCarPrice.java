package kr.carz.savecar.domain;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CampingCarPrice")
public class CampingCarPrice {
    @Id
    @Column(name = "camp_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long campId;

    @ManyToOne
    @JoinColumn(name = "camp_var_id")
    private CampingCarVariable campVarId; // 캠핑카 id
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
