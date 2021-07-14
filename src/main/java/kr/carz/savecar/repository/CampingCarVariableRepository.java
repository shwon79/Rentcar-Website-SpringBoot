package kr.carz.savecar.repository;

import kr.carz.savecar.domain.CampingCar;
import kr.carz.savecar.domain.CampingCarVariable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampingCarVariableRepository extends JpaRepository<CampingCarVariable, Long> {

    List<CampingCarVariable> findAll();

}
