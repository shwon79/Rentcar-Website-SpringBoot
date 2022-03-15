package kr.carz.savecar.repository;

import kr.carz.savecar.domain.MonthlyRent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonthlyRentRepository extends JpaRepository<MonthlyRent, Long> {
    List<MonthlyRent> findAll();
    List<MonthlyRent> findByCategory1(String category1);
    List<MonthlyRent> findByCategory1AndCategory2(String category1, String category2);
    List<MonthlyRent> findByCategory2(String category2);
    Optional<MonthlyRent> findByEndGreaterThanEqualAndStartIsLessThanEqualAndNameMoren(Long end, Long start, String name);
    MonthlyRent findByName(String name);

    @Query("SELECT DISTINCT d.category1 FROM MonthlyRent d")
    List<String> findDistinctCategory1();

    List<MonthlyRent> findByCategory2AndTwoYearlyRentIsNotNull(String category2);
    List<MonthlyRent> findAllByTwoYearlyRentIsNotNull();

}
