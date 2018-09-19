package com.scanapp.controllers;

import com.scanapp.models.User;
import com.scanapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class UserController{

    @Autowired
    private UserRepository userRepository;

    //add new User
 @RequestMapping(path="/add", method = RequestMethod.POST)
 public String addNewUser (@RequestParam (required = false) String firstname, @RequestParam (required = false) String lastname,@RequestParam (required = false) String email) {
        User user = new User();
        System.out.println(firstname);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEmail(email);
        userRepository.save(user);
        return "dashboard/users";
    }


    //get All Users
    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}


