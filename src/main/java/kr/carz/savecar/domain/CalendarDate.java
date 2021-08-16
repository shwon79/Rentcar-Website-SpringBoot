package kr.carz.savecar.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(exclude = {"calendarTimeList", "dateCampingList"})
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

    @OneToMany(mappedBy = "dateId", targetEntity=CalendarTime.class)
    private List<CalendarTime> calendarTimeList = new ArrayList<CalendarTime>();

    public void addCalendarTime(CalendarTime calendarTime)
    {
        calendarTime.setDateId(this);
        this.calendarTimeList.add(calendarTime);
    }


    @OneToMany(mappedBy = "dateId", targetEntity=DateCamping.class)
    private List<DateCamping> dateCampingList = new ArrayList<DateCamping>();

    public void addDateCamping(DateCamping dateCamping)
    {
        dateCamping.setDateId(this);
        this.dateCampingList.add(dateCamping);
    }
}
