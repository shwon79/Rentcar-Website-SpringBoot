package kr.carz.savecar.service;

import kr.carz.savecar.domain.CampingCarPriceRate;
import kr.carz.savecar.repository.CampingCarRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class CampingCarService {

    private final CampingCarRepository campingCarRepository;

    public CampingCarService(CampingCarRepository campingCarRepository) {
        this.campingCarRepository = campingCarRepository;
    }

    public List<CampingCarPriceRate> findCampingCarRents(){
        return campingCarRepository.findAll();
    }

}
