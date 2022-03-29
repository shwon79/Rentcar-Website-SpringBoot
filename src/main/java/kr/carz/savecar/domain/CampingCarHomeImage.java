package kr.carz.savecar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "CampingCarHomeImage")
public class CampingCarHomeImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "homeId")
    private CampingCarHome campingCarHome;
    private String imageUrl;

    @Builder
    public CampingCarHomeImage(Long imageId, CampingCarHome campingCarHome, String imageUrl) {
        this.imageId = imageId;
        this.campingCarHome = campingCarHome;
        this.imageUrl = imageUrl;
    }
}
