package com.scanapp.controllers;


import com.scanapp.services.WriteToFile;
import com.scanapp.util.FileSearchUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;


@Controller
public class FileSearchController extends FileSearchUtils {


    @RequestMapping(path="/result/{preference}", method = RequestMethod.GET)
    public String scanResult(@PathVariable("preference") String preference) throws IOException {
        System.out.println(preference);
        FileSearchUtils result = new FileSearchUtils();
        System.out.println("what is showing " + result.main().getCreditCardsFromFile(rootDirectory()));
        return "dashboard/scan";

    }


}
