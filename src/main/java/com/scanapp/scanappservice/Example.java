package com.scanapp.scanappservice;

import com.scanapp.scanappservice.util.FileSearchUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;


public class Example {


    public static void main(String ...args){


//System.out.print(System.getProperty("SystemDrive"));
//System.out.print( File.listRoots()[0].getAbsolutePath());
System.out.println( File.listRoots().toString());
for(File e : File.listRoots()){
    System.out.println(e.getAbsolutePath());
}

    }
}
