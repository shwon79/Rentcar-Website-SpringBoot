package kr.carz.savecar.repository;

import kr.carz.savecar.domain.MorenReservation;
import kr.carz.savecar.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MorenReservationRepository extends JpaRepository<MorenReservation, Long> {

    List<MorenReservation> findAll();
}
