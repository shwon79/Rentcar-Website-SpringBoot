package kr.carz.savecar.service;

import kr.carz.savecar.domain.CampingcarDateTime2;
import kr.carz.savecar.domain.CampingcarDateTimeDto;
import kr.carz.savecar.repository.CampingcarDateTimeRepository2;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class CampingcarDateTimeService2 {

    private final CampingcarDateTimeRepository2 campingcarDateTimeRepository2;

    public CampingcarDateTimeService2(CampingcarDateTimeRepository2 campingcarDateTimeRepository2) {
        this.campingcarDateTimeRepository2 = campingcarDateTimeRepository2;
    }

    public Long save(CampingcarDateTimeDto dto) {
       return campingcarDateTimeRepository2.save(dto.toEntity()).getDateTimeId();
    }

    public List<CampingcarDateTime2> findAllReservations(){
        return campingcarDateTimeRepository2.findAll();
    }

    public Optional<CampingcarDateTime2> findById(Long id){
        return campingcarDateTimeRepository2.findById(id);
    }

}
