package com.ISSUberTim10.ISSUberTim10.dto;

public class NoteDTO {
    private Long id;
    private String date;
    private String message;

    public NoteDTO() {
    }

    public NoteDTO(Long id, String date, String message) {
        this.id = id;
        this.date = date;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }
}
