package kr.carz.savecar.repository;

import kr.carz.savecar.domain.RealTimeRentCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealTimeRentCarRepository extends JpaRepository<RealTimeRentCar, Long> {

    List<RealTimeRentCar> findAll();
    List<RealTimeRentCar> findByIsExpected(int isExpected);
    List<RealTimeRentCar> findByCarGubunAndIsExpected(String carGubun, int isExpected);


}
