package kr.carz.savecar.service;

import kr.carz.savecar.domain.CalendarDate;
import kr.carz.savecar.domain.CalendarTime;
import kr.carz.savecar.domain.CampingCar;
import kr.carz.savecar.domain.CampingCarPrice;
import kr.carz.savecar.repository.CalendarTimeRepository;
import kr.carz.savecar.repository.CampingCarRepository;

import java.util.List;

public class CalendarTimeService {

    private final CalendarTimeRepository calendarTimeRepository;

    public CalendarTimeService(CalendarTimeRepository calendarTimeRepository) {
        this.calendarTimeRepository = calendarTimeRepository;
    }

    public List<CalendarTime> findCalendarTime(){
        return calendarTimeRepository.findAll();
    }
    public List<CalendarTime> findCalendarTimeByDateIdAndCarName(CalendarDate dateId, CampingCarPrice carName){
        return calendarTimeRepository.findByDateIdAndCarName(dateId, carName);
    }

}
