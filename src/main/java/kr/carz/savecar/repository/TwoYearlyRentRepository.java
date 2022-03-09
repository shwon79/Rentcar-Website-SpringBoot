package kr.carz.savecar.repository;

import kr.carz.savecar.domain.TwoYearlyRent;
import kr.carz.savecar.domain.YearlyRent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TwoYearlyRentRepository extends JpaRepository<TwoYearlyRent, Long> {
    List<TwoYearlyRent> findAll();

    List<TwoYearlyRent> findByCategory1(String category1);
    List<TwoYearlyRent> findByCategory2(String category2);
    List<TwoYearlyRent> findByCategory1AndCategory2(String category1, String category2);
    TwoYearlyRent findByName(String name);
    TwoYearlyRent findByEndGreaterThanEqualAndStartIsLessThanEqualAndNameMoren(Long end, Long start, String name);
}
