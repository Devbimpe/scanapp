package com.scanapp.repositories;

import com.scanapp.models.Scan;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface ScanRepository extends CrudRepository<Scan, Long> {
    Scan findByDateCreated(Date date);
    Scan findByPreferences(String preference);
}
