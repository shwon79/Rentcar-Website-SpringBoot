package kr.carz.savecar.repository;

import kr.carz.savecar.domain.CalendarDate;
import kr.carz.savecar.domain.CalendarTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarDateRepository extends JpaRepository<CalendarDate, Long> {

    List<CalendarDate> findAll();
    CalendarDate findByDateId(Long dateId);
    List<CalendarDate> findByYearAndMonth(String year, String month);
    CalendarDate findByMonthAndDayAndYear(String month, String day, String year);


}
