package kr.carz.savecar.repository;

import kr.carz.savecar.domain.ValuesForWeb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValuesForWebRepository extends JpaRepository<ValuesForWeb, Long> {
    Optional<ValuesForWeb> findByTitle(String title);
}
