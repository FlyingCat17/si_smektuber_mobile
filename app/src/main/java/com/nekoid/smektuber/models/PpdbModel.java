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
//    public String deletedAt;
    public String createdAt;
    public String updatedAt;
//    String deletedAt,
    public PpdbModel(int id, String schoolYear, String description, String poster, String dateStart, String dateEnd, String status,  String createdAt, String updatedAt) {
        this.id = id;
        this.schoolYear = isEmpty( schoolYear );
        this.description = isEmpty( description );
        this.poster = isEmpty( poster );
        this.dateStart = isEmpty( dateStart );
        this.dateEnd = isEmpty( dateEnd );
        this.status = isEmpty( status );
//        this.deletedAt = deletedAt;
        this.createdAt = isEmpty( createdAt );
        this.updatedAt = isEmpty( updatedAt );
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
//            json.getString("deleted_at"),
            json.getString("created_at"),
            json.getString("updated_at")
        );
    }
}
