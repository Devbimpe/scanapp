package com.scanapp.services;

import com.scanapp.util.FileSearchUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
/**
 * this class might be deleted or changed later ,
 * itz just to call the file search method
 */
public class SampleService {
    @Value("${app.files.filter.extensions:docx}")
    private String commaSeparatedListOfExtensions;// all of these will be db configs , as it needs to be a runtime confiog, they cant keep stopping service to change
    @Value("${app.files.result.path:/Users/user/Documents/}")
    private String resultLogPath;



    public void runCreditCardSearch(){

        FileSearchUtils fileSearchUtils = new FileSearchUtils(commaSeparatedListOfExtensions);
        fileSearchUtils.setRecordFilePath(resultLogPath);
        fileSearchUtils.listFoundFiles();

    }



}
