package kr.carz.savecar.service;

import kr.carz.savecar.domain.ReservationSaveDto;
import kr.carz.savecar.repository.ReservationRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Long save(ReservationSaveDto dto) {
       return reservationRepository.save(dto.toEntity()).getId();
    }
}
