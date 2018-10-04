package com.scanapp.models;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Data
@Entity
@Table(name = "scan")
public class Scan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private  Long id ;

    @Column(name = "scanName")
    private String nameOfScan;

    @Column(name = "status")
    private String status;

    @Column(name = "dateCreated")
    private String dateCreated;

    public String[] getMaxfiles() {
        return maxfiles;
    }

    public void setMaxfiles(String[] maxfiles) {
        this.maxfiles = maxfiles;
    }

    @Column(name = "createdBy")
    private String createdBy;


    public String getCustomRegex() {
        return customRegex;
    }

    public void setCustomRegex(String customRegex) {
        this.customRegex = customRegex;
    }

    @Column(name = "folders")
    private String[] folders;

    public String[] getFolders() {
        return folders;
    }

    public void setFolders(String[] folders) {
        this.folders = folders;
    }

    @Column(name = "maxfiles")
    private String[] maxfiles;

    @Column(name = "customRegex")
    private String customRegex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOfScan() {
        return nameOfScan;
    }

    public void setNameOfScan(String nameOfScan) {
        this.nameOfScan = nameOfScan;
    }

    public String getStatus() {
        return status;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


    public String[] getPreferences() {
        return preferences;
    }

    public void setPreferences(String[] preferences) {
        this.preferences = preferences;
    }

    private String[] preferences;
}
