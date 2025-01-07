package com.example.chattingapplication.model;

import com.google.firebase.Timestamp;

public class UserModel {
    private String phone;
    private String userId;
    private String password;
    private String username;
    private Timestamp createdTimestamp;

    public UserModel() {
    }

    public UserModel(String phone, String password, String username, Timestamp createdTimestamp, String userId) {
        this.phone = phone;
        this.password = password;
        this.username = username;
        this.createdTimestamp = createdTimestamp;
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
