package com.scanapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by USER on 8/15/2018.
 */
@Controller
public class LoginController {
    @RequestMapping("/login")
    String login(){
        return "login";
    }
    @RequestMapping("/index")
    String dashboard(){
        return "dashboard/dashboard";
    }
    @RequestMapping("/users")
    String users(){
        return "dashboard/users";
    }
    @RequestMapping("/scan")
    String scan(){
        return "dashboard/scan";
    }
    @RequestMapping("/licence")
    String licence(){
        return "dashboard/licence";
    }


    //shouldn't be here but for the main time
//    @PostMapping("/saveUser")
//    public String saveUser(UserService user, Model model) {
//        model.addAttribute("user", user);
//        return "dashboard/users";
//    }
}