package kr.carz.savecar.repository;

import kr.carz.savecar.domain.MonthlyRent;
import kr.carz.savecar.domain.YearlyRent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YearlyRentRepository extends JpaRepository<YearlyRent, Long> {
    List<YearlyRent> findAll();

    List<YearlyRent> findByCategory1(String category1);
    List<YearlyRent> findByCategory1AndCategory2(String category1, String category2);
    List<YearlyRent> findByCategory1AndCategory2AndName(String category1, String category2, String name);
}
