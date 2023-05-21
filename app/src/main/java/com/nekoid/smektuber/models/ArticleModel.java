package com.nekoid.smektuber.models;

import org.json.JSONException;
import org.json.JSONObject;

public class ArticleModel extends Models {

    public int id;

    public String title;

    public String slug;

    public String description;

    public String thumbnail;

    public String createdAt;

    public String updatedAt;


    public ArticleModel(int id, String title, String slug, String description, String thumbnail, String created_at, String updated_at) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.description = description;
        this.thumbnail = thumbnail;
        this.createdAt = created_at;
        this.updatedAt = updated_at;
    }

    public static ArticleModel fromJson(JSONObject json) throws JSONException {
        return new ArticleModel(
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
