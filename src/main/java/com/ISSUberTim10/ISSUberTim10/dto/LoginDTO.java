package com.ISSUberTim10.ISSUberTim10.dto;

public class LoginDTO {
    private String email;
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
