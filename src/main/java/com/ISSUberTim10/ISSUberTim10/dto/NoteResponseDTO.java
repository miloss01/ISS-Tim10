package com.ISSUberTim10.ISSUberTim10.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
public class NoteResponseDTO {
    private int totalCount;
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
