package kr.carz.savecar.service;

import kr.carz.savecar.domain.LongTermRent;
import kr.carz.savecar.domain.LongTermRentImage;
import kr.carz.savecar.domain.Subscribe;
import kr.carz.savecar.domain.SubscribeImage;
import kr.carz.savecar.dto.LongTermRentImageDTO;
import kr.carz.savecar.dto.SubscribeImageDTO;
import kr.carz.savecar.repository.LongTermRentImageRepository;
import kr.carz.savecar.repository.SubscribeImageRepository;
import kr.carz.savecar.repository.SubscribeRepository;

import java.util.List;
import java.util.Optional;

public class SubscribeImageService {

    private final SubscribeImageRepository subscribeImageRepository;

    public SubscribeImageService(SubscribeImageRepository subscribeImageRepository){
        this.subscribeImageRepository = subscribeImageRepository;
    }

    public List<SubscribeImage> findAll(){
        return subscribeImageRepository.findAll();
    }

    public Optional<SubscribeImage> findById(Long imageId){
        return subscribeImageRepository.findById(imageId);
    }
    public List<SubscribeImage> findBySubscribe(Subscribe subscribe){
        return subscribeImageRepository.findBySubscribe(subscribe);
    }

    public Long saveDTO(SubscribeImageDTO dto){
        return subscribeImageRepository.save(dto.toEntity()).getImageId();
    }
    public void delete(SubscribeImage image){
        subscribeImageRepository.delete(image);
    }
}