package kr.carz.savecar.service;

import kr.carz.savecar.domain.CampingCarHome;
import kr.carz.savecar.domain.CampingCarHomeImage;
import kr.carz.savecar.dto.CampingCarHomeImageDTO;
import kr.carz.savecar.repository.CampingCarHomeImageRepository;

import java.util.List;
import java.util.Optional;

public class CampingCarHomeImageService {

    private final CampingCarHomeImageRepository campingCarHomeImageRepository;

    public CampingCarHomeImageService(CampingCarHomeImageRepository campingCarHomeImageRepository){
        this.campingCarHomeImageRepository = campingCarHomeImageRepository;
    }

    public List<CampingCarHomeImage> findAll(){
        return campingCarHomeImageRepository.findAll();
    }

    public Optional<CampingCarHomeImage> findById(Long imageId){
        return campingCarHomeImageRepository.findById(imageId);
    }
    public List<CampingCarHomeImage> findByCampingCarHome(CampingCarHome campingCarHome){
        return campingCarHomeImageRepository.findByCampingCarHome(campingCarHome);
    }

    public void delete(CampingCarHomeImage image){
        campingCarHomeImageRepository.delete(image);
    }

    public Long saveDTO(CampingCarHomeImageDTO campingCarHomeImageDTO, CampingCarHome campingCarHome, String imageUrl){
        return campingCarHomeImageRepository.save(campingCarHomeImageDTO.toEntity(campingCarHome, imageUrl)).getImageId();
    }
}