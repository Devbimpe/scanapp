package com.scanapp.controllers;

import com.scanapp.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

}