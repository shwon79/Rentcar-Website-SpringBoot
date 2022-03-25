package kr.carz.savecar.domain;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Data
@NoArgsConstructor
@Table(name = "ExpectedDay")
public class ExpectedDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String expectedDay;

    @Builder
    public ExpectedDay(Long id, String expectedDay) {
        this.id = id;
        this.expectedDay = expectedDay;
    }
}
