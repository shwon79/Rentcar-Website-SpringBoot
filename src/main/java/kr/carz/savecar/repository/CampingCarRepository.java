package kr.carz.savecar.repository;

import kr.carz.savecar.domain.CampingCarPriceRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampingCarRepository extends JpaRepository<CampingCarPriceRate, Long> {

    List<CampingCarPriceRate> findAll();

}
