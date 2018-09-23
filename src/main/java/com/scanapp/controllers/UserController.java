package com.scanapp.controllers;

import com.scanapp.models.User;
import com.scanapp.repositories.UserRepository;
import com.scanapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.enterprise.inject.Model;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private UserService userService;

    //add new User
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String addUser(@RequestParam("firstName") String firstName,
                          @RequestParam("lastName") String lastName,
                          @RequestParam("email") String email) {
        System.out.println(firstName);
        User user = new User();
        if (emailExist(email)) {
            System.out.println("user already exists");

        } else {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            userRepository.save(user);

        }
        return "dashboard/users";
    }


    //update new User
    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public String updateUser(@RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String lastName,
                             @RequestParam("email") String email) {
        System.out.println(firstName);
        User user = userRepository.findByEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        userRepository.save(user);
        return "dashboard/users";
    }


    @RequestMapping(value="/add", method = RequestMethod.GET)
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, User user){
        modelAndView.addObject("user", user);
        modelAndView.setViewName("dashboard/users");
        return modelAndView;
    }

    //get All Users
    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userRepository.findAll();

    }

    //delete User
//    @GetMapping(value = "/delete")
//    public String deleteUser(@RequestParam("email") String email) {
//                System.out.println(email);
//                User user = userRepository.findByEmail(email);
//
//                userRepository.delete(user);
//                return "dashboard/users";
//
//    }

    //other methods
    private boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }


}




