package kr.carz.savecar.service;

import kr.carz.savecar.domain.TwoYearlyRent;
import kr.carz.savecar.domain.YearlyRent;
import kr.carz.savecar.dto.TwoYearlyRentDTO;
import kr.carz.savecar.dto.YearlyRentDTO;
import kr.carz.savecar.repository.TwoYearlyRentRepository;
import kr.carz.savecar.repository.YearlyRentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class TwoYearlyRentService {
    private final TwoYearlyRentRepository twoYearlyRentRepository;

    public TwoYearlyRentService(TwoYearlyRentRepository twoYearlyRentRepository) {
        this.twoYearlyRentRepository = twoYearlyRentRepository;
    }

    public List<TwoYearlyRent> findAllTwoYearlyRents(){
        return twoYearlyRentRepository.findAll();
    }
    public List<TwoYearlyRent> findByCategory1(String category1){
        return twoYearlyRentRepository.findByCategory1(category1);
    }
    public List<TwoYearlyRent> findByCategory2(String category2){
        return twoYearlyRentRepository.findByCategory2(category2);
    }
    public List<TwoYearlyRent> findNameOfTwoYearlyRents(String category1, String category2){
        return twoYearlyRentRepository.findByCategory1AndCategory2(category1, category2);
    }
    public TwoYearlyRent findPrice(String name){
        return twoYearlyRentRepository.findByName(name);
    }
    public TwoYearlyRent findByMorenCar(Long start, Long end, String name){
        return twoYearlyRentRepository.findByEndGreaterThanEqualAndStartIsLessThanEqualAndNameMoren(end, start, name);
    }
    public Optional findById(Long id){
        return twoYearlyRentRepository.findById(id);
    }

    public Long updateAllPriceByDTO(TwoYearlyRentDTO twoYearlyRentDTO, TwoYearlyRent twoYearlyRent){

        twoYearlyRent.setDeposit(twoYearlyRentDTO.getDeposit());
        twoYearlyRent.setCost_for_20Tk(twoYearlyRentDTO.getCost_for_20Tk());
        twoYearlyRent.setCost_for_30Tk(twoYearlyRentDTO.getCost_for_30Tk());
        twoYearlyRent.setCost_for_40Tk(twoYearlyRentDTO.getCost_for_40Tk());
        twoYearlyRent.setCost_per_km(twoYearlyRentDTO.getCost_per_km());
        twoYearlyRent.setCredit(twoYearlyRentDTO.getCredit());

        return twoYearlyRentRepository.save(twoYearlyRent).getId();
    }
}
