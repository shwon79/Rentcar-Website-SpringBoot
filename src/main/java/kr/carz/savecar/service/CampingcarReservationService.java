package kr.carz.savecar.service;

import kr.carz.savecar.domain.CampingCarReservation;
import kr.carz.savecar.dto.CampingCarReservationDTO;
import kr.carz.savecar.repository.CampingcarReservationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Long save_campingcar_reservation(CampingCarReservationDTO dto) {
        return campingcarReservationRepository.save(dto.toEntity()).getId();
    }

    public Long saveDTO(CampingCarReservationDTO campingCarReservationDTO, CampingCarReservation campingCarReservation) {

        campingCarReservation.setAgree(campingCarReservationDTO.getAgree());
        campingCarReservation.setCarType(campingCarReservationDTO.getCarType());
        campingCarReservation.setDay(campingCarReservationDTO.getDay());
        campingCarReservation.setDeposit(campingCarReservationDTO.getDeposit());
        campingCarReservation.setDepositor(campingCarReservationDTO.getDepositor());
        campingCarReservation.setDetail(campingCarReservationDTO.getDetail());
        campingCarReservation.setName(campingCarReservationDTO.getName());
        campingCarReservation.setPhone(campingCarReservationDTO.getPhone());
        campingCarReservation.setRentDate(campingCarReservationDTO.getRentDate());
        campingCarReservation.setRentTime(campingCarReservationDTO.getRentTime());
        campingCarReservation.setReservation(campingCarReservationDTO.getReservation());
        campingCarReservation.setReturnDate(campingCarReservationDTO.getReturnDate());
        campingCarReservation.setReturnTime(campingCarReservationDTO.getReturnTime());
        campingCarReservation.setTotal(campingCarReservationDTO.getTotal());
        campingCarReservation.setTotalHalf(campingCarReservationDTO.getTotalHalf());
        campingCarReservation.setExtraTime(campingCarReservationDTO.getExtraTime());

        return campingcarReservationRepository.save(campingCarReservation).getId();
    }

    public Long save(CampingCarReservation campingCarReservation) {

        return campingcarReservationRepository.save(campingCarReservation).getId();
    }

    public List<CampingCarReservation> findAllReservations(){
        return campingcarReservationRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Optional findById(Long id){
        return campingcarReservationRepository.findById(id);
    }

    public Page<CampingCarReservation> findAllPageable(Pageable pageable){
        return campingcarReservationRepository.findAllByOrderByIdDesc(pageable);
    }
}
