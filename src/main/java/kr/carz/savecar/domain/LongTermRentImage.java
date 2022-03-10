package kr.carz.savecar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "LongTermRentImage")
public class LongTermRentImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "longTermRentId")
    private LongTermRent longTermRent;
    private String imageUrl;

    @Builder
    public LongTermRentImage(Long imageId, LongTermRent longTermRent, String imageUrl) {
        this.imageId = imageId;
        this.longTermRent = longTermRent;
        this.imageUrl = imageUrl;
    }
}
