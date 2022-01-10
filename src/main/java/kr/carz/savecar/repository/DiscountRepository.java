package kr.carz.savecar.repository;

import kr.carz.savecar.domain.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    List<Discount> findAll();
    Optional<Discount> findByCarNo(String carNo);


}