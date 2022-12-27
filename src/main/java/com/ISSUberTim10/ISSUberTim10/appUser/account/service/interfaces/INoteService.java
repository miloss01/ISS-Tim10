package com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.appUser.account.Note;
import com.ISSUberTim10.ISSUberTim10.appUser.account.dto.NoteResponseDTO;
import org.springframework.http.ResponseEntity;

public interface INoteService {

    public Note save(Note note);

    ResponseEntity<NoteResponseDTO> getAll(int id);
}

