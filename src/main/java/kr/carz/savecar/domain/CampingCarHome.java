package kr.carz.savecar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "CampingCarHome")
public class CampingCarHome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long homeId;

    private String title;
    private String description;

    @Builder
    public CampingCarHome(Long homeId, String title, String description) {
        this.homeId = homeId;
        this.title = title;
        this.description = description;
    }
}
