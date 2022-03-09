package kr.carz.savecar.service;

import kr.carz.savecar.domain.MorenReservation;
import kr.carz.savecar.domain.Reservation;
import kr.carz.savecar.dto.MorenReservationDTO;
import kr.carz.savecar.repository.MorenReservationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MorenReservationService {

    private final MorenReservationRepository morenReservationRepository;

    public MorenReservationService(MorenReservationRepository morenReservationRepository) {
        this.morenReservationRepository = morenReservationRepository;
    }
    public Long save(MorenReservation morenReservation) {
        return morenReservationRepository.save(morenReservation).getId();
    }

    public Long saveDTO(MorenReservationDTO dto) {
        return morenReservationRepository.save(dto.toEntity()).getId();
    }

    public List<MorenReservation> findAllMorenReservations(){
        return morenReservationRepository.findAll();
    }
    public Optional<MorenReservation> findMorenReservationById(Long id){
        return morenReservationRepository.findById(id);
    }
    public void delete(MorenReservation morenReservation) { morenReservationRepository.delete(morenReservation); }

    public Page<MorenReservation> findAllPageable(Pageable pageable){
        return morenReservationRepository.findAllByOrderByIdDesc(pageable);
    }
}
