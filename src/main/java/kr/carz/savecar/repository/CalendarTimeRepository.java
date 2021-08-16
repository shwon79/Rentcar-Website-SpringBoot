package kr.carz.savecar.repository;

import kr.carz.savecar.domain.CalendarDate;
import kr.carz.savecar.domain.CalendarTime;
import kr.carz.savecar.domain.CampingCar;
import kr.carz.savecar.domain.CampingCarPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarTimeRepository extends JpaRepository<CalendarTime, Long> {

    List<CalendarTime> findAll();
    List<CalendarTime> findByDateIdAndCarName(CalendarDate dateId, CampingCarPrice carName);
//    CalendarTime findByDateIdAndCarNameAndReserve_time(CalendarDate dateId, CampingCarPrice carName, String reserve_time);

}
