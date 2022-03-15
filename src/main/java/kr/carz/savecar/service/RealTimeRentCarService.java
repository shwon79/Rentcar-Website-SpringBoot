package kr.carz.savecar.service;

import kr.carz.savecar.domain.RealTimeRentCar;
import kr.carz.savecar.dto.RealTimeRentCarDTO;
import kr.carz.savecar.repository.RealTimeRentCarRepository;

import java.util.List;
import java.util.Optional;

public class RealTimeRentCarService {

    private final RealTimeRentCarRepository realTimeRentRepository;

    public RealTimeRentCarService(RealTimeRentCarRepository realTimeRentRepository){
        this.realTimeRentRepository = realTimeRentRepository;
    }

    public List<RealTimeRentCar> findAll(){
        return realTimeRentRepository.findAll();
    }

    public Optional<RealTimeRentCar> findById(Long carId){
        return realTimeRentRepository.findById(carId);
    }

    public Long save(RealTimeRentCar realTimeRent){
        return realTimeRentRepository.save(realTimeRent).getRealTimeRentId();
    }

    public RealTimeRentCar saveDTO(RealTimeRentCarDTO realTimeRentDTO){
        return realTimeRentRepository.save(realTimeRentDTO.toEntity());
    }

    public void deleteAllInBatch(){
        realTimeRentRepository.deleteAllInBatch();
    }
}