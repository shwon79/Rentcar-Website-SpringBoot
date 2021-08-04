package kr.carz.savecar.repository;

import kr.carz.savecar.domain.CampingcarDateTime;
import kr.carz.savecar.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampingcarDateTimeRepository extends JpaRepository<Reservation, Long> {

//    List<CampingcarDateTime> findAll();
}
