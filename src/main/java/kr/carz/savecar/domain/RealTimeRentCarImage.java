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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "RealTimeRentId")
    private RealTimeRentCar realTimeRentCar;
    private String imageUrl;

    @Builder
    public RealTimeRentCarImage(RealTimeRentCar realTimeRentCar, String imageUrl) {
        this.realTimeRentCar = realTimeRentCar;
        this.imageUrl = imageUrl;
    }
}
