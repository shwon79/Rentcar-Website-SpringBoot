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
    private Float onedays;
    private Float twodays;
    private Float threedays;
    private Float fourdays;
    private Float fivedays;
    private Float sixdays;
    private Float sevendays;
    private Float eightdays;
    private Float ninedays;
    private Float tendays;
    private Float elevendays;
    private Float twelvedays;
    private Float thirteendays;
    private Float fourteendays;
    private Float fifteendays;
    private Float sixteendays;
    private Float seventeendays;
    private Float eighteendays;
    private Float ninetinedays;
    private Float twentydays;
    private Float twentyonedays;
    private Float twentytwodays;
    private Float twentythreedays;
    private Float twentyfourdays;
    private Float twentyfivedays;
    private Float twentysixdays;
    private Float twentysevendays;
    private Float twentyeightdays;
    private Float twentyninedays;
    private Float thirtydays;
    private String deposit;
    private String yearmodel;
    private String fuel;
    private String gearBox;
    private String license;
    private String personnel;


    @Builder
    public CampingCarPrice(String carName, String carNum, String carCode, String season, Float onedays, Float twodays, Float threedays, Float fourdays, Float fivedays, Float sixdays, Float sevendays, Float eightdays, Float ninedays, Float tendays, Float elevendays, Float twelvedays, Float thirteendays, Float fourteendays
                        , Float fifteendays, Float sixteendays, Float seventeendays, Float eighteendays, Float ninetinedays, Float twentydays, Float twentyonedays
                        , Float twentytwodays, Float twentythreedays, Float twentyfourdays, Float twentyfivedays, Float twentysixdays, Float twentysevendays
                        , Float twentyeightdays, Float twentyninedays, Float thirtydays, String deposit, String yearmodel, String fuel, String gearBox, String license, String personnel) {
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
        this.fuel = fuel;
        this.gearBox = gearBox;
        this.license = license;
        this.personnel = personnel;
    }
}
