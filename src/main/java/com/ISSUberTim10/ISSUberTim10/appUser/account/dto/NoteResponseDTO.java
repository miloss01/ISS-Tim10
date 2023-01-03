package com.ISSUberTim10.ISSUberTim10.appUser.account.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.util.ArrayList;
@Getter
@Setter
public class NoteResponseDTO {
    private int totalCount;
    @Valid
    private ArrayList<NoteDTO> results;

    public NoteResponseDTO() {
        this.results = new ArrayList<>();
    }

    public NoteResponseDTO(int totalCount, ArrayList<NoteDTO> results) {
        this();
        this.totalCount = totalCount;
        this.results = results;
    }


}
