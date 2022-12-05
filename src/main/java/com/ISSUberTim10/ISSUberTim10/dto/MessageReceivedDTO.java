package com.ISSUberTim10.ISSUberTim10.dto;

import com.ISSUberTim10.ISSUberTim10.domain.Message;

public class MessageReceivedDTO {
    private Long id;
    private String timeOfSending;
    private Long senderId;
    private Long receiverId;
    private String message;
    private String type;
    private Long rideId;

    public MessageReceivedDTO() {
    }

    public MessageReceivedDTO(Long id, String timeOfSending, Long senderId, Long receiverId, String message, String type, Long rideId) {
        this.id = id;
        this.timeOfSending = timeOfSending;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.type = type;
        this.rideId = rideId;
    }

    public MessageReceivedDTO(Message message){
        //this.receiverId = message.getReceiver().getId();
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

    public Long getId() {
        return id;
    }

    public String getTimeOfSending() {
        return timeOfSending;
    }

    public Long getSenderId() {
        return senderId;
    }
}
