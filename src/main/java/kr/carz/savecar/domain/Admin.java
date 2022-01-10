package kr.carz.savecar.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class Admin {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;

    @Builder
    public Admin(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
