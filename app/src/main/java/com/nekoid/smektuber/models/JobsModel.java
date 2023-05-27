package com.nekoid.smektuber.models;

import org.json.JSONException;
import org.json.JSONObject;

public class JobsModel extends Models{
    public int id;

    public String title;

    public String slug;

    public String description;

    public String thumbnail;

    public String createdAt;

    public String updatedAt;


    public JobsModel(int id, String title, String slug, String description, String thumbnail, String created_at, String updated_at) {
        this.id = id;
        this.slug = isEmpty(slug);
        this.title = isEmpty(title);
        this.description = isEmpty(description);
        this.thumbnail = isEmpty(thumbnail);
        this.createdAt = isEmpty(created_at);
        this.updatedAt = isEmpty(updated_at);
    }

    public static JobsModel fromJson(JSONObject json) throws JSONException {
        return new JobsModel(
                json.getInt("id"),
                json.getString("title"),
                json.getString("slug"),
                json.getString("description"),
                json.getString("thumbnail"),
                json.getString("created_at"),
                json.getString("updated_at")
        );
    }
}
