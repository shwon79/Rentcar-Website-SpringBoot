package kr.carz.savecar.service;

import kr.carz.savecar.domain.TwoYearlyRent;
import kr.carz.savecar.domain.YearlyRent;
import kr.carz.savecar.repository.TwoYearlyRentRepository;
import kr.carz.savecar.repository.YearlyRentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class TwoYearlyRentService {
    private final TwoYearlyRentRepository twoYearlyRentRepository;

    public TwoYearlyRentService(TwoYearlyRentRepository twoYearlyRentRepository) {
        this.twoYearlyRentRepository = twoYearlyRentRepository;
    }

    public List<TwoYearlyRent> findTwoYearlyRents(){
        return twoYearlyRentRepository.findAll();
    }
    public List<TwoYearlyRent> findByCategory1(String category1){
        return twoYearlyRentRepository.findByCategory1(category1);
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
}