package kr.carz.savecar.service;

import kr.carz.savecar.domain.MonthlyRent;
import kr.carz.savecar.repository.MonthlyRentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MonthlyRentService {

    private final MonthlyRentRepository monthlyRentRepository;

    public MonthlyRentService(MonthlyRentRepository monthlyRentRepository) {
        this.monthlyRentRepository = monthlyRentRepository;
    }

    public List<MonthlyRent> findMonthlyRents(){
        return monthlyRentRepository.findAll();
    }
    public List<MonthlyRent> findCategory2OfMonthlyRents(String category1){
        return monthlyRentRepository.findByCategory1(category1);
    }
    public List<MonthlyRent> findNameOfMonthlyRents(String category1, String category2){
        return monthlyRentRepository.findByCategory1AndCategory2(category1, category2);
    }

    public List<MonthlyRent> findByCategory2(String category2){
        return monthlyRentRepository.findByCategory2(category2);
    }
    public MonthlyRent findPrice(String name){
        return monthlyRentRepository.findByName(name);
    }
    public MonthlyRent findByMorenCar(Long start, Long end, String name){
        return monthlyRentRepository.findByEndGreaterThanEqualAndStartIsLessThanEqualAndNameMoren(end, start, name);
    }
    public Optional findById(Long id){
        return monthlyRentRepository.findById(id);
    }

}
