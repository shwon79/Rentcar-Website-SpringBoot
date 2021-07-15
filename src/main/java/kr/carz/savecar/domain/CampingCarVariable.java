package kr.carz.savecar.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CampingCarVariable")
public class CampingCarVariable {
    @Id
    @Column(name = "camp_var_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long campVarId;

    @Column(name = "camp_var_name", nullable = false)
    private String campVarName; // 트래블, 리무진, 유럽의 분류
}
