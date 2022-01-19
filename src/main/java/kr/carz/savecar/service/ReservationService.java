package kr.carz.savecar.service;

import kr.carz.savecar.domain.Reservation;
import kr.carz.savecar.dto.ReservationSaveDTO;
import kr.carz.savecar.repository.ReservationRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Long save(ReservationSaveDTO dto) {
       return reservationRepository.save(dto.toEntity()).getId();
    }

    public List<Reservation> findAllReservations(){
        return reservationRepository.findAll();
    }
    public List<Reservation> findByCreatedDateAfter(LocalDateTime date){
        return reservationRepository.findByCreatedDateIsAfter(date);
    }

}
