package kr.carz.savecar.service;

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
}
