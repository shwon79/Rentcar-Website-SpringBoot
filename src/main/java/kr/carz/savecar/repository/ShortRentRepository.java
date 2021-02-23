package kr.carz.savecar.repository;

import kr.carz.savecar.domain.ShortRent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShortRentRepository extends JpaRepository<ShortRent, Long> {

    List<ShortRent> findAll();

}
