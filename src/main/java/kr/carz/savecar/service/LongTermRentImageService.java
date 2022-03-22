package kr.carz.savecar.service;

import kr.carz.savecar.domain.LongTermRent;
import kr.carz.savecar.domain.LongTermRentImage;
import kr.carz.savecar.dto.LongTermRentImageDTO;
import kr.carz.savecar.repository.LongTermRentImageRepository;
import kr.carz.savecar.repository.LongTermRentRepository;

import java.util.List;
import java.util.Optional;

public class LongTermRentImageService {

    private final LongTermRentImageRepository longTermRentImageRepository;

    public LongTermRentImageService(LongTermRentImageRepository longTermRentImageRepository){
        this.longTermRentImageRepository = longTermRentImageRepository;
    }

    public List<LongTermRentImage> findAll(){
        return longTermRentImageRepository.findAll();
    }

    public List<LongTermRentImage> findByLongTermRent(LongTermRent longTermRent){
        return longTermRentImageRepository.findByLongTermRent(longTermRent);
    }

    public Long saveDTO(LongTermRentImageDTO dto){
        return longTermRentImageRepository.save(dto.toEntity()).getImageId();
    }
    public void delete(LongTermRentImage image){
        longTermRentImageRepository.delete(image);
    }
}