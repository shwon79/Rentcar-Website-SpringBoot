package kr.carz.savecar.repository;

import kr.carz.savecar.domain.CalendarDate;
import kr.carz.savecar.domain.Explanation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExplanationRepository extends JpaRepository<Explanation, Long> {

}
