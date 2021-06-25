package kr.carz.savecar.service;

import kr.carz.savecar.domain.CampingCar;
import kr.carz.savecar.domain.ShortRent;
import kr.carz.savecar.repository.ShortRentRepository;

import java.util.List;

public class ShortRentService {
    private final ShortRentRepository shortRentRepository;

    public ShortRentService(ShortRentRepository shortRentRepository) {
        this.shortRentRepository = shortRentRepository;
    }
    public List<ShortRent> findShortRents(){
        return shortRentRepository.findAll();
    }

    public List<ShortRent> findShortRentsByCategory1(String category1){
        return shortRentRepository.findByCategory1Equals(category1);
    }
    public List<ShortRent> findShortRentsByNotCategory1(String category1){
        return shortRentRepository.findByCategory1Not(category1);
    }
}
