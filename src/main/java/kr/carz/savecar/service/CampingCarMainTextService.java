package kr.carz.savecar.service;

import kr.carz.savecar.domain.CampingCarMainText;
import kr.carz.savecar.domain.CampingCarPrice;
import kr.carz.savecar.domain.Images;
import kr.carz.savecar.dto.CampingCarMainTextDTO;
import kr.carz.savecar.dto.ImagesDTO;
import kr.carz.savecar.repository.CampingCarMainTextRepository;
import kr.carz.savecar.repository.ImagesRepository;

import java.util.List;
import java.util.Optional;

public class CampingCarMainTextService {

    private final CampingCarMainTextRepository campingCarMainTextRepository;

    public CampingCarMainTextService(CampingCarMainTextRepository campingCarMainTextRepository){
        this.campingCarMainTextRepository = campingCarMainTextRepository;
    }

    public List<CampingCarMainText> findImageByCarName(CampingCarPrice carName){
        return campingCarMainTextRepository.findByCarName(carName);
    }
    public Optional<CampingCarMainText> findImageByImageId(Long imageId){
        return campingCarMainTextRepository.findByImageId(imageId);
    }

    public List<CampingCarMainText> findAllImage(){
        return campingCarMainTextRepository.findAll();
    }

    public Long save(CampingCarMainText campingCarMainText) {
        return campingCarMainTextRepository.save(campingCarMainText).getImageId();
    }

    public Long saveDTO(CampingCarMainTextDTO dto, CampingCarPrice carName) {
        return campingCarMainTextRepository.save(dto.toEntity(carName)).getImageId();
    }

    public void delete(Long imageId) {
        campingCarMainTextRepository.deleteById(imageId);
    }

    public Long saveOriginalWithDTO(CampingCarMainText campingCarMainText, CampingCarMainTextDTO dto, CampingCarPrice carName) {

        campingCarMainText.setCarName(carName);
        campingCarMainText.setTitle(dto.getTitle());
        campingCarMainText.setUrl(dto.getUrl());
        campingCarMainText.setIsUploaded(dto.getIsUploaded());

        return campingCarMainTextRepository.save(campingCarMainText).getImageId();
    }
}