package kr.carz.savecar.service;

import kr.carz.savecar.domain.CalendarDate;
import kr.carz.savecar.domain.CalendarTime;
import kr.carz.savecar.domain.CampingcarDateTime2;
import kr.carz.savecar.repository.CalendarDateRepository;
import kr.carz.savecar.repository.CalendarTimeRepository;

import java.util.List;

public class CalendarDateService {

    private final CalendarDateRepository calendarDateRepository;

    public CalendarDateService(CalendarDateRepository calendarDateRepository) {
        this.calendarDateRepository = calendarDateRepository;
    }

    public Long save(CalendarDate calendarDate) {
        return calendarDateRepository.save(calendarDate).getDateId();
    }

    public List<CalendarDate> findCalendarDate(){
        return calendarDateRepository.findAll();
    }
    public CalendarDate findCalendarDateByDateId(Long dateId){
        return calendarDateRepository.findByDateId(dateId);
    }

    public List<CalendarDate> findCalendarDateByMonth(String month){
        return calendarDateRepository.findByMonth(month);
    }
    public CalendarDate findCalendarDateByMonthAndDayAndYear(String month, String day, String year){
        return calendarDateRepository.findByMonthAndDayAndYear(month, day, year);
    }

}
