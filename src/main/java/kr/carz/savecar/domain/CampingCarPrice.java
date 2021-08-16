package kr.carz.savecar.domain;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "CampingCarPrice")
public class CampingCarPrice {
    @Id
    @Column(name = "car_name")
    private String carName;


    @OneToMany(mappedBy = "carName", targetEntity=CalendarTime.class)
    private List<CalendarTime> calendarTimeList = new ArrayList<CalendarTime>();

    public void addCalendarTime(CalendarTime calendarTime)
    {
        calendarTime.setCarName(this);
        this.calendarTimeList.add(calendarTime);
    }


    @OneToMany(mappedBy = "carName", targetEntity=DateCamping.class)
    private List<DateCamping> dateCampingList = new ArrayList<DateCamping>();

    public void addDateCamping(DateCamping dateCamping)
    {
        dateCamping.setCarName(this);
        this.dateCampingList.add(dateCamping);
    }

    private String onedays;
    private String twodays;
    private String threedays;
    private String fourdays;
    private String fivedays;
    private String sixdays;
    private String sevendays;
    private String eightdays;
    private String ninedays;
    private String tendays;
    private String elevendays;
    private String twelvedays;
    private String thirteendays;
    private String fourteendays;
    private String fifteendays;
    private String sixteendays;
    private String seventeendays;
    private String eighteendays;
    private String ninetinedays;
    private String twentydays;
    private String twentyonedays;
    private String twentytwodays;
    private String twentythreedays;
    private String twentyfourdays;
    private String twentyfivedays;
    private String twentysixdays;
    private String twentysevendays;
    private String twentyeightdays;
    private String twentyninedays;
    private String thirtydays;
    private String deposit;
    private String yearmodel;
}
