package kr.carz.savecar.service;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.repository.DateCampingRepository;

import java.util.List;

public class DateCampingService {

    private final DateCampingRepository dateCampingRepository;

    public DateCampingService(DateCampingRepository dateCampingRepository) {
        this.dateCampingRepository = dateCampingRepository;
    }


    public void save(DateCamping dateCamping) {
        dateCampingRepository.save(dateCamping);
    }

    public List<DateCamping> findDateCampingPrice(){
        return dateCampingRepository.findAll();
    }

    public List<DateCamping> findByDateId(CalendarDate date_id){
        return dateCampingRepository.findByDateId(date_id);
    }


    public DateCamping findByDateIdAndCarName(CalendarDate date_id, CampingCarPrice car_name){
        return dateCampingRepository.findByDateIdAndCarName(date_id, car_name);
    }

    public List<DateCamping> findByCarNameAndDateIdGreaterThanEqualAndDateIdLessThanEqual(CampingCarPrice car_name, CalendarDate start_date,CalendarDate end_date){
        return dateCampingRepository.findByCarNameAndDateIdGreaterThanEqualAndDateIdLessThanEqual(car_name, start_date, end_date);
    }

}