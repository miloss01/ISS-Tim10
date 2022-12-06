package com.ISSUberTim10.ISSUberTim10.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
public class MessageResponseDTO {
    private int totalCount;
    private ArrayList<MessageReceivedDTO> messages;

    public MessageResponseDTO() {
        this.messages = new ArrayList<>();
    }

    public MessageResponseDTO(int totalCount, ArrayList<MessageReceivedDTO> messages) {
        this();
        this.totalCount = totalCount;
        this.messages = messages;
    }

}
