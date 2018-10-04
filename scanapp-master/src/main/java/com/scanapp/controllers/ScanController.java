package com.scanapp.controllers;


import com.scanapp.models.Scan;
import com.scanapp.models.User;
import com.scanapp.repositories.ScanRepository;
import com.scanapp.services.ScanService;
import org.apache.camel.language.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ScanController {


    @Autowired
    private ScanRepository scanRepository;

    private ScanService scanService;

    //add New Scan
    @RequestMapping(path = "/addScan", method = RequestMethod.POST)
    public String addScan(@RequestParam("scanName") String scanName,
                          @RequestParam("pref") String[] pref,
                          @RequestParam("foldr") String[] foldr,
                          @RequestParam("maxfile") String[] maxfile,
                          Model model){
        String user = "Busayo";
        Scan scan = new Scan();
        scan.setCreatedBy(user);

        scan.setNameOfScan(scanName);

        LocalDate localDate = LocalDate.now();
        String currentDate = DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate);
        System.out.println(currentDate);
        scan.setDateCreated(currentDate);
        scan.setPreferences(pref);
        scan.setStatus("Not Scanned");
        scan.setFolders(foldr);
        scan.setMaxfiles(maxfile);


//        ArrayList<String> arrList = new ArrayList<String>();
//        arrList.addAll(Arrays.asList( csv,txt,doc,docx,pdf,ppt,pptx,xls,xlsm,notes));

        //scan.setMaxfiles(option1);

        scanRepository.save(scan);
        model.addAttribute("message", "Scan created");
        return "dashboard/scan";
    }


    //get All Users
    @GetMapping(path = "/allscans")
    public @ResponseBody
    Iterable<Scan> getAllScans() {
        return scanRepository.findAll();

    }

}
