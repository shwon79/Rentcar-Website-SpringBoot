package kr.carz.savecar.domain;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CalendarDate")
public class CalendarDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dateId;

    private String year;
    private String month;
    private String day;
    private String wDay;                // 요일
    private String season;             // 성수기 비성수기
}
