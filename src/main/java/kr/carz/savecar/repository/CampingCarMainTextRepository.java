package kr.carz.savecar.repository;

import kr.carz.savecar.domain.CampingCarMainText;
import kr.carz.savecar.domain.CampingCarPrice;
import kr.carz.savecar.domain.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampingCarMainTextRepository extends JpaRepository<CampingCarMainText, Long> {
    Optional<CampingCarMainText> findByTitle(String title);
    List<CampingCarMainText> findByCarName(CampingCarPrice carName);
    Optional<CampingCarMainText> findByImageId(Long imageId);

    List<CampingCarMainText> findAll();
}
