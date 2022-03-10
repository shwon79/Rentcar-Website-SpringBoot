package kr.carz.savecar.repository;

import kr.carz.savecar.domain.LongTermRent;
import kr.carz.savecar.domain.LongTermRentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LongTermRentImageRepository extends JpaRepository<LongTermRentImage, Long> {

    List<LongTermRentImage> findAll();
    List<LongTermRentImage> findByLongTermRent(LongTermRent longTermRent);
}
