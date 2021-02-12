package kr.carz.savecar.repository;

import kr.carz.savecar.domain.MonthlyRent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyRentRepository extends JpaRepository<MonthlyRent, Long> {
    List<MonthlyRent> findAll();
}
