package com.ISSUberTim10.ISSUberTim10.dto;

public class UserDTO {
    private Long id;
    private String email;
    private String type;

    public UserDTO() {
    }

    public UserDTO(Long id, String email, String type) {
        this.id = id;
        this.email = email;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }
}
