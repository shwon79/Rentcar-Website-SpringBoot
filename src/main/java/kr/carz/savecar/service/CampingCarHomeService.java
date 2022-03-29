package kr.carz.savecar.service;

import kr.carz.savecar.domain.CampingCarHome;
import kr.carz.savecar.repository.CampingCarHomeRepository;
import java.util.List;
import java.util.Optional;

public class CampingCarHomeService {

    private final CampingCarHomeRepository campingCarHomeRepository;

    public CampingCarHomeService(CampingCarHomeRepository campingCarHomeRepository){
        this.campingCarHomeRepository = campingCarHomeRepository;
    }

    public List<CampingCarHome> findAll(){
        return campingCarHomeRepository.findAll();
    }

    public Optional<CampingCarHome> findById(Long homeId){
        return campingCarHomeRepository.findById(homeId);
    }

    public void delete(CampingCarHome campingCarHome){
        campingCarHomeRepository.delete(campingCarHome);
    }
}