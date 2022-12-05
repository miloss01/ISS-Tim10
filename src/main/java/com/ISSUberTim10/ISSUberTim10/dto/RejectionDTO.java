package com.ISSUberTim10.ISSUberTim10.dto;

public class RejectionDTO {
    private String reason;
    private String timeOfRejection;

    public RejectionDTO() {
    }

    public RejectionDTO(String reason, String timeOfRejection) {
        this.reason = reason;
        this.timeOfRejection = timeOfRejection;
    }

    public String getReason() {
        return reason;
    }

    public String getTimeOfRejection() {
        return timeOfRejection;
    }
}
