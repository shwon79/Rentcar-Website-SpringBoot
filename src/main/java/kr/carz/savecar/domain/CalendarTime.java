package kr.carz.savecar.domain;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CalendarTime")
public class CalendarTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long timeId;

    @ManyToOne
    @JoinColumn(name = "date_id")
    private CalendarDate dateId; // 날짜 id
    private String reserve_time;    // 예약시작시간
    private String reserve_complete;   // 시간당 예약여부
}
