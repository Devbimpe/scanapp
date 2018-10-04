package com.scanapp.services;


import com.scanapp.models.Scan;
import com.scanapp.repositories.ScanRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ScanService {
    private ScanRepository scanRepository;

    public ScanService(ScanRepository scanRepository){
        this.scanRepository = scanRepository;
    }

    public Scan findByDateCreated(Date date){
        return scanRepository.findByDateCreated(date);
    }

    public Scan findByPreferences(String preference){
        return scanRepository.findByPreferences(preference);
    }

    public void saveScan(Scan scan){
        scanRepository.save(scan);
    }
}
