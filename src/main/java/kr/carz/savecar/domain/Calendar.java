package kr.carz.savecar.domain;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Calendar")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long calId;

    private Long campId; // 캠핑카 id
    private String reserve_date;
    private String reserve_time;
    private String reserve_per_time;
    private String reserve_complete;
    private String season;
}
