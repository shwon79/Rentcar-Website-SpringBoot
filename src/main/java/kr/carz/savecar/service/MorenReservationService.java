package kr.carz.savecar.service;

import kr.carz.savecar.domain.MorenReservation;
import kr.carz.savecar.domain.MorenReservationDTO;
import kr.carz.savecar.domain.ReservationSaveDTO;
import kr.carz.savecar.repository.MorenReservationRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class MorenReservationService {

    private final MorenReservationRepository morenReservationRepository;

    public MorenReservationService(MorenReservationRepository morenReservationRepository) {
        this.morenReservationRepository = morenReservationRepository;
    }


    public Long save(MorenReservationDTO dto) {
        return morenReservationRepository.save(dto.toEntity()).getId();
    }

    public List<MorenReservation> findAllMorenReservations(){
        return morenReservationRepository.findAll();
    }
}
