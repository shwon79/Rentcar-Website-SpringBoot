package kr.carz.savecar.service;

import kr.carz.savecar.domain.LongTermRent;
import kr.carz.savecar.dto.LongTermRentDTO;
import kr.carz.savecar.repository.LongTermRentRepository;

import java.util.List;
import java.util.Optional;

public class LongTermRentService {

    private final LongTermRentRepository longTermRentRepository;

    public LongTermRentService(LongTermRentRepository longTermRentRepository){
        this.longTermRentRepository = longTermRentRepository;
    }

    public List<LongTermRent> findAll(){
        return longTermRentRepository.findAll();
    }

    public Optional<LongTermRent> findById(Long carId){
        return longTermRentRepository.findById(carId);
    }

    public List<LongTermRent> findTop4ByOrderBySequenceAsc() { return longTermRentRepository.findTop4ByOrderBySequenceAsc(); }

    public List<LongTermRent> findTop4ByNewOldOrderBySequenceAsc(String newOld) { return longTermRentRepository.findTop4ByNewOldOrderBySequenceAsc(newOld); }

    public Long save(LongTermRent longTermRent){
        return longTermRentRepository.save(longTermRent).getLongTermRentId();
    }

    public Long saveDTO(LongTermRentDTO dto){
        return longTermRentRepository.save(dto.toEntity()).getLongTermRentId();
    }

    public Long updateByDTO(LongTermRent longTermRent, LongTermRentDTO dto){
        longTermRent.setCarName(dto.getCarName());
        longTermRent.setCarNum(dto.getCarNum());
        longTermRent.setCarColor(dto.getCarColor());
        longTermRent.setCarYearModel(dto.getCarYearModel());
        longTermRent.setContractPeriod(dto.getContractPeriod());
        longTermRent.setContractKm(dto.getContractKm());
        longTermRent.setContractPrice(dto.getContractPrice());
        longTermRent.setContractDeposit(dto.getContractDeposit());
        longTermRent.setContractMaintenance(dto.getContractMaintenance());
        longTermRent.setNewOld(dto.getNewOld());
        longTermRent.setFuel(dto.getFuel());
        longTermRent.setDescription(dto.getDescription());

        return longTermRentRepository.save(longTermRent).getLongTermRentId();
    }

    public void delete(LongTermRent longTermRent){
        longTermRentRepository.delete(longTermRent);
    }
}