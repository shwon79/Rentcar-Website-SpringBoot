package kr.carz.savecar.service;

import kr.carz.savecar.domain.CampingcarDateTime;
import kr.carz.savecar.domain.CampingcarDateTimeDto;
import kr.carz.savecar.domain.Reservation;
import kr.carz.savecar.domain.ReservationSaveDto;
import kr.carz.savecar.repository.CampingcarDateTimeRepository;
import kr.carz.savecar.repository.ReservationRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class CampingcarDateTimeService {

    private final CampingcarDateTimeRepository campingcarDateTimeRepository;

    public CampingcarDateTimeService(CampingcarDateTimeRepository campingcarDateTimeRepository) {
        this.campingcarDateTimeRepository = campingcarDateTimeRepository;
    }

//    public Long save(CampingcarDateTimeDto dto) {
//       return campingcarDateTimeRepository.save(dto.toEntity()).getId();
//    }

//    public List<CampingcarDateTime> findAllReservations(){
//        return campingcarDateTimeRepository.findAll();
//    }
}
