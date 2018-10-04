//package com.scanapp.services.impl;
//
//import com.scanapp.models.User;
//import com.scanapp.repositories.UserRepository;
//import com.scanapp.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserServiceImpl implements UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    public UserService loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = this.userRepository.findByEmail(email);
//
//        if (user == null) {
//            System.out.println("User not found! " + email);
//            throw new UsernameNotFoundException("User with email " + email + " was not found in the database");
//        }
//
//        UserService userDetails = (UserDetails) new User(appUser.getUserName(), //
//                appUser.getEncrytedPassword(), grantList);
//
//}
//
//
//
