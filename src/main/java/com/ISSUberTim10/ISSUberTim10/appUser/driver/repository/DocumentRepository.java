package com.ISSUberTim10.ISSUberTim10.appUser.driver.repository;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.Document;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.dto.DocumentDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    ArrayList<Document> findByDriverId(Long id);
}
