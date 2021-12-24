package kr.carz.savecar.domain;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
public class MultiId implements Serializable {

    private static final long serialVersionUID = 242141234124312341L;

    private CalendarDate dateId; // 날짜 id
    private CampingCarPrice carName; // 캠핑카 이름(key)

}
