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

    public List<RealTimeRentCar> findByIsExpected(int isExpected){ return realTimeRentRepository.findByIsExpected(isExpected); }

    public List<RealTimeRentCar> findByCarGubunAndIsExpected(String carGubun, int isExpected){ return realTimeRentRepository.findByCarGubunAndIsExpected(carGubun, isExpected); }
    public List<RealTimeRentCar> findByCarGubunAndIsExpectedAndIsLongTerm(String carGubun, int isExpected, int isLongTerm){ return realTimeRentRepository.findByCarGubunAndIsExpectedAndIsLongTerm(carGubun, isExpected, isLongTerm); }

    public Optional<RealTimeRentCar> findById(Long carId){
        return realTimeRentRepository.findById(carId);
    }

    public List<RealTimeRentCar> findByCarNo(String carNo){
        return realTimeRentRepository.findByCarNo(carNo);
    }
    public Optional<RealTimeRentCar> findByCarCode(String carCode){
        return realTimeRentRepository.findByCarCode(carCode);
    }

    public Long save(RealTimeRentCar realTimeRent){
        return realTimeRentRepository.save(realTimeRent).getRealTimeRentId();
    }

    public void saveAll(List<RealTimeRentCar> realTimeRentList){
        realTimeRentRepository.saveAll(realTimeRentList);
    }

    public RealTimeRentCar saveDTO(RealTimeRentCarDTO realTimeRentDTO){
        return realTimeRentRepository.save(realTimeRentDTO.toEntity());
    }

    public void deleteAllInBatch(){
        realTimeRentRepository.deleteAllInBatch();
    }
    public void deleteById(Long id) {
        realTimeRentRepository.deleteById(id);
    }
}