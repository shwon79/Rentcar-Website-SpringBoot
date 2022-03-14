package kr.carz.savecar.service;

import kr.carz.savecar.domain.RealTimeRent;
import kr.carz.savecar.dto.RealTimeRentDTO;
import kr.carz.savecar.repository.RealTimeRentRepository;

import java.util.List;
import java.util.Optional;

public class RealTimeRentService {

    private final RealTimeRentRepository realTimeRentRepository;

    public RealTimeRentService(RealTimeRentRepository realTimeRentRepository){
        this.realTimeRentRepository = realTimeRentRepository;
    }

    public List<RealTimeRent> findAll(){
        return realTimeRentRepository.findAll();
    }

    public Optional<RealTimeRent> findById(Long carId){
        return realTimeRentRepository.findById(carId);
    }

    public Long save(RealTimeRent realTimeRent){
        return realTimeRentRepository.save(realTimeRent).getRealTimeRentId();
    }

    public Long saveDTO(RealTimeRentDTO realTimeRentDTO){
        return realTimeRentRepository.save(realTimeRentDTO.toEntity()).getRealTimeRentId();
    }

    public void deleteAllInBatch(){
        realTimeRentRepository.deleteAllInBatch();
    }
}