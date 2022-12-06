package com.ISSUberTim10.ISSUberTim10.dto;

import java.util.ArrayList;

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

    public int getTotalCount() {
        return totalCount;
    }

    public ArrayList<MessageReceivedDTO> getMessages() {
        return messages;
    }
}
