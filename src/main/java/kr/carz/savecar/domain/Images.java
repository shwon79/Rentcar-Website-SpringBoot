package kr.carz.savecar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Images")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    private String title;
    private String url;

    @Builder
    public Images(Long imageId, String title, String url) {
        this.imageId = imageId;
        this.title = title;
        this.url = url;
    }
}
