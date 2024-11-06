package com.Relationship.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FriendRelationship")  // Map to your database table
public class FriendRelationship {

    @Id
    private String friendUniqueId;

    private String status;

    // Add other fields and their respective getters and setters

    public String getFriendUniqueId() {
        return friendUniqueId;
    }

    public void setFriendUniqueId(String friendUniqueId) {
        this.friendUniqueId = friendUniqueId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Add other getters and setters
}

