package kr.carz.savecar.service;

import kr.carz.savecar.domain.Explanation;
import kr.carz.savecar.repository.ExplanationRepository;

import java.util.Optional;


public class ExplanationService {

    private final ExplanationRepository explanationRepository;

    public ExplanationService(ExplanationRepository explanationRepository) {
        this.explanationRepository = explanationRepository;
    }

    public Optional findById(Long id){
        return explanationRepository.findById(id);
    }

    public Explanation save(Explanation explanation){
        return explanationRepository.save(explanation);
    }

}
