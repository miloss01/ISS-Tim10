package com.ISSUberTim10.ISSUberTim10.appUser.account.service.impl;

import com.ISSUberTim10.ISSUberTim10.appUser.account.Note;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.NoteDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.NoteResponseDTO;
import com.ISSUberTim10.ISSUberTim10.appUser.account.repository.NoteRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.INoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService implements INoteService {

    @Autowired
    NoteRepository noteRepository;

    @Override
    public Note save(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public ResponseEntity<NoteResponseDTO> getAll(int id) {

        ArrayList<NoteDTO> results = new ArrayList<>();
        List<Note> notes = noteRepository.findAll();
        for (Note note : notes) {
            if (note.getAppUser().getId() == id) results.add(new NoteDTO(note));
        }

        NoteResponseDTO noteResponseDTO = new NoteResponseDTO();
        noteResponseDTO.setResults(results);
        noteResponseDTO.setTotalCount(noteResponseDTO.getResults().size());
        return new ResponseEntity<>(noteResponseDTO, HttpStatus.OK);
    };

}
