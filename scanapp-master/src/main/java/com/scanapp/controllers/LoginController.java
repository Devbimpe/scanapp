package com.scanapp.controllers;

import com.scanapp.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.security.Principal;
import java.util.ArrayList;

/**
 * Created by USER on 8/15/2018.
 */
@Controller
public class LoginController {
    @RequestMapping("/login")
    String login()
    {

        return "login";
    }
    @RequestMapping("/forgotPassword")
    String forgotPassword(){
        return "forgotPassword";
    }

    @RequestMapping("/index")
    String dashboard()
    {
        return "dashboard/dashboard";
    }

    @RequestMapping("/users")
    String users(){

        return "dashboard/users";
    }

    @GetMapping("/users")
    public ModelAndView displayUserOnUserPage(){
        ModelAndView modelAndView = new ModelAndView("dashboard/users");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        modelAndView.addObject("username", username);
        return modelAndView;
    }

    @RequestMapping("/scan")
    String scan(){
        return "dashboard/scan";
    }

    @GetMapping("/scan")
    public ModelAndView displayUserOnScanPage(){
        ModelAndView modelAndView = new ModelAndView("dashboard/scan");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        modelAndView.addObject("username", username);
        return modelAndView;
    }


    @RequestMapping("/licence")
    String licence(){
        return "dashboard/licence";
    }

    @GetMapping("/licence")
    public ModelAndView displayUserOnLicencePage(){
        ModelAndView modelAndView = new ModelAndView("dashboard/licence");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        modelAndView.addObject("username", username);
        return modelAndView;
    }

    @RequestMapping("/details")
    String details(){
        return "dashboard/details";
    }










}