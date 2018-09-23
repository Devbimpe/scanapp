package com.scanapp.services;

import com.scanapp.models.User;
import com.scanapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User deleteByEmail(String email){
        return userRepository.deleteByEmail(email);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}

