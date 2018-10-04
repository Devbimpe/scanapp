//package com.scanapp.router;
//import com.scanapp.forms.Templates;
//import com.scanapp.models.User;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/pages/users")
//public class Userroutes extends RouteHelperFunctions{
//
//
//    @GetMapping("/")
//    public String display(@PathVariable Long id, Model model){
//        List<User> user = userRepository.getAllUsers(id);
//        Templates information = new Templates();
//
//
//        information.data2 = user.get(0).getEmail();
//        information.data3 = user.get(0).getFirstName();
//        information.data4 = user.get(0).getLastName();
//
//        return "dashboard/users";
//    }
//}
