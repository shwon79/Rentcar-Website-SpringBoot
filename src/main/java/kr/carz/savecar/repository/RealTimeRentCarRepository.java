package kr.carz.savecar.repository;

import kr.carz.savecar.domain.RealTimeRentCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RealTimeRentCarRepository extends JpaRepository<RealTimeRentCar, Long> {

    List<RealTimeRentCar> findAll();
    List<RealTimeRentCar> findByIsExpected(int isExpected);
    List<RealTimeRentCar> findByCarGubunAndIsExpected(String carGubun, int isExpected);
    List<RealTimeRentCar> findByCarGubunAndIsExpectedAndIsLongTerm(String carGubun, int isExpected, int isLongTerm);
    List<RealTimeRentCar> findByCarNo(String carNo);
    Optional<RealTimeRentCar> findByCarCode(String carCode);
}
