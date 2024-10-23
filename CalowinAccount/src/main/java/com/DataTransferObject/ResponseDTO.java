package com.DataTransferObject;

public class ResponseDTO {
    
    private String userID;
    private String email;
    private String name;
    private float weight;
    private String bio;

    public ResponseDTO(String userID, String email, String name, float weight, String bio) {
        this.userID = userID;
        this.email = email;
        this.name = name;
        this.weight = weight;
        this.bio = bio;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWeight() {
        return this.weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getBio() {
        return this.bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

}
