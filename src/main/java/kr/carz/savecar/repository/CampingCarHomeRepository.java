package kr.carz.savecar.repository;

import kr.carz.savecar.domain.CampingCarHome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampingCarHomeRepository extends JpaRepository<CampingCarHome, Long> {

    List<CampingCarHome> findAll();
}
