package kr.carz.savecar.service;

import kr.carz.savecar.domain.Discount;
import kr.carz.savecar.domain.DiscountSaveDTO;
import kr.carz.savecar.repository.DiscountRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class DiscountService {

    private final DiscountRepository discountRepository;
    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public List<Discount> findAllDiscounts(){
        return discountRepository.findAll();
    }
    public Optional<Discount> findDiscountByCarNo(String carNo){
        return discountRepository.findByCarNo(carNo);
    }

    public Long save(DiscountSaveDTO dto) {
        return discountRepository.save(dto.toEntity()).getDiscountId();
    }

}