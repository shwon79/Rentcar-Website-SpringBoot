package kr.carz.savecar.dto;

import kr.carz.savecar.domain.Admin;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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

    @Builder
    public AdminDTO(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}