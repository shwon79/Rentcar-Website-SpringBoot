package kr.carz.savecar.service;

import kr.carz.savecar.domain.*;
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

    public CalendarTime findCalendarTimeByDateIdAndCarNameAndReserveTime(CalendarDate dateId, CampingCarPrice carName, String reserve_time){
        return calendarTimeRepository.findByDateIdAndCarNameAndReserveTime(dateId, carName, reserve_time);
    }
    public CalendarTime findCalendarTimeByTimeId(Long timeId){
        return calendarTimeRepository.findByTimeId(timeId);
    }

    public List<CalendarTime> findByDateIdAndCarNameAndReserveTimeGreaterThanEqual(CalendarDate date_id, CampingCarPrice car_name, String start_date){
        return calendarTimeRepository.findByDateIdAndCarNameAndReserveTimeGreaterThanEqual(date_id, car_name, start_date);
    }
    public List<CalendarTime> findByDateIdAndCarNameAndReserveTimeLessThanEqual(CalendarDate date_id,CampingCarPrice car_name, String start_date){
        return calendarTimeRepository.findByDateIdAndCarNameAndReserveTimeLessThanEqual(date_id, car_name, start_date);
    }
    public void save(CalendarTime calendarTime){
        calendarTimeRepository.save(calendarTime);
    }

}
