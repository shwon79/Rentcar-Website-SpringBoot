package kr.carz.savecar.repository;

import kr.carz.savecar.domain.MonthlyRent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyRentRepository extends JpaRepository<MonthlyRent, Long> {
    List<MonthlyRent> findAll();

    List<MonthlyRent> findByCategory1(String category1);
    List<MonthlyRent> findByCategory1AndCategory2(String category1, String category2);
    MonthlyRent findByName(String name);
}
