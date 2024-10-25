package com.DataTransferObject;

import com.Account.UserStatusEnum;

public class ViewProfileResponseDTO {
    
    private String userID;
    private String name;
    private String bio;
    private UserStatusEnum userStatus;

    public ViewProfileResponseDTO(String userID, String name, String bio, UserStatusEnum userStatus) {
        this.userID = userID;
        this.name = name;
        this.bio = bio;
        this.userStatus = userStatus;
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

    public UserStatusEnum getUserStatus() {
        return this.userStatus;
    }

    public void setUserStatus(UserStatusEnum userStatus) {
        this.userStatus = userStatus;
    }

}
