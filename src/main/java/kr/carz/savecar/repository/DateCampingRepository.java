package kr.carz.savecar.repository;

import kr.carz.savecar.domain.CalendarDate;
import kr.carz.savecar.domain.CampingCarPrice;
import kr.carz.savecar.domain.DateCamping;
import kr.carz.savecar.domain.MonthlyRent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DateCampingRepository extends JpaRepository<DateCamping, Long> {

    List<DateCamping> findAll();
    List<DateCamping> findByDateId(CalendarDate date_id);
    DateCamping findByDateIdAndCarName(CalendarDate date_id, CampingCarPrice car_name);
    List<DateCamping> findByCarNameAndDateIdGreaterThanEqualAndDateIdLessThanEqual(CampingCarPrice car_name, CalendarDate start_date,CalendarDate end_date);

}
