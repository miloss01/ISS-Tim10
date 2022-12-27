package com.ISSUberTim10.ISSUberTim10.appUser.account.service.impl;

import com.ISSUberTim10.ISSUberTim10.appUser.account.Note;
import com.ISSUberTim10.ISSUberTim10.appUser.account.repository.NoteRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.INoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService implements INoteService {

    @Autowired
    NoteRepository noteRepository;

    @Override
    public Note save(Note note) {
        return noteRepository.save(note);
    }
}
