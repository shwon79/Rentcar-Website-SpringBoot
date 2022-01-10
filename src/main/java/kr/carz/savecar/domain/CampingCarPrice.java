package kr.carz.savecar.domain;


import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"calendarTimeList", "dateCampingList"})
@Table(name = "CampingCarPrice")
public class CampingCarPrice {
    @Id
    @Column(name = "car_name")
    private String carName;

    @OneToMany(mappedBy = "carName", targetEntity=CalendarTime.class)
    private List<CalendarTime> calendarTimeList = new ArrayList<>();

    public void addCalendarTime(CalendarTime calendarTime)
    {
        calendarTime.setCarName(this);
        this.calendarTimeList.add(calendarTime);
    }

    @OneToMany(mappedBy = "carName", targetEntity=DateCamping.class)
    private List<DateCamping> dateCampingList = new ArrayList<>();

    public void addDateCamping(DateCamping dateCamping)
    {
        dateCamping.setCarName(this);
        this.dateCampingList.add(dateCamping);
    }

    private String carNum;
    private String carCode;
    private String season;   // 0 비성수기  1 성수기
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

    @Builder
    public CampingCarPrice(String carName, String carNum, String carCode, String season, String onedays, String twodays, String threedays, String fourdays, String fivedays, String sixdays, String sevendays, String eightdays, String ninedays, String tendays, String elevendays, String twelvedays, String thirteendays, String fourteendays
                        , String fifteendays, String sixteendays, String seventeendays, String eighteendays, String ninetinedays, String twentydays, String twentyonedays
                        , String twentytwodays, String twentythreedays, String twentyfourdays, String twentyfivedays, String twentysixdays, String twentysevendays
                        , String twentyeightdays, String twentyninedays, String thirtydays, String deposit, String yearmodel) {
        this.carName = carName;
        this.carNum = carNum;
        this.carCode = carCode;
        this.season = season;
        this.onedays = onedays;
        this.twodays = twodays;
        this.threedays = threedays;
        this.fourdays = fourdays;
        this.fivedays = fivedays;
        this.sixdays = sixdays;
        this.sevendays = sevendays;
        this.eightdays = eightdays;
        this.ninedays = ninedays;
        this.tendays = tendays;
        this.elevendays = elevendays;
        this.twelvedays = twelvedays;
        this.thirteendays = thirteendays;
        this.fourteendays = fourteendays;
        this.fifteendays = fifteendays;
        this.sixteendays = sixteendays;
        this.seventeendays = seventeendays;
        this.eighteendays = eighteendays;
        this.ninetinedays = ninetinedays;
        this.twentydays = twentydays;
        this.twentyonedays = twentyonedays;
        this.twentytwodays = twentytwodays;
        this.twentythreedays = twentythreedays;
        this.twentyfourdays = twentyfourdays;
        this.twentyfivedays = twentyfivedays;
        this.twentysixdays = twentysixdays;
        this.twentysevendays = twentysevendays;
        this.twentyeightdays = twentyeightdays;
        this.twentyninedays = twentyninedays;
        this.thirtydays = thirtydays;
        this.deposit = deposit;
        this.yearmodel = yearmodel;
    }
}
