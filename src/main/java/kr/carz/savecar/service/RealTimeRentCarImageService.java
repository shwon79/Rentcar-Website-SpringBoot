package kr.carz.savecar.service;

import kr.carz.savecar.domain.RealTimeRentCar;
import kr.carz.savecar.domain.RealTimeRentCarImage;
import kr.carz.savecar.dto.RealTimeRentCarImageDTO;
import kr.carz.savecar.repository.RealTimeRentCarImageRepository;

import java.util.List;

public class RealTimeRentCarImageService {

    private final RealTimeRentCarImageRepository realTimeRentImageRepository;

    public RealTimeRentCarImageService(RealTimeRentCarImageRepository realTimeRentImageRepository){
        this.realTimeRentImageRepository = realTimeRentImageRepository;
    }

    public List<RealTimeRentCarImage> findAll(){
        return realTimeRentImageRepository.findAll();
    }

    public List<RealTimeRentCarImage> findByRealTimeRent(RealTimeRentCar realTimeRent){
        return realTimeRentImageRepository.findByRealTimeRentCar(realTimeRent);
    }

    public Long save(RealTimeRentCarImage realTimeRentImage){
        return realTimeRentImageRepository.save(realTimeRentImage).getImageId();
    }
    public void saveAll(List<RealTimeRentCarImage> realTimeRentImageList){
        realTimeRentImageRepository.saveAll(realTimeRentImageList);
    }

    public Long saveDTO(RealTimeRentCarImageDTO realTimeRentImageDTO){
        return realTimeRentImageRepository.save(realTimeRentImageDTO.toEntity()).getImageId();
    }

    public void deleteAllInBatch(){
        realTimeRentImageRepository.deleteAllInBatch();
    }
    public void deleteById(Long id){realTimeRentImageRepository.deleteById(id);}
}