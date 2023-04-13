package com.nekoid.smektuber.models;

public class User extends Models {
    public String username;
    public String password;
    public String token;

    public User(String username, String password, String token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }
}
