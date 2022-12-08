package com.ISSUberTim10.ISSUberTim10.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
public class AllUsersDTO {
    private int totalCount;
    private ArrayList<UserExpandedDTO> results;

    public AllUsersDTO() {
        this.results = new ArrayList<>();
    }

    public AllUsersDTO(int totalCount, ArrayList<UserExpandedDTO> results) {
        this();
        this.totalCount = totalCount;
        this.results = results;
    }

}
