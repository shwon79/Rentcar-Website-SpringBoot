package kr.carz.savecar.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(exclude = {"dateId", "carName"})
@Table(name = "DateCamping")
@IdClass(MultiId.class)
public class DateCamping implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "dateId")
    private CalendarDate dateId; // 날짜 id

    @Id
    @ManyToOne
    @JoinColumn(name = "car_name")
    private CampingCarPrice carName; // 캠핑카 이름(key)

    private String reserved;    // 예약완료여부
}
