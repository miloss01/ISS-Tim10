package com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.Document;

import java.util.ArrayList;
import java.util.Optional;

public interface IDocumentService {

    public ArrayList<Document> findByDriverId(Long id);
    public Document findById(Long id);
    public Document save(Document document);
    public void deleteById(Long id);

}
