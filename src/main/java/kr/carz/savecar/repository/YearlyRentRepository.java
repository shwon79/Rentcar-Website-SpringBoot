package kr.carz.savecar.repository;

import kr.carz.savecar.domain.YearlyRent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YearlyRentRepository extends JpaRepository<YearlyRent, Long> {
    List<YearlyRent> findAll();
}
