package kr.carz.savecar.service;

import kr.carz.savecar.domain.CampingCarReservation;
import kr.carz.savecar.domain.Reservation;
import kr.carz.savecar.dto.ReservationSaveDTO;
import kr.carz.savecar.repository.ReservationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public List<Reservation> findByTitle(String title){
        return reservationRepository.findByTitle(title);
    }
    public Optional<Reservation> findById(Long reservationId){
        return reservationRepository.findById(reservationId);
    }

    public List<Reservation> findByCreatedDateAfter(LocalDateTime date){
        return reservationRepository.findByCreatedDateIsAfter(date);
    }

    public Page<Reservation> findAllPageable(Pageable pageable){
        return reservationRepository.findAllByOrderByIdDesc(pageable);
    }

}
