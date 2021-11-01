package kr.carz.savecar.service;

import kr.carz.savecar.domain.CampingCar;
import kr.carz.savecar.domain.Login;
import kr.carz.savecar.repository.CampingCarRepository;
import kr.carz.savecar.repository.LoginRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class LoginService {

    private final LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public List<Login> findAllLogins(){
        return loginRepository.findAll();
    }

    public Login findLoginByIdAndPwd(String id, String pwd){
        return loginRepository.findByIdAndPassword(id, pwd);
    }

}