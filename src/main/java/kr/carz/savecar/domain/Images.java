package kr.carz.savecar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Images")
public class Images implements Comparable<Images>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "car_name")
    private CampingCarPrice carName; // 캠핑카 id

    private int title;
    private String url;
    private String isUploaded;
    private String isMain;

    @Builder
    public Images(Long imageId, CampingCarPrice carName, int title, String url, String isUploaded, String isMain) {
        this.carName = carName;
        this.imageId = imageId;
        this.title = title;
        this.url = url;
        this.isUploaded = isUploaded;
        this.isMain = isMain;
    }

    @Override
    public int compareTo(Images o) {
        return Integer.compare(title, o.getTitle());
    }
}
