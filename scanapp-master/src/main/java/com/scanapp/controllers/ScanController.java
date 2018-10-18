package com.scanapp.controllers;


import com.scanapp.config.MyUserPrincipal;
import com.scanapp.models.Scan;
import com.scanapp.models.User;
import com.scanapp.repositories.ScanRepository;
import com.scanapp.services.ScanService;
import org.apache.camel.language.Bean;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


    //allscans that have been carried out
    @GetMapping("/details")
    public ModelAndView displayScanResult() throws IOException {
        ModelAndView modelAndView = new ModelAndView("dashboard/details");
        //get number of files in the log folder
        File directory=new File("C:/Users/SilverFox/Documents/logs");
        int fileCount=directory.list().length;
        System.out.println("File Count:"+fileCount);

        //get the names of files in the log folder
        File[] listOfFiles = directory.listFiles();
        StringBuilder sbStr = new StringBuilder();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {

                System.out.println(listOfFiles[i].getName());
                sbStr.append(listOfFiles[i].getName());

            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }

            //files names are gotten and it ends here



            //content of file


            BufferedReader in = null;
            try {
                in = new BufferedReader(new FileReader(listOfFiles[i].getAbsoluteFile()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String line;
            while((line = in.readLine()) != null)
            {
                System.out.println("stuff" +line);
            }
            in.close();
            //end of fetching details of log file



            //get the time the scans were actually created and last modified
            BasicFileAttributes attr = null;
            try {
                attr = Files.readAttributes(listOfFiles[i].toPath(), BasicFileAttributes.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("creationTime: " + attr.creationTime());
            System.out.println("modifiedTime: " + attr.lastModifiedTime());


            //ends here


        }



        //JSON for easy frontend thingy starts here
        JSONArray fileNameArray = new JSONArray();
        JSONArray fileCreationTimeArray = new JSONArray();
        JSONArray fileModifiedTimeArray = new JSONArray();

        for (int i = 0; i < listOfFiles.length; i++) {
            BasicFileAttributes attr = null;
            try {
                attr = Files.readAttributes(listOfFiles[i].toPath(), BasicFileAttributes.class);
            } catch (IOException e) {
                e.printStackTrace();
            }


            //dateFormatting
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = inputFormat.parse(attr.creationTime().toString());
                date2 = inputFormat.parse(attr.lastModifiedTime().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String formattedCreationTimeDate = outputFormat.format(date1);
            //System.out.println("formattedCreationTimeDate= " + formattedCreationTimeDate);
            String formattedModifiedDate = outputFormat.format(date2);
            //System.out.println("formattedModifiedTimeDate= " + formattedModifiedDate);

            //dateFormatting ends


            fileNameArray.put(listOfFiles[i].getName());
            fileCreationTimeArray.put( formattedCreationTimeDate);
            fileModifiedTimeArray.put( formattedModifiedDate);

        }
        //System.out.println("MyJsonArray");
        //System.out.println(fileNameArray);


        //JSON thingy ends here

       //where the JSON interacts with the view
        modelAndView.addObject("fileNames", fileNameArray);
        modelAndView.addObject("fileCreationTimes", fileCreationTimeArray);
        modelAndView.addObject("fileModifiedTimes", fileModifiedTimeArray);


        return modelAndView;
    }
    @GetMapping("/index")
    public ModelAndView displayScanCount(){
        ModelAndView modelAndView = new ModelAndView("dashboard/dashboard");
        //get number of files in the log folder
        File directory=new File("C:/Users/SilverFox/Documents/logs");
        int fileCount=directory.list().length;
        System.out.println("File Count:"+fileCount);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println(username);
        modelAndView.addObject("fileCount", fileCount);
        modelAndView.addObject("username", username);

        return modelAndView;
    }


}
