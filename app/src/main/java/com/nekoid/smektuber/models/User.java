package com.nekoid.smektuber.models;

import java.util.Map;

public class User {
    public String username;
    public String password;
    public String token;

    User(String username, String password, String token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }
}
