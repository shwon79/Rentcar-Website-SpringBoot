package kr.carz.savecar.repository;

import kr.carz.savecar.domain.RealTimeRentCar;
import kr.carz.savecar.domain.RealTimeRentCarImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealTimeRentCarImageRepository extends JpaRepository<RealTimeRentCarImage, Long> {

    List<RealTimeRentCarImage> findAll();
    List<RealTimeRentCarImage> findByRealTimeRentCar(RealTimeRentCar realTimeRentCar);
}
