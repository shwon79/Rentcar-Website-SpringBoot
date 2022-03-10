package kr.carz.savecar.service;

import kr.carz.savecar.domain.CampingCarPrice;
import kr.carz.savecar.dto.CampingCarPriceDTO;
import kr.carz.savecar.repository.CampingCarPriceRepository;

import java.util.List;

public class CampingCarPriceService {

    private final CampingCarPriceRepository campingCarPriceRepository;

    public CampingCarPriceService(CampingCarPriceRepository campingCarPriceRepository) {
        this.campingCarPriceRepository = campingCarPriceRepository;
    }

    public String save(CampingCarPrice campingCarPrice) {
        return campingCarPriceRepository.save(campingCarPrice).getCarName();
    }


    public String saveDTO(CampingCarPrice campingCarPrice, CampingCarPriceDTO dto) {
        campingCarPrice.setCarNum(dto.getCarNum());
        campingCarPrice.setCarCode(dto.getCarCode());
        campingCarPrice.setYearmodel(dto.getYearmodel());
        campingCarPrice.setFuel(dto.getFuel());
        campingCarPrice.setGearBox(dto.getGearBox());
        campingCarPrice.setLicense(dto.getLicense());
        campingCarPrice.setPersonnel(dto.getPersonnel());

        campingCarPrice.setBasic_option(dto.getBasic_option());
        campingCarPrice.setFacility(dto.getFacility());
        campingCarPrice.setCamper_price(dto.getCamper_price());
        campingCarPrice.setRent_policy(dto.getRent_policy());
        campingCarPrice.setRent_insurance(dto.getRent_insurance());
        campingCarPrice.setRent_rule(dto.getRent_rule());
        campingCarPrice.setRefund_policy(dto.getRefund_policy());
        campingCarPrice.setDriver_license(dto.getDriver_license());

        return campingCarPriceRepository.save(campingCarPrice).getCarName();
    }


    public List<CampingCarPrice> findAllCampingCarPrice(){
        return campingCarPriceRepository.findAll();
    }

    public CampingCarPrice findCampingCarPriceByCarName(String carName){
        return campingCarPriceRepository.findByCarName(carName);
    }

}
