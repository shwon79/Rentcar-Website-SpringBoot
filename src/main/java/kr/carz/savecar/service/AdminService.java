package kr.carz.savecar.service;

import kr.carz.savecar.domain.Admin;
import kr.carz.savecar.dto.AdminDTO;
import kr.carz.savecar.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminService implements UserDetailsService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }

    // 회원가입
    @Transactional
    public Long signUp(AdminDTO adminDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        adminDTO.setPassword(passwordEncoder.encode(adminDTO.getPassword()));

        // password를 암호화 한 뒤 dp에 저장
        return adminRepository.save(adminDTO.toEntity()).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 로그인을 하기 위해 가입된 user정보를 조회하는 메서드
        Optional<Admin> adminWrapper = adminRepository.findByUsername(username);
        Admin admin = adminWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));

        // 아이디, 비밀번호, 권한리스트를 매개변수로 User를 만들어 반환해준다.
        return new User(admin.getUsername(), admin.getPassword(), authorities);
    }
}