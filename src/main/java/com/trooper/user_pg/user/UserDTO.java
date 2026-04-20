package com.trooper.user_pg.user;

import jakarta.validation.constraints.NotBlank;

public record UserDTO(
         Long id,
         @NotBlank
         String login,
         @NotBlank
         String password,
         Boolean admin
) {
    public UserDTO (User user) {
        this(user.getId(), user.getLogin(), user.getPassword(), user.getAdmin());
    }
}
