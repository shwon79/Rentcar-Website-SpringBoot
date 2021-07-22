package kr.carz.savecar.service;

import kr.carz.savecar.domain.CalendarDate;
import kr.carz.savecar.domain.CampingCarPrice;
import kr.carz.savecar.repository.CalendarDateRepository;
import kr.carz.savecar.repository.CampingCarPriceRepository;

import java.util.List;

public class CampingCarPriceService {

    private final CampingCarPriceRepository campingCarPriceRepository;

    public CampingCarPriceService(CampingCarPriceRepository campingCarPriceRepository) {
        this.campingCarPriceRepository = campingCarPriceRepository;
    }

    public List<CampingCarPrice> findCampingCarPrice(){
        return campingCarPriceRepository.findAll();
    }
    public CampingCarPrice findCampingCarPriceByCarName(String carName){
        return campingCarPriceRepository.findByCarName(carName);
    }

}
