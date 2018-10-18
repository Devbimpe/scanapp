package com.scanapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomPassword extends BCryptPasswordEncoder {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String encode(CharSequence rawPassword) {
        return super.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        System.out.println("paasword etered {}" + rawPassword);
        return super.matches(rawPassword, encodedPassword);
    }
}
