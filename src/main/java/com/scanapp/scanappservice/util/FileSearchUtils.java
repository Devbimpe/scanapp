package com.scanapp.scanappservice.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Slf4j
public class FileSearchUtils {

    private String commaSeparatedListOfExtensions;

    public FileSearchUtils(String commaSeparatedListOfExtensions) {
        this.commaSeparatedListOfExtensions = commaSeparatedListOfExtensions;
    }

    private List<String> result = new ArrayList<String>();

    /**
     * @return root directory file path
     */
    private  String rootDirectory() {
        return File.listRoots()[0].getAbsolutePath();
    }


    private void searchDirectory(File directory) {


        if (directory.isDirectory()) {
            search(directory);
        } else {
            System.out.println(directory.getAbsoluteFile() + " is not a directory!");
        }

    }

    private void search(File file) {

        if (file.isDirectory()) {
            System.out.println("Searching directory ... " + file.getAbsoluteFile());

            //do you have permission to read this directory?
            if (file.canRead()) {
                for (File temp : Objects.requireNonNull(file.listFiles())) {
                    if (temp.isDirectory()) {
                        search(temp);
                    } else {
                        if (accept(temp)) {
                            result.add(temp.getAbsoluteFile().toString());
                        }

                    }
                }

            } else {
                System.out.println(file.getAbsoluteFile() + "Permission Denied");
            }
        }

    }


    private boolean accept(File file) {
        String fileName = file.getAbsolutePath();
        Set<String> fileExtensionList = StringUtils.commaDelimitedListToSet(commaSeparatedListOfExtensions);
        boolean shouldAccept = fileExtensionList.stream().anyMatch(i -> fileName.endsWith(i));
        return shouldAccept;

    }


    public  List<String> listFoundFiles(){
        Path rootDir = Paths.get(rootDirectory());
        searchDirectory(rootDir.toFile());

        return result;
    }

//    public static void main(String ...args){
//        FileSearchUtils fileSearchUtils=new FileSearchUtils(".doc,.txt");
//        fileSearchUtils.accept(new File("Erevna Doc.docx"));
//    }

}
