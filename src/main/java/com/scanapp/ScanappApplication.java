package com.scanapp;


import com.scanapp.services.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScanappApplication implements CommandLineRunner {


    private SampleService sampleService;

    @Autowired
    public void setSampleService(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ScanappApplication.class, args);
    }

    @Override
    public void run(String... args) {
        sampleService.runCreditCardSearch();
    }
}
