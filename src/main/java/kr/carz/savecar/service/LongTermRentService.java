package kr.carz.savecar.service;

import kr.carz.savecar.domain.LongTermRent;
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
}