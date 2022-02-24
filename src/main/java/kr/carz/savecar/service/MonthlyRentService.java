package kr.carz.savecar.service;

import kr.carz.savecar.domain.MonthlyRent;
import kr.carz.savecar.dto.MonthlyRentDTO;
import kr.carz.savecar.dto.MonthlyRentVO;
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

    public List<MonthlyRent> findAllMonthlyRents(){
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

    public List<String> findDistinctCategory1(){
        return monthlyRentRepository.findDistinctCategory1();
    }

    public Long save(MonthlyRent monthlyRent){
        return monthlyRentRepository.save(monthlyRent).getId();
    }

    public Long updateAllPriceByDTO(MonthlyRentDTO monthlyRentDTO, MonthlyRent monthlyRent){

        monthlyRent.setCategory1(monthlyRentDTO.getCategory1());
        monthlyRent.setCategory2(monthlyRentDTO.getCategory2());
        monthlyRent.setName(monthlyRentDTO.getName());
        monthlyRent.setDeposit(monthlyRentDTO.getDeposit());
        monthlyRent.setCost_for_2k(monthlyRentDTO.getCost_for_2k());
        monthlyRent.setCost_for_2_5k(monthlyRentDTO.getCost_for_2_5k());
        monthlyRent.setCost_for_3k(monthlyRentDTO.getCost_for_3k());
        monthlyRent.setCost_for_4k(monthlyRentDTO.getCost_for_4k());
        monthlyRent.setCost_for_others(monthlyRentDTO.getCost_for_others());
        monthlyRent.setAge_limit(monthlyRentDTO.getAge_limit());
        monthlyRent.setCost_per_km(monthlyRentDTO.getCost_per_km());
        monthlyRent.setNameMoren(monthlyRentDTO.getNameMoren());
        monthlyRent.setStart(monthlyRentDTO.getStart());
        monthlyRent.setEnd(monthlyRentDTO.getEnd());
        monthlyRent.setCredit(monthlyRentDTO.getCredit());
        monthlyRent.setImg_url(monthlyRentDTO.getImg_url());

        return monthlyRentRepository.save(monthlyRent).getId();
    }

    public Long updateAllPriceByVO(MonthlyRentVO monthlyRentVO, MonthlyRent monthlyRent){

        monthlyRent.setCategory1(monthlyRentVO.getCategory1());
        monthlyRent.setCategory2(monthlyRentVO.getCategory2());
        monthlyRent.setName(monthlyRentVO.getName());
        monthlyRent.setDeposit(monthlyRentVO.getDeposit());
        monthlyRent.setCost_for_2k(monthlyRentVO.getCost_for_2k());
        monthlyRent.setCost_for_2_5k(monthlyRentVO.getCost_for_2_5k());
        monthlyRent.setCost_for_3k(monthlyRentVO.getCost_for_3k());
        monthlyRent.setCost_for_4k(monthlyRentVO.getCost_for_4k());
        monthlyRent.setCost_for_others(monthlyRentVO.getCost_for_others());
        monthlyRent.setAge_limit(monthlyRentVO.getAge_limit());
        monthlyRent.setCost_per_km(monthlyRentVO.getCost_per_km());
        monthlyRent.setNameMoren(monthlyRentVO.getNameMoren());
        monthlyRent.setStart(monthlyRentVO.getStart());
        monthlyRent.setEnd(monthlyRentVO.getEnd());
        monthlyRent.setCredit(monthlyRentVO.getCredit());
        monthlyRent.setImg_url(monthlyRentVO.getImg_url());

        return monthlyRentRepository.save(monthlyRent).getId();
    }

    public List<MonthlyRent> findByCategory2AndTwoYearlyRentIsNotNull(String category2){
        return monthlyRentRepository.findByCategory2AndTwoYearlyRentIsNotNull(category2);
    }
}
