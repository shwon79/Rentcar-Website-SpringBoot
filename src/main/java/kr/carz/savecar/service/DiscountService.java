package kr.carz.savecar.service;

import kr.carz.savecar.domain.Discount;
import kr.carz.savecar.dto.DiscountSaveDTO;
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
    public Optional<Discount> findDiscountByDiscountId(Long discountId){
        return discountRepository.findById(discountId);
    }

    public Long save(Discount discount) {
        return discountRepository.save(discount).getDiscountId();
    }

    public Long saveDTO(DiscountSaveDTO save_dto) {
        return discountRepository.save(save_dto.toEntity()).getDiscountId();
    }
    public void delete(Discount discount) { discountRepository.delete(discount); }

}