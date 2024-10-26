package com.Account;
import jakarta.persistence.*;

@Entity
@Table(name = "UserInfo")
public class UserEntityy {
    @Id
    @Column(name = "user_id", length = 8, nullable = false, unique = true)
    private String userId;

    @Column(name = "name", length = 16, nullable = false)
    private String name;

    @Column(name = "weight", nullable = false)
    private float weight;

    @Column(name = "bio", length = 250)
    private String bio;

    public UserEntityy() {}

    // Constructor with fields
    public UserEntityy(String userId, String name, float weight, String bio) {
        this.userId = userId;
        this.name = name;
        this.weight = weight;
        this.bio = bio;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    // Override toString for easy debugging
    @Override
    public String toString() {
        return "UserEntity{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", bio='" + bio + '\'' +
                '}';
    }
}
