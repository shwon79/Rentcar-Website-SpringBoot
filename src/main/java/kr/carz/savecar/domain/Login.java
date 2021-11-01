package kr.carz.savecar.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Login")
public class Login {

    @Id
    private String id;
    private String password;
}