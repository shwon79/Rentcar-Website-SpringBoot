package kr.carz.savecar.domain;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CalendarDate")
public class CalendarDate {
    @Id
    private String dateId;

    private String year;
    private String month;
    private String day;
    private String season;             // 성수기 비성수기
}
