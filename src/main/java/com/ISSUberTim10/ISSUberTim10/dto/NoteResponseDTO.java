package com.ISSUberTim10.ISSUberTim10.dto;

import java.util.ArrayList;

public class NoteResponseDTO {
    private int totalCount;
    private ArrayList<NoteDTO> results;

    public NoteResponseDTO() {
    }

    public NoteResponseDTO(int totalCount, ArrayList<NoteDTO> results) {
        this.totalCount = totalCount;
        this.results = results;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public ArrayList<NoteDTO> getResults() {
        return results;
    }
}
