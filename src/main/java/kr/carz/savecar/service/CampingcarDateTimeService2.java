package kr.carz.savecar.service;

import kr.carz.savecar.domain.CampingcarDateTime2;
import kr.carz.savecar.repository.CampingcarDateTimeRepository2;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class CampingcarDateTimeService2 {

    private final CampingcarDateTimeRepository2 campingcarDateTimeRepository2;

    public CampingcarDateTimeService2(CampingcarDateTimeRepository2 campingcarDateTimeRepository2) {
        this.campingcarDateTimeRepository2 = campingcarDateTimeRepository2;
    }

//    public Long save(CampingcarDateTimeDto dto) {
//       return campingcarDateTimeRepository2.save(dto.toEntity()).getDateTimeId();
//    }


    public Long save2(CampingcarDateTime2 campingcarDateTime) {
        return campingcarDateTimeRepository2.save(campingcarDateTime).getDateTimeId();
    }

    public List<CampingcarDateTime2> findAllReservations(){
        return campingcarDateTimeRepository2.findAll();
    }

    public CampingcarDateTime2 findByDateTimeId(Long id){
        return campingcarDateTimeRepository2.findByDateTimeId(id);
    }

}
