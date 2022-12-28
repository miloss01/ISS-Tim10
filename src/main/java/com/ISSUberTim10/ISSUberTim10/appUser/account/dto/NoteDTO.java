package com.ISSUberTim10.ISSUberTim10.appUser.account.dto;

import com.ISSUberTim10.ISSUberTim10.appUser.account.Note;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.ISSUberTim10.ISSUberTim10.helper.StringFormatting.simpleDateFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {
    private Long id;
    private String date;
    private String message;

    public NoteDTO(Note note) {
        this.id = note.getId();
        this.date = simpleDateFormat.format(note.getNoteDate());
        this.message = note.getMessage();
    }
}
