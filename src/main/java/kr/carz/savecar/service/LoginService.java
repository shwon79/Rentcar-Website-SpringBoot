package kr.carz.savecar.service;

import kr.carz.savecar.domain.CampingCar;
import kr.carz.savecar.domain.Login;
import kr.carz.savecar.repository.CampingCarRepository;
import kr.carz.savecar.repository.LoginRepository;

import java.util.List;

public class LoginService {

    private final LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public List<Login> findAllLogins(){
        return loginRepository.findAll();
    }

    public Login findLoginById(String id){
        return loginRepository.findById(id);
    }



}
