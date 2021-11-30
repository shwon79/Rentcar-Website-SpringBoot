package kr.carz.savecar.service;

import kr.carz.savecar.domain.CampingcarDateTime2;
import kr.carz.savecar.repository.CampingcarDateTimeRepository2;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class CampingcarDateTimeService2 {

    private final CampingcarDateTimeRepository2 campingcarDateTimeRepository2;

    public CampingcarDateTimeService2(CampingcarDateTimeRepository2 campingcarDateTimeRepository2) {
        this.campingcarDateTimeRepository2 = campingcarDateTimeRepository2;
    }

    public void delete(CampingcarDateTime2 campingcarDateTime) {
        campingcarDateTimeRepository2.delete(campingcarDateTime);
    }

    public void deleteByDateTimeId(Long dateTimeId) {
       campingcarDateTimeRepository2.deleteById(dateTimeId);
    }


    public Long save2(CampingcarDateTime2 campingcarDateTime) {
        return campingcarDateTimeRepository2.save(campingcarDateTime).getDateTimeId();
    }

    public List<CampingcarDateTime2> findAllReservations(){
        return campingcarDateTimeRepository2.findAll(Sort.by(Sort.Direction.DESC, "dateTimeId"));
    }

    public CampingcarDateTime2 findByDateTimeId(Long id){
        return campingcarDateTimeRepository2.findByDateTimeId(id);
    }

}
