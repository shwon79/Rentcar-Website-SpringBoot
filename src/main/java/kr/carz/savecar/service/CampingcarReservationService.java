package kr.carz.savecar.service;

import kr.carz.savecar.domain.CampingCarReservation;
import kr.carz.savecar.repository.CampingcarReservationRepository;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class CampingcarReservationService {

    private final CampingcarReservationRepository campingcarReservationRepository;

    public CampingcarReservationService(CampingcarReservationRepository campingcarReservationRepository) {
        this.campingcarReservationRepository = campingcarReservationRepository;
    }

    public void delete(CampingCarReservation campingcarReservation) {
        campingcarReservationRepository.delete(campingcarReservation);
    }

    public void deleteById(Long id) {
       campingcarReservationRepository.deleteById(id);
    }

    public Long save_campingcar_reservation(CampingCarReservation campingcarReservation) {
        return campingcarReservationRepository.save(campingcarReservation).getId();
    }

    public List<CampingCarReservation> findAllReservations(){
        return campingcarReservationRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Optional findById(Long id){
        return campingcarReservationRepository.findById(id);
    }

}
