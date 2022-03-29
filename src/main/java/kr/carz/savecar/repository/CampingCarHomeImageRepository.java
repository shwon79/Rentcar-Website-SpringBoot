package kr.carz.savecar.repository;

import kr.carz.savecar.domain.CampingCarHome;
import kr.carz.savecar.domain.CampingCarHomeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampingCarHomeImageRepository extends JpaRepository<CampingCarHomeImage, Long> {

    List<CampingCarHomeImage> findAll();
    List<CampingCarHomeImage> findByCampingCarHome(CampingCarHome campingCarHome);
}
