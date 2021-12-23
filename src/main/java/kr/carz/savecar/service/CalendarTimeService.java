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

    public CalendarTime findCalendarTimeByDateIdAndCarNameAndReserveTime(CalendarDate dateId, CampingCarPrice carName, String reserve_time){
        return calendarTimeRepository.findByDateIdAndCarNameAndReserveTime(dateId, carName, reserve_time);
    }
    public CalendarTime findCalendarTimeByTimeId(Long timeId){
        return calendarTimeRepository.findByTimeId(timeId);
    }
    public List<CalendarTime> findByDateIdAndCarNameAndReserveTimeGreaterThanEqual(CalendarDate dateId, CampingCarPrice carName, String reserve_time){
        return calendarTimeRepository.findByDateIdAndCarNameAndReserveTimeGreaterThanEqual(dateId, carName, reserve_time);
    }
    public List<CalendarTime> findByDateIdAndCarNameAndReserveTimeLessThanEqual(CalendarDate dateId, CampingCarPrice carName, String reserve_time){
        return calendarTimeRepository.findByDateIdAndCarNameAndReserveTimeLessThanEqual(dateId, carName, reserve_time);
    }
    public void save_calendar_time_list(List<CalendarTime> calendarTimeList){
        for(int i=0; i<calendarTimeList.size(); i++){
            calendarTimeRepository.save(calendarTimeList.get(i));
        }
    }
    public void save(CalendarTime CalendarTime) {
        calendarTimeRepository.save(CalendarTime);
    }

}
