package com.nekoid.smektuber.models;

import androidx.annotation.NonNull;

import org.json.JSONObject;

public class UserModel extends Models {
    public String name;
    public String email;
    public String role;
    public String username;

    public boolean emailVerified;

    public UserModel(String name, String email, String role, String username, boolean emailVerified) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.username = username;
        this.emailVerified = emailVerified;
    }

    public static UserModel fromJson(JSONObject json) {
        try {
            return new UserModel(
                    json.getString("name"),
                    json.getString("email"),
                    json.getString("role"),
                    json.getString("username"),
                    json.getBoolean("email_verified_at")
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toJson() {
        String json = "{";
        json += "\"name\":\"" + name + "\",";
        json += "\"email\":\"" + email + "\",";
        json += "\"role\":\"" + role + "\",";
        json += "\"username\":\"" + username + "\"";
        json += "}";
        return json;
    }

    @NonNull
    @Override
    public String toString() {
        String user = "name:";
        user += name;
        user += ", email:";
        user += email;
        user += ", role:";
        user += role;
        user += ", username:";
        user += username;
        return user;
    }
}