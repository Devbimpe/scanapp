package com.bimdev.scanappservice;

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
