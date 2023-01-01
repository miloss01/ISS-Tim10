package com.ISSUberTim10.ISSUberTim10.appUser.driver.service.impl;

import com.ISSUberTim10.ISSUberTim10.appUser.driver.Document;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.repository.DocumentRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.driver.service.interfaces.IDocumentService;
import com.ISSUberTim10.ISSUberTim10.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class DocumentService implements IDocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public ArrayList<Document> findByDriverId(Long id) {
        return documentRepository.findByDriverId(id);
    }

    @Override
    public Document findById(Long id) {
        Optional<Document> document = documentRepository.findById(id);

        if (!document.isPresent())
            throw new CustomException("Document does not exist!", HttpStatus.NOT_FOUND);

        return document.get();
    }

    @Override
    public Document save(Document document) {
        return documentRepository.save(document);
    }

    @Override
    public void deleteById(Long id) {
        Document document = findById(id);
        documentRepository.deleteById(document.getId());
    }
}
