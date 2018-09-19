package com.scanapp.services.impl;

import com.scanapp.models.User;
import com.scanapp.repositories.UserRepository;
import com.scanapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    public void addUser(User user){
        userRepository.save(user);
    }
}



