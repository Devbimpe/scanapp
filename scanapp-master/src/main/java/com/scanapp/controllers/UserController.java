package com.scanapp.controllers;

import com.scanapp.config.GenerateSecurePassword;
import com.scanapp.models.Role;
import com.scanapp.models.User;
import com.scanapp.repositories.RoleRepository;
import com.scanapp.repositories.UserRepository;
import com.scanapp.services.EmailService;
import com.scanapp.services.UserService;
import com.sun.deploy.net.proxy.AutoProxyScript;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.util.*;

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


    @Autowired
    private RoleRepository roleRepository;

    private UserService userService;

    private EmailService emailService;

    @Autowired
    private JavaMailSender sender;


    @Autowired
    private PasswordEncoder passwordEncoder;

    //add new User
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String addUser(@RequestParam("firstName") String firstName,
                          @RequestParam("lastName") String lastName,
                          @RequestParam("email") String email ,
                          @RequestParam("password") String password,
                          @RequestParam("role") String role,
                           Model model) {


        System.out.println(firstName);
        User user = new User();


        if (emailExist(email)) {
            System.out.println("user already exists");

        } else {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));

            Role anyRole = roleRepository.findByName("ROLE_" + role);
          user.setRoles(new HashSet<>(roleRepository.findAll()));
          user.setRole(role.toUpperCase());

            userRepository.save(user);

            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            try {
                helper.setTo(email);
                System.out.println(email);
                helper.setText("This is to inform you that you have been created as a/an " + role + " on the EREVNA app " + "your password is "+ password);
                helper.setSubject("Erevna Account Creation");
            } catch (MessagingException e) {
                e.printStackTrace();
                return "Error while sending mail ..";
            }
            sender.send(message);




        }
        model.addAttribute("message", "User Created Successfully");
        return "dashboard/users";
    }


//retrieve Password
    @RequestMapping(path = "/forgotPass", method = RequestMethod.POST)
    public String retrievePassword(@RequestParam("fmail") String mail, Model model){
        User user = userRepository.findByEmail(mail);
        System.out.println(mail);
        if(emailExist(mail)) {
            String generatedPassword = RandomStringUtils.randomAlphanumeric(10);
            System.out.println(generatedPassword);
            user.setPassword(passwordEncoder.encode(generatedPassword));

            userRepository.save(user);
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            try {
                helper.setTo(mail);
                System.out.println(mail);
                helper.setText("This is to inform you that you that your new password is " + generatedPassword);
                helper.setSubject("Erevna Password Change");
            } catch (MessagingException e) {
                e.printStackTrace();
                return "Error while sending mail ..";
            }
            sender.send(message);
            model.addAttribute("fmessage", "Your Password has been changed, check mail to see new Password");

        }
        else{
            System.out.println("The email you entered cannot be found");
            model.addAttribute("message", "Your Password has been changed, check mail to see new Password");

        }
        return "login";
    }

    @GetMapping("/forgotPass")
    public ModelAndView passParametersWithModelAndView() {
        ModelAndView modelAndView = new ModelAndView("forgotPassword");
        modelAndView.addObject("message", "Baeldung");
        return modelAndView;
    }


    //update new User
    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public String updateUser(@RequestParam("firstName") String firstName,
                          @RequestParam("lastName") String lastName,
                          @RequestParam("email") String email,
                             @RequestParam("roles") String role,
                             Model model) {
        System.out.println(firstName);
            User user = userRepository.findByEmail(email);
            String codedFirstName = "" ;
            String codedLastName = "";
            codedFirstName = firstName.replaceAll("[^a-zA-Z0-9/s+]+", "");
            codedLastName = lastName.replaceAll("[^a-zA-Z0-9/s+]+", "");
            user.setFirstName(codedFirstName);
            user.setLastName(codedLastName);
            user.setRole(role);
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
    List<User> getAllUsers() {
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




