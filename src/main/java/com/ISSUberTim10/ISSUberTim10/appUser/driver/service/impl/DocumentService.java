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
        if (!document.getImage().startsWith("data:image")){
            throw new CustomException("File is not an image!", HttpStatus.BAD_REQUEST);
        }
        double stringLength = document.getImage().length() - "data:image/pgn:base64".length();
        double sizeInb = 4 * Math.ceil((stringLength/3)) * 0.5624896334383812;
        double sizeInMb = (sizeInb/1024)/1024;
        if (sizeInMb > 1048576*5){
            //e sad matematika i char je 1b, 1 mb je 1024kb, a kb je 1024kb ynaci 1 mb je 1024*1024kb
            throw new CustomException("File is bigger than 5mb!", HttpStatus.BAD_REQUEST);
        }
        return documentRepository.save(document);
    }

    @Override
    public void deleteById(Long id) {
        Document document = findById(id);
        documentRepository.deleteById(document.getId());
    }
}
