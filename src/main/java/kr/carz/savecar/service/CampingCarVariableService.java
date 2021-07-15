package kr.carz.savecar.service;

import kr.carz.savecar.domain.CampingCarVariable;
import kr.carz.savecar.repository.CampingCarVariableRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CampingCarVariableService {

    @Autowired
    private final CampingCarVariableRepository campingCarVariableRepository;

    public CampingCarVariableService(CampingCarVariableRepository campingCarVariableRepository) {
        this.campingCarVariableRepository = campingCarVariableRepository;
    }

    public List<CampingCarVariable> findCampingCarVariables(){
        return campingCarVariableRepository.findAll();
    }
}
