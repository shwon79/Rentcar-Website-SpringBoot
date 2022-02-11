package kr.carz.savecar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "CampingCarMainText")
public class CampingCarMainText implements Comparable<CampingCarMainText>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "car_name")
    private CampingCarPrice carName; // 캠핑카 id

    private int title;
    private String url;
    private String isUploaded;

    @Builder
    public CampingCarMainText(Long imageId, CampingCarPrice carName, int title, String url, String isUploaded) {
        this.carName = carName;
        this.imageId = imageId;
        this.title = title;
        this.url = url;
        this.isUploaded = isUploaded;
    }

    @Override
    public int compareTo(CampingCarMainText o) {
        return Integer.compare(title, o.getTitle());
    }
}
