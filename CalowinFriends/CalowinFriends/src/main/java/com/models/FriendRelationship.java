package com.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// import com.ENUM.FriendRequestStatus;

@Entity
@Table(name = "FriendRelationship", schema = "dbo")
public class FriendRelationship {

    @Id
    @Column(name = "Unique_ID")
    private String uniqueId;

    @Column(name = "Friend_Unique_ID")
    private String friendUniqueId;

    @Column(name = "[Friended On]")
    private LocalDateTime friendedOn;

    @Column(name = "status")
    private String status; // e.g., "PENDING", "ACCEPTED", "DECLINED"

    // Getters and Setters

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getFriendUniqueId() {
        return friendUniqueId;
    }

    public void setFriendUniqueId(String friendUniqueId) {
        this.friendUniqueId = friendUniqueId;
    }

    public LocalDateTime getFriendedOn() {
        return friendedOn;
    }

    public void setFriendedOn(LocalDateTime friendedOn) {
        this.friendedOn = friendedOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}