package kr.carz.savecar.service;

import kr.carz.savecar.domain.*;
import kr.carz.savecar.repository.CampingCarPriceRepository;
import kr.carz.savecar.repository.DateCampingRepository;

import java.util.Date;
import java.util.List;

public class DateCampingService {

    private final DateCampingRepository dateCampingRepository;

    public DateCampingService(DateCampingRepository dateCampingRepository) {
        this.dateCampingRepository = dateCampingRepository;
    }


    public CalendarDate save(DateCamping dateCamping) {
        return dateCampingRepository.save(dateCamping).getDateId();
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

}
