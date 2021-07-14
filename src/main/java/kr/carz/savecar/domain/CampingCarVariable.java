package kr.carz.savecar.domain;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CampingCarVariable")
public class CampingCarVariable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long campVarId;

    private String campVarName; // 트래블, 리무진, 유럽의 분류
}
