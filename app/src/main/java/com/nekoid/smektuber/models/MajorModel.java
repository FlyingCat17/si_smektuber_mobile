package com.nekoid.smektuber.models;

import org.json.JSONObject;

public class MajorModel extends Models {

    public int id;

    public String name;

    public String description;

    public String photo;

    public String facility;

    public int achievementId;

    public String createdAt;

    public String updatedAt;

    public MajorModel(int id, String name, String description, String photo, String facility, int achievementId, String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.facility = facility;
        this.achievementId = achievementId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static MajorModel fromJson(JSONObject json) {
        return new MajorModel(
                json.optInt("id"),
                json.optString("name"),
                json.optString("description"),
                json.optString("photo"),
                json.optString("facility"),
                json.optInt("achievement_id"),
                json.optString("created_at"),
                json.optString("updated_at")
        );
    }
}
