package kr.carz.savecar.repository;

import kr.carz.savecar.domain.MorenReservation;
import kr.carz.savecar.domain.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MorenReservationRepository extends JpaRepository<MorenReservation, Long> {

    List<MorenReservation> findAll();
    MorenReservation findByCarNo(String carNo);
    Page<MorenReservation> findAllByOrderByIdDesc(Pageable pageable);

}
