package kr.carz.savecar.service;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.repository.DateCampingRepository;

import java.util.List;

public class DateCampingService {

    private final DateCampingRepository dateCampingRepository;

    public DateCampingService(DateCampingRepository dateCampingRepository) {
        this.dateCampingRepository = dateCampingRepository;
    }


    public CalendarDate save(DateCamping dateCamping) {
        return dateCampingRepository.save(dateCamping).getDateId();
    }

    public List<DateCamping> findByDateId(CalendarDate date_id){
        return dateCampingRepository.findByDateId(date_id);
    }

    public DateCamping findByDateIdAndCarName(CalendarDate date_id, CampingCarPrice car_name){
        return dateCampingRepository.findByDateIdAndCarName(date_id, car_name);
    }

    public List<DateCamping> findByCarNameAndDateIdGreaterThanEqualAndDateIdLessThanEqual(CampingCarPrice campingCarPrice, CalendarDate start_date, CalendarDate end_date){
        return dateCampingRepository.findByCarNameAndDateIdGreaterThanEqualAndDateIdLessThanEqual(campingCarPrice, start_date, end_date);
    }

}
