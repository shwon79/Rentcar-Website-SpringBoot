package kr.carz.savecar.service;

import kr.carz.savecar.domain.Admin;
import kr.carz.savecar.domain.CampingCarReservation;
import kr.carz.savecar.domain.Images;
import kr.carz.savecar.dto.AdminDTO;
import kr.carz.savecar.dto.ImagesDTO;
import kr.carz.savecar.repository.AdminRepository;
import kr.carz.savecar.repository.ImagesRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public Long save(ImagesDTO imagesDTO) {
        return imagesRepository.save(imagesDTO.toEntity()).getImageId();
    }
}