package com.nekoid.smektuber.models;

import androidx.annotation.NonNull;

import org.json.JSONObject;

public class UserModel extends Models {
    public String name;

    public String email;

    public String role;

    public String username;

    public String avatar;

    public boolean emailVerified;

    public UserModel(String name, String email, String role, String avatar, String username, boolean emailVerified) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.avatar = avatar;
        this.username = username;
        this.emailVerified = emailVerified;
    }

    public static UserModel fromJson(JSONObject json) {
        try {
            return new UserModel(
                    json.getString("name"),
                    json.getString("email"),
                    json.getString("role"),
                    json.getString("avatar"),
                    json.getString("username"),
                    json.getBoolean("email_verified_at")
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}