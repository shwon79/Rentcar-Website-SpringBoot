package kr.carz.savecar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ValuesForWeb")
public class ValuesForWeb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long valueId;

    private String title;
    private String value;

    @Builder
    public ValuesForWeb(Long valueId, String title, String value) {
        this.valueId = valueId;
        this.title = title;
        this.value = value;
    }
}
