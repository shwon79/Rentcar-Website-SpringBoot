package kr.carz.savecar.repository;

import kr.carz.savecar.domain.Rent24;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface Rent24Repository extends JpaRepository<Rent24, Long> {

    Optional<Rent24> findByRent24Id(Long rent24Id);
    List<Rent24> findAll();

}
