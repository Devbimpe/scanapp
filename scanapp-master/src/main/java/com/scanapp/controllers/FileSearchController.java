package com.scanapp.controllers;


import com.scanapp.services.WriteToFile;
import com.scanapp.util.FileSearchUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static sun.nio.cs.Surrogate.MIN;


@Controller
public class FileSearchController extends FileSearchUtils {


//    @RequestMapping(path="/result/{preference}", method = RequestMethod.GET)
//    public String scanResult(@PathVariable("preference") String preference) throws IOException {
//        System.out.println(preference);
//        FileSearchUtils result = new FileSearchUtils();
//       // System.out.println("what is showing " + result.main().getCreditCardsFromFile(rootDirectory()));
//        return "dashboard/scan";
//
//    }

//    @RequestMapping(path="/result", method = RequestMethod.GET)
//    public String scanResult() throws IOException {
//
//        FileSearchUtils result = new FileSearchUtils();
//        System.out.println("what is showing " + result.main().getCreditCardsFromFile(rootDirectory()));
//        return "dashboard/scan";
//
//    }

    @RequestMapping(path="/result")
    public String scanResult() throws IOException {

        File inFile = new File("C:/Users/SilverFox/Documents/logs/testLog151018.log");
        FileReader ins = null;
        try {
            ins = new FileReader(inFile);

            int ch;
            while ((ch = ins.read()) != -1) {
                //System.out.println((char) ch);

            }
            String data = "";
            data = new String(Files.readAllBytes(Paths.get("C:/Users/SilverFox/Documents/logs/testLog151018.log")));

            System.out.println(data);


        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                ins.close();
            } catch (Exception e) {
            }
        }
        File dir = new File("C:/Users/SilverFox/IdeaProjects/scanapp_master/scanforcard/");
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/C","start","runthis.bat" );
        pb.directory(dir);
        Process p = pb.start();
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            p.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
     catch (Exception ex) {
        System.out.println(ex);
    }
      System.out.println
              ("Scanning in progress..");

        return "dashboard/scan";

    }








    }




