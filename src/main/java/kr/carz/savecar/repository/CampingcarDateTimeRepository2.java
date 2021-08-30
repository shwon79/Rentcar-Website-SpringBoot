package kr.carz.savecar.repository;

import kr.carz.savecar.domain.CampingcarDateTime2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampingcarDateTimeRepository2 extends JpaRepository<CampingcarDateTime2, Long> {

    List<CampingcarDateTime2> findAll();
    CampingcarDateTime2 findByDateTimeId(Long id);


}
