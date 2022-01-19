package kr.carz.savecar.service;

import kr.carz.savecar.domain.ValuesForWeb;
import kr.carz.savecar.dto.ValuesForWebDTO;
import kr.carz.savecar.repository.ValuesForWebRepository;

import java.util.Optional;

public class ValuesForWebService {

    private final ValuesForWebRepository valuesForWebRepository;

    public ValuesForWebService(ValuesForWebRepository valuesForWebRepository){
        this.valuesForWebRepository = valuesForWebRepository;
    }

    public Optional<ValuesForWeb> findValueByTitle(String title){
        return valuesForWebRepository.findByTitle(title);
    }

    public Long save(ValuesForWebDTO valuesForWebDTO) {
        return valuesForWebRepository.save(valuesForWebDTO.toEntity()).getValueId();
    }
}