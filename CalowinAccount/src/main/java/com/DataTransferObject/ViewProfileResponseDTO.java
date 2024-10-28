package com.DataTransferObject;

import com.Account.Entities.FriendStatus;

public class ViewProfileResponseDTO {
    
    private String userID;
    private String name;
    private String bio;
    private FriendStatus friendStatus;

    public ViewProfileResponseDTO(String userID, String name, String bio, FriendStatus friendStatus) {
        this.userID = userID;
        this.name = name;
        this.bio = bio;
        this.friendStatus = friendStatus;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return this.bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public FriendStatus getFriendStatus() {
        return this.friendStatus;
    }

    public void setFriendStatus(FriendStatus friendStatus) {
        this.friendStatus = friendStatus;
    }

}
