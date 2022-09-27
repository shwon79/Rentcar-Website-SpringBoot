package kr.carz.savecar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "RealTimeRentCarImage")
public class RealTimeRentCarImage extends BaseTimeEntity {
    @Id
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "RealTimeRentId")
    private RealTimeRentCar realTimeRentCar;
    private String imageUrl;

    @Builder
    public RealTimeRentCarImage(Long imageId, RealTimeRentCar realTimeRentCar, String imageUrl) {
        this.imageId = imageId;
        this.realTimeRentCar = realTimeRentCar;
        this.imageUrl = imageUrl;
    }
}
