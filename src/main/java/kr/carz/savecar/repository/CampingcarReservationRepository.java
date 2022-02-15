package kr.carz.savecar.repository;

import kr.carz.savecar.domain.CampingCarReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampingcarReservationRepository extends JpaRepository<CampingCarReservation, Long> {

    List<CampingCarReservation> findAll();

    Page<CampingCarReservation> findAllByOrderByIdDesc(Pageable pageable);

}
