package com.trooper.user_pg.user;

import java.time.LocalDate;

public record UserDetailDTO(
         Long id,
         String login,
         String password,
         LocalDate createDate,
         boolean active,
         boolean admin
) {
    public UserDetailDTO(User user) {
        this(user.getId(), user.getLogin(), user.getPassword(), user.getCreateDate(), user.isActive(), user.getAdmin());
    }
}
