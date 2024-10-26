package com.Account;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Friend Relationship")
public class FriendRelationship {

    @Id
    @Column(name = "Unique ID", length = 200, nullable = false)
    private String uniqueId;

    @Column(name = "Friend Unique ID", length = 200, nullable = false)
    private String friendUniqueId;

    @Column(name = "Friended On", nullable = false)
    private LocalDateTime friendedOn;

    @Column(name = "status", length = 50)
    private UserStatusEnum status;

    // Default constructor
    public FriendRelationship() {}

    // Constructor with fields
    public FriendRelationship(String uniqueId, String friendUniqueId, LocalDateTime friendedOn, UserStatusEnum status) {
        this.uniqueId = uniqueId;
        this.friendUniqueId = friendUniqueId;
        this.friendedOn = friendedOn;
        this.status = status;
    }

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

    public UserStatusEnum getStatus() {
        return status;
    }

    public void setStatus(UserStatusEnum stranger) {
        this.status = stranger;
    }

    // Override toString for easy debugging
    @Override
    public String toString() {
        return "FriendRelationship{" +
                "uniqueId='" + uniqueId + '\'' +
                ", friendUniqueId='" + friendUniqueId + '\'' +
                ", friendedOn=" + friendedOn +
                ", status='" + status + '\'' +
                '}';
    }
}
