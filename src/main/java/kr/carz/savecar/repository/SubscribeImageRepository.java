package kr.carz.savecar.repository;

import kr.carz.savecar.domain.LongTermRent;
import kr.carz.savecar.domain.LongTermRentImage;
import kr.carz.savecar.domain.Subscribe;
import kr.carz.savecar.domain.SubscribeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribeImageRepository extends JpaRepository<SubscribeImage, Long> {

    List<SubscribeImage> findAll();
    List<SubscribeImage> findBySubscribe(Subscribe subscribe);
}
