package com.trooper.user_pg.service;

public interface PasswordHashing {

    String encodePassword (String password);
    boolean checkEncodedPassword(String encodedPassword, String password);
}
