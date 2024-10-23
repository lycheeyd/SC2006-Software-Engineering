package com.Account;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "UserInfo")

public class Profile {

    @Id
    @Column(name = "user_id", length = 8, nullable = false, unique = true)
    private String userID;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "weight", nullable = false)
    private float weight;

    @Column(name = "bio", nullable = true)
    private String bio;

    // Default constructor is required by JPA
    public Profile() {
    }

    public Profile(String userID, String name, float weight) {
        this.userID = userID;
        this.name = name;
        this.weight = weight;
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
