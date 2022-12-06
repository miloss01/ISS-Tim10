package com.ISSUberTim10.ISSUberTim10.dto;

import java.util.ArrayList;

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

    public int getTotalCount() {
        return totalCount;
    }

    public ArrayList<UserExpandedDTO> getResults() {
        return results;
    }
}
