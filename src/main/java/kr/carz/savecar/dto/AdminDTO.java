package kr.carz.savecar.dto;

import kr.carz.savecar.domain.Admin;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {
    private Long id;
    private String username;
    private String password;

    // Member 객체로 변환
    public Admin toEntity() {
        return Admin.builder()
                .id(id)
                .username(username)
                .password(password)
                .build();
    }
}