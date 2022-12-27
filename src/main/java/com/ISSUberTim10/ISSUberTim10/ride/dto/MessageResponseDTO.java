package com.ISSUberTim10.ISSUberTim10.ride.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
public class MessageResponseDTO {
    private int totalCount;
    private ArrayList<MessageReceivedDTO> results;

    public MessageResponseDTO() {
        this.results = new ArrayList<>();
    }

    public MessageResponseDTO(int totalCount, ArrayList<MessageReceivedDTO> results) {
        this();
        this.totalCount = totalCount;
        this.results = results;
    }

}
