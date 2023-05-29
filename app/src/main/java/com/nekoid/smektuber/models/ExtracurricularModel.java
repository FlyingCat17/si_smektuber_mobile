package com.nekoid.smektuber.models;

import org.json.JSONObject;

public class ExtracurricularModel extends Models {

    public int id;

    public String name;

    public String description;
    public String logo;

    public String photo;

    public String createdAt;

    public String updatedAt;

    public ExtracurricularModel(int id, String name, String description, String logo,String photo, String createdAt, String updatedAt) {
        this.id = id;
        this.name = isEmpty(name);
        this.description = isEmpty(description);
        this.logo = isEmpty( logo );
        this.photo = isEmpty(photo);
        this.createdAt = isEmpty(createdAt);
        this.updatedAt = isEmpty(updatedAt);
    }

    public static ExtracurricularModel fromJson(JSONObject json) {
        return new ExtracurricularModel(
                json.optInt("id"),
                json.optString("extracurricular_name"),
                json.optString("extracurricular_description"),
                json.optString("extracurricular_logo"),
                json.optString("extracurricular_photo_1"),
                json.optString("created_at"),
                json.optString("updated_at")
        );
    }
}
