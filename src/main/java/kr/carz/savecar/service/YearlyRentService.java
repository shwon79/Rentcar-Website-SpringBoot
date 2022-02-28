package kr.carz.savecar.service;

import kr.carz.savecar.domain.MonthlyRent;
import kr.carz.savecar.domain.YearlyRent;
import kr.carz.savecar.dto.*;
import kr.carz.savecar.repository.YearlyRentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class YearlyRentService {
    private final YearlyRentRepository yearlyRentRepository;

    public YearlyRentService(YearlyRentRepository yearlyRentRepository) {
        this.yearlyRentRepository = yearlyRentRepository;
    }

    public List<YearlyRent> findAllYearlyRents(){
        return yearlyRentRepository.findAll();
    }
    public List<YearlyRent> findCategory2OfMonthlyRents(String category1){
        return yearlyRentRepository.findByCategory1(category1);
    }
    public List<YearlyRent> findByCategory2(String category2){
        return yearlyRentRepository.findByCategory2(category2);
    }
    public List<YearlyRent> findNameOfYearlyRents(String category1, String category2){
        return yearlyRentRepository.findByCategory1AndCategory2(category1, category2);
    }
    public YearlyRent findPrice(String name){
        return yearlyRentRepository.findByName(name);
    }
    public YearlyRent findByMorenCar(Long start, Long end, String name){
        return yearlyRentRepository.findByEndGreaterThanEqualAndStartIsLessThanEqualAndNameMoren(end, start, name);
    }
    public Optional findById(Long id){
        return yearlyRentRepository.findById(id);
    }

    public Long updateAllPriceByDTO(YearlyRentDTO yearlyRentDTO, YearlyRent yearlyRent){

        yearlyRent.setDeposit(yearlyRentDTO.getDeposit());
        yearlyRent.setCost_for_20k(yearlyRentDTO.getCost_for_20k());
        yearlyRent.setCost_for_30k(yearlyRentDTO.getCost_for_30k());
        yearlyRent.setCost_for_40k(yearlyRentDTO.getCost_for_40k());
        yearlyRent.setCost_per_km(yearlyRentDTO.getCost_per_km());
        yearlyRent.setCredit(yearlyRentDTO.getCredit());

        return yearlyRentRepository.save(yearlyRent).getId();
    }

    public Long saveByRentCarVO(RentCarVO rentCarVO, String imgUrl){

        YearlyRentDTO yearlyRentDTO = new YearlyRentDTO(rentCarVO.getCategory1(), rentCarVO.getCategory2(), rentCarVO.getDeposit_yearly(), rentCarVO.getName()
                , rentCarVO.getCost_for_20k(), rentCarVO.getCost_for_30k(), rentCarVO.getCost_for_40k(), rentCarVO.getCost_for_others(), rentCarVO.getAge_limit()
                , rentCarVO.getCost_per_km_yearly(), rentCarVO.getNameMoren(), rentCarVO.getStart(), rentCarVO.getEnd(), rentCarVO.getCredit_yearly(), imgUrl);

        return yearlyRentRepository.save(yearlyRentDTO.toEntity()).getId();
    }

    public Optional<YearlyRent> findByid(Long yearRentId) {
        return yearlyRentRepository.findById(yearRentId);
    }
}
