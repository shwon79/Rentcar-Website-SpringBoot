package kr.carz.savecar.repository;

import kr.carz.savecar.domain.LongTermRent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LongTermRentRepository extends JpaRepository<LongTermRent, Long> {

    List<LongTermRent> findAll();
    List<LongTermRent> findTop4ByOrderBySequenceAsc();
}
