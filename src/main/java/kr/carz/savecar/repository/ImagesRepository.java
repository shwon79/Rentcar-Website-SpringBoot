package kr.carz.savecar.repository;

import kr.carz.savecar.domain.Admin;
import kr.carz.savecar.domain.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImagesRepository extends JpaRepository<Images, Long> {
    Optional<Images> findByTitle(String title);
}