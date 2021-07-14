package kr.carz.savecar.service;

import kr.carz.savecar.domain.CampingCarVariable;
import kr.carz.savecar.repository.CampingCarVariableRepository;

import java.util.List;

public class CampingCarVariableService {

    private final CampingCarVariableRepository campingCarVariableRepository;

    public CampingCarVariableService(CampingCarVariableRepository campingCarVariableRepository) {
        this.campingCarVariableRepository = campingCarVariableRepository;
    }

    public List<CampingCarVariable> findCampingCarVariables(){
        return campingCarVariableRepository.findAll();
    }
}
