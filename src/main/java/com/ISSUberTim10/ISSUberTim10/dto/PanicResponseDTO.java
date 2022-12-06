package com.ISSUberTim10.ISSUberTim10.dto;

public class PanicResponseDTO {
    private Long id;
    private Long userId;
    private Long rideId;
    private String time;
    private String reason;

    public PanicResponseDTO() {
    }

    public PanicResponseDTO(Long id, Long userId, Long rideId, String time, String reason) {
        this.id = id;
        this.userId = userId;
        this.rideId = rideId;
        this.time = time;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getRideId() {
        return rideId;
    }

    public String getTime() {
        return time;
    }

    public String getReason() {
        return reason;
    }
}
