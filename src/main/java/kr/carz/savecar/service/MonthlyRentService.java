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
}
