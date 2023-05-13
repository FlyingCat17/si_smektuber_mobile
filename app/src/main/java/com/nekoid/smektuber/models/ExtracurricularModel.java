package com.nekoid.smektuber.models;

import org.json.JSONObject;

public class ExtracurricularModel extends Models {

    public int id;

    public String name;

    public String description;

    public String photo;

    public int achievementId;

    public String createdAt;

    public String updatedAt;

    public ExtracurricularModel(int id, String name, String description, String photo, int achievementId, String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.achievementId = achievementId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ExtracurricularModel fromJson(JSONObject json) {
        return new ExtracurricularModel(
                json.optInt("id"),
                json.optString("name"),
                json.optString("description"),
                json.optString("photo"),
                json.optInt("achievement_id"),
                json.optString("created_at"),
                json.optString("updated_at")
        );
    }
}
