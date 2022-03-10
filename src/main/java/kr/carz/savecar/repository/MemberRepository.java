package kr.carz.savecar.repository;

import kr.carz.savecar.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findAll();
    Optional<Member> findByUsername(String username);

}
