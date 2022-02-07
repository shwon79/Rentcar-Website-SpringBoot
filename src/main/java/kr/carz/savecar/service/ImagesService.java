package kr.carz.savecar.service;

import kr.carz.savecar.domain.CampingCarPrice;
import kr.carz.savecar.domain.Images;
import kr.carz.savecar.dto.ImagesDTO;
import kr.carz.savecar.repository.ImagesRepository;

import java.util.List;
import java.util.Optional;

public class ImagesService {

    private final ImagesRepository imagesRepository;

    public ImagesService(ImagesRepository imagesRepository){
        this.imagesRepository = imagesRepository;
    }

    public Optional<Images> findImageByTitle(String title){
        return imagesRepository.findByTitle(title);
    }

    public List<Images> findImageByCarName(CampingCarPrice carName){
        return imagesRepository.findByCarName(carName);
    }
    public List<Images> findByCarNameAndIsMain(CampingCarPrice carName, String isMain){
        return imagesRepository.findByCarNameAndIsMain(carName, isMain);
    }
    public Optional<Images> findImageByImageId(Long imageId){
        return imagesRepository.findByImageId(imageId);
    }

    public List<Images> findAllImage(){
        return imagesRepository.findAll();
    }

    public Long save(Images images) {
        return imagesRepository.save(images).getImageId();
    }

    public Long saveDTO(ImagesDTO imagesDTO, CampingCarPrice carName) {
        return imagesRepository.save(imagesDTO.toEntity(carName)).getImageId();
    }

    public void delete(Long imageId) {
        imagesRepository.deleteById(imageId);
    }

    public Long saveOriginalWithDTO(Images image, ImagesDTO dto, CampingCarPrice carName) {

        image.setCarName(carName);
        image.setTitle(dto.getTitle());
        image.setUrl(dto.getUrl());
        image.setIsUploaded(dto.getIsUploaded());
        image.setIsMain(dto.getIsMain());

        return imagesRepository.save(image).getImageId();
    }
}