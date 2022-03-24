package kr.carz.savecar.service;

import kr.carz.savecar.domain.Discount;
import kr.carz.savecar.domain.ExpectedDay;
import kr.carz.savecar.dto.DiscountSaveDTO;
import kr.carz.savecar.repository.DiscountRepository;
import kr.carz.savecar.repository.ExpectedDayRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class ExpectedDayService {

    private final ExpectedDayRepository expectedDayRepository;
    public ExpectedDayService(ExpectedDayRepository expectedDayRepository) {
        this.expectedDayRepository = expectedDayRepository;
    }

    public List<ExpectedDay> findAll(){
        return expectedDayRepository.findAll();
    }
    public Optional<ExpectedDay> findById(Long id){
        return expectedDayRepository.findById(id);
    }

}