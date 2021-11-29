package kr.carz.savecar.repository;

import kr.carz.savecar.domain.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {

    List<Login> findAll();
    Login findByIdAndPassword(String id, String pwd);


}