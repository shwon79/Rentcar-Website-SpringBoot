package kr.carz.savecar.repository;

import kr.carz.savecar.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAll();
    List<Reservation> findByTitle(String title);
    List<Reservation> findByCreatedDateAfter(LocalDateTime date);
    List<Reservation> findByCreatedDateIsAfter(LocalDateTime date);
}
