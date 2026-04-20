package com.trooper.user_pg.user;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "users")
@Entity(name = "User")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    private LocalDate createDate;
    private boolean active;
    private Boolean admin;

    public User(@Valid UserDTO data, String passwd) {
        this.login = data.login();
        this.password = passwd;
        this.createDate = LocalDate.now();
        this.active = true;
        if (data.admin() != null) {
            this.admin = data.admin();
        } else this.admin = false;
    }

    public void excluir() {

        this.active = false;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", createDate=" + createDate +
                ", active=" + active +
                ", admin=" + admin +
                '}';
    }
}
