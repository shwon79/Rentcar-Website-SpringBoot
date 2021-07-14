package kr.carz.savecar.domain;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CampingCarPrice")
public class CampingCarPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long campId;

    private Long campVarId; // 캠핑카분류 id
    private String onedays;
    private String fourdays; // 성수기, 비수기
    private String fivedays;
    private String sevendays;
    private String tendays;
    private String fifteendays;
    private String monthly;
    private String deposit;
    private String yearmodel;
}
