package kr.carz.savecar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "SubscribeImage")
public class SubscribeImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "subscribeId")
    private Subscribe subscribe;
    private String imageUrl;

    @Builder
    public SubscribeImage(Long imageId, Subscribe subscribe, String imageUrl) {
        this.imageId = imageId;
        this.subscribe = subscribe;
        this.imageUrl = imageUrl;
    }
}
