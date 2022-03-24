package kr.carz.savecar.repository;

import kr.carz.savecar.domain.ExpectedDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpectedDayRepository extends JpaRepository<ExpectedDay, Long> {
    Optional<ExpectedDay> findById(Long id);

    List<ExpectedDay> findAll();
}
