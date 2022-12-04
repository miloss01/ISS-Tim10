package com.ISSUberTim10.ISSUberTim10.dto;

import com.ISSUberTim10.ISSUberTim10.domain.Message;

public class MessageDTO {
    private Long receiverId;
    private String message;
    private String type;
    private Long rideId;

    public MessageDTO() {
    }

    public MessageDTO(Long receiverId, String message, String type, Long rideId) {
        super();
        this.receiverId = receiverId;
        this.message = message;
        this.type = type;
        this.rideId = rideId;
    }

    public  MessageDTO(Message message){
        this.receiverId = message.getReceiver().getId();
        this.message = message.getMessageText();
        this.type = message.getMessageType().toString();
        this.rideId = message.getRideID();
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public Long getRideId() {
        return rideId;
    }
}
