package kr.carz.savecar.service;

import kr.carz.savecar.domain.Reservation;
import kr.carz.savecar.domain.ReservationSaveDTO;
import kr.carz.savecar.repository.ReservationRepository;
import org.springframework.transaction.annotation.Transactional;

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
}
