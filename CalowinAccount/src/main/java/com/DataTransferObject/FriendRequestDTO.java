package com.DataTransferObject;

public class FriendRequestDTO {
    private String senderId;
    private String senderUsername;

    // Constructor with String parameters
    public FriendRequestDTO(String senderId, String senderUsername) {
        this.senderId = senderId;
        this.senderUsername = senderUsername;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }
}
