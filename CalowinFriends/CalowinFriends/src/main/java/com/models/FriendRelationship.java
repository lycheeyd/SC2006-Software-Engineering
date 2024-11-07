package com.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// import com.ENUM.FriendRequestStatus;



@Entity
@Table(name = "FriendRelationship", schema = "dbo")
public class FriendRelationship {
    @Id
    @Column(name = "Unique_ID")
    private String uniqueId;

    @ManyToOne
    @JoinColumn(name = "Unique_ID", referencedColumnName = "user_id", insertable = false, updatable = false)
    private UserInfo user;

    @ManyToOne
    @JoinColumn(name = "Friend_Unique_ID", referencedColumnName = "user_id", insertable = false, updatable = false)
    private UserInfo friendUser;

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

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public UserInfo getFriendUser() {
        return friendUser;
    }

    public void setFriendUser(UserInfo friendUser) {
        this.friendUser = friendUser;
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
