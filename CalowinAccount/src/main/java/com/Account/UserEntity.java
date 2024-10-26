package com.Account;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "SecureUserInfo")

public class UserEntity {

    @Id
    @Column(name = "user_id", length = 8, nullable = false, unique = true)
    private String userID;

    @Column(name = "email_address", nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FriendRelationship> friends;

    // Default constructor is required by JPA
    public UserEntity() {
    }

    public UserEntity(String userID, String email, String password) {
        this.userID = userID;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<FriendRelationship> getFriends() { return friends; }
    public void setFriends(Set<FriendRelationship> friends) { this.friends = friends; }

    public String getUsername() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUsername'");
    }

}
