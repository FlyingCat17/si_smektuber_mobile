package com.nekoid.smektuber.models;

import org.json.JSONException;
import org.json.JSONObject;

public class PpdbModel extends  Models{
    public int id;
    public String schoolYear;
    public String description;
    public String poster;
    public String dateStart;
    public String dateEnd;
    public String status;
    public String deletedAt;
    public String createdAt;
    public String updatedAt;

    public PpdbModel(int id, String schoolYear, String description, String poster, String dateStart, String dateEnd, String status, String deletedAt, String createdAt, String updatedAt) {
        this.id = id;
        this.schoolYear = schoolYear;
        this.description = description;
        this.poster = poster;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.status = status;
        this.deletedAt = deletedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;   
    }

    public static PpdbModel fromJson(JSONObject json) throws JSONException {
        return new PpdbModel(
            json.getInt("id"),
            json.getString("school_year"),
            json.getString("description"),
            json.getString("poster"),
            json.getString("date_start"),
            json.getString("date_end"),
            json.getString("status"),
            json.getString("deleted_at"),
            json.getString("created_at"),
            json.getString("updated_at")
        );
    }
}
