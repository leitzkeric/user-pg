package com.trooper.user_pg.service;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHashingBCrypt implements PasswordHashing {

    @Override
    public String encodePassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    @Override
    public boolean checkEncodedPassword(String encodedPassword, String password) {
        return  BCrypt.checkpw(encodedPassword, password);
    }
}
