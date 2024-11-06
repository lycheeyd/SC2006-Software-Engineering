package com.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.ENUM.FriendRequestStatus;

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
    private FriendRequestStatus status; // e.g., "PENDING", "ACCEPTED", "DECLINED"

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

    public FriendRequestStatus getStatus() {
        return status;
    }

    public void setStatus(FriendRequestStatus status) {
        this.status = status;
    }
}