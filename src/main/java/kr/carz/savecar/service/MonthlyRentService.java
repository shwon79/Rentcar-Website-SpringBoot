package kr.carz.savecar.service;

import kr.carz.savecar.domain.MonthlyRent;
import kr.carz.savecar.repository.MonthlyRentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<MonthlyRent> findMileageOfMonthlyRents(String category1, String category2, String name){
        return monthlyRentRepository.findByCategory1AndCategory2AndName(category1, category2, name);
    }


}
