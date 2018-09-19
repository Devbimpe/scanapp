package com.scanapp.controllers;


import com.scanapp.util.FileSearchUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;


@Controller
public class FileSearchController extends FileSearchUtils {


    @RequestMapping("/result")
    public String scanResult() throws IOException {
        FileSearchUtils result = new FileSearchUtils();
        return "dashboard/scan";

    }


}
