package kr.carz.savecar.service;

import kr.carz.savecar.domain.CampingCarPrice;
import kr.carz.savecar.domain.CampingCarPriceRate;
import kr.carz.savecar.dto.CampingCarPriceRateDTO;
import kr.carz.savecar.repository.CampingCarPriceRateRepository;
import kr.carz.savecar.repository.CampingCarPriceRepository;

import java.util.List;

public class CampingCarPriceRateService {

    private final CampingCarPriceRateRepository campingCarPriceRateRepository;

    public CampingCarPriceRateService(CampingCarPriceRateRepository campingCarPriceRateRepository) {
        this.campingCarPriceRateRepository = campingCarPriceRateRepository;
    }

    public Long save(CampingCarPriceRate campingCarPriceRate) {
        return campingCarPriceRateRepository.save(campingCarPriceRate).getId();
    }

    public Long saveDto(CampingCarPriceRateDTO dto, CampingCarPrice carName) {
        return campingCarPriceRateRepository.save(dto.toEntity(carName)).getId();
    }

    public List<CampingCarPriceRate> findAllCampingCarPriceRate(){
        return campingCarPriceRateRepository.findAll();
    }
    public CampingCarPriceRate findByCarNameAndSeason(CampingCarPrice carName, String season){
        return campingCarPriceRateRepository.findByCarNameAndSeason(carName, season);
    }

    public List<CampingCarPriceRate> findByCarName(CampingCarPrice carName){
        return campingCarPriceRateRepository.findByCarName(carName);
    }

    public List<CampingCarPriceRate> findBySeason(String season){
        return campingCarPriceRateRepository.findBySeason(season);
    }

}
