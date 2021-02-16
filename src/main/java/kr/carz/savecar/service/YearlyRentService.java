package kr.carz.savecar.service;

import kr.carz.savecar.domain.MonthlyRent;
import kr.carz.savecar.domain.YearlyRent;
import kr.carz.savecar.repository.YearlyRentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class YearlyRentService {
    private final YearlyRentRepository yearlyRentRepository;

    public YearlyRentService(YearlyRentRepository yearlyRentRepository) {
        this.yearlyRentRepository = yearlyRentRepository;
    }

    public List<YearlyRent> findYearlyRents(){
        return yearlyRentRepository.findAll();
    }
    public List<YearlyRent> findCategory2OfMonthlyRents(String category1){
        return yearlyRentRepository.findByCategory1(category1);
    }
    public List<YearlyRent> findNameOfYearlyRents(String category1, String category2){
        return yearlyRentRepository.findByCategory1AndCategory2(category1, category2);
    }
    public List<YearlyRent> findMileageOfYearlyRents(String category1, String category2, String name){
        return yearlyRentRepository.findByCategory1AndCategory2AndName(category1, category2, name);
    }
}
