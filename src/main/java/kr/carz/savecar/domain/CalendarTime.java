package kr.carz.savecar.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(exclude = {"dateId", "carName"})
@Table(name = "CalendarTime")
public class CalendarTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long timeId;

    @ManyToOne
    @JoinColumn(name = "date_id")
    private CalendarDate dateId; // 날짜 id

    @ManyToOne
    @JoinColumn(name = "car_name")
    private CampingCarPrice carName; // 캠핑카 id

    @Column(name = "reserve_time")
    private String reserveTime;    // 예약시작시간

    @Column(name = "reserve_complete")
    private String reserveComplete;   // 시간당 예약여부
}
