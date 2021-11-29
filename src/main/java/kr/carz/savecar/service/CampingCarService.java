package kr.carz.savecar.service;

import kr.carz.savecar.domain.CampingCar;
import kr.carz.savecar.domain.MonthlyRent;
import kr.carz.savecar.repository.CampingCarRepository;
import kr.carz.savecar.repository.ShortRentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class CampingCarService {

    private final CampingCarRepository campingCarRepository;

    public CampingCarService(CampingCarRepository campingCarRepository) {
        this.campingCarRepository = campingCarRepository;
    }

    public List<CampingCar> findCampingCarRents(){
        return campingCarRepository.findAll();
    }

}
