package com.scanapp.controllers;

import com.scanapp.models.User;
import com.scanapp.repositories.UserRepository;
import com.scanapp.services.UserService;
import com.sun.deploy.net.proxy.AutoProxyScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ModelAttribute;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

import static org.bouncycastle.crypto.tls.ContentType.alert;

@Controller
@RequestMapping("/")
public class UserController {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserRepository userRepository;

    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //add new User
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String addUser(@RequestParam("firstName") String firstName,
                          @RequestParam("lastName") String lastName,
                          @RequestParam("email") String email ,
                          @RequestParam("password") String password, Model model) {
        System.out.println(firstName);
        User user = new User();
        if (emailExist(email)) {
            System.out.println("user already exists");

        } else {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);



        }
        model.addAttribute("message", "User Created Successfully");
        return "dashboard/users";
    }


    //update new User
    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public String updateUser(@RequestParam("firstName") String firstName,
                          @RequestParam("lastName") String lastName,
                          @RequestParam("email") String email,
                             Model model) {
        System.out.println(firstName);
            User user = userRepository.findByEmail(email);
            String codedFirstName = "" ;
            String codedLastName = "";
            codedFirstName = firstName.replaceAll("[^a-zA-Z0-9/s+]+", "");
            codedLastName = lastName.replaceAll("[^a-zA-Z0-9/s+]+", "");

            user.setFirstName(codedFirstName);
            user.setLastName(codedLastName);
            userRepository.save(user);
            model.addAttribute("message", "User info Edited Successfully");
        return "dashboard/users";


    }




    @GetMapping("/add")
    public String suggestEvent() {
        return "dashboard/users";
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




