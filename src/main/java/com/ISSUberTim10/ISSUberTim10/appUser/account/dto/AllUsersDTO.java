package com.ISSUberTim10.ISSUberTim10.appUser.account.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.util.ArrayList;
@Getter
@Setter
public class AllUsersDTO {
    private int totalCount;
    @Valid
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
