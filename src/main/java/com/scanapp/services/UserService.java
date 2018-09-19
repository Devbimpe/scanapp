package com.scanapp.services;

import com.scanapp.models.User;
import org.springframework.stereotype.Service;


@Service
public interface UserService {

    void addUser(User user);
}

