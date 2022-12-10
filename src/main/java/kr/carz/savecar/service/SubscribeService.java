package kr.carz.savecar.service;

import kr.carz.savecar.domain.LongTermRent;
import kr.carz.savecar.domain.Subscribe;
import kr.carz.savecar.dto.LongTermRentDTO;
import kr.carz.savecar.dto.SubscribeDTO;
import kr.carz.savecar.repository.LongTermRentRepository;
import kr.carz.savecar.repository.SubscribeRepository;

import java.util.List;
import java.util.Optional;

public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    public SubscribeService(SubscribeRepository subscribeRepository){
        this.subscribeRepository = subscribeRepository;
    }

    public List<Subscribe> findAll(){
        return subscribeRepository.findAll();
    }

    public Optional<Subscribe> findById(Long subscribeId){
        return subscribeRepository.findById(subscribeId);
    }

    public Long save(Subscribe subscribe){
        return subscribeRepository.save(subscribe).getSubscribeId();
    }

    public Long saveDTO(SubscribeDTO dto){
        return subscribeRepository.save(dto.toEntity()).getSubscribeId();
    }

    public Long updateByDTO(Subscribe longTermRent, SubscribeDTO dto){
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

        return subscribeRepository.save(longTermRent).getSubscribeId();
    }

    public void delete(Subscribe subscribe){
        subscribeRepository.delete(subscribe);
    }
}