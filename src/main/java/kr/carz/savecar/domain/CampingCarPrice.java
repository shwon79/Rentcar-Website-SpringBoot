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
@EqualsAndHashCode(exclude = {"campingCarPriceRates", "calendarTimeList", "dateCampingList"})
@Table(name = "CampingCarPrice")
public class CampingCarPrice {
    @Id
    @Column(name = "car_name")
    private String carName;

    @Column(columnDefinition = "LONGTEXT")
    private String basic_option;

    @Column(columnDefinition = "LONGTEXT")
    private String facility;

    private String carNum;
    private String carCode;
    private String yearmodel;
    private String fuel;
    private String gearBox;
    private String license;
    private String personnel;
    private String camper_price;
    private String rent_policy;
    private String rent_insurance;
    private String rent_rule;
    private String refund_policy;
    private String driver_license;



    @OneToMany(mappedBy = "carName", targetEntity=CampingCarPriceRate.class)
    private List<CampingCarPriceRate> campingCarPriceRates = new ArrayList<>();

    public void addCampingCarPriceRates(CampingCarPriceRate campingCarPriceRate)
    {
        campingCarPriceRate.setCarName(this);
        this.campingCarPriceRates.add(campingCarPriceRate);
    }

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


    @Builder
    public CampingCarPrice(String basic_option, String facility, String camper_price, String rent_policy, String rent_insurance, String rent_rule, String refund_policy, String driver_license
                        , String carName, String carNum, String carCode, String yearmodel, String fuel, String gearBox, String license, String personnel) {
        this.camper_price = camper_price;
        this.rent_policy = rent_policy;
        this.rent_insurance = rent_insurance;
        this.rent_rule = rent_rule;
        this.refund_policy = refund_policy;
        this.driver_license = driver_license;

        this.basic_option = basic_option;
        this.facility = facility;
        this.carName = carName;
        this.carNum = carNum;
        this.carCode = carCode;
        this.yearmodel = yearmodel;
        this.fuel = fuel;
        this.gearBox = gearBox;
        this.license = license;
        this.personnel = personnel;
    }
}
