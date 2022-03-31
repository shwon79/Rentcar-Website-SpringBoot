package kr.carz.savecar.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "CampingCarHome")
public class CampingCarHome implements Comparable<CampingCarHome>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long homeId;

    private String title;
    private String description;
    private int sequence;
    private int columnNum;

    @Builder
    public CampingCarHome(Long homeId, String title, String description, int sequence, int columnNum) {
        this.homeId = homeId;
        this.title = title;
        this.description = description;
        this.sequence = sequence;
        this.columnNum = columnNum;
    }

    @Override
    public int compareTo(CampingCarHome o) {
        return Integer.compare(this.sequence, o.sequence);
    }
}
