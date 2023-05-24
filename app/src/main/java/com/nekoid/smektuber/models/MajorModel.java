package com.nekoid.smektuber.models;

import org.json.JSONObject;

public class MajorModel extends Models {

    public int id;

    public String majorName;

    public String majorDescription;

    public String majorLogo;
    
    public String picture1;
    
    public String picture2;
    
    public String createdAt;
    
    public String updatedAt;

    public MajorModel(int id, String majorName, String majorDescription, String majorLogo, String picture1, String picture2, String createdAt, String updatedAt) {
        this.id = id;
        this.majorName = isEmpty(majorName);
        this.majorDescription = isEmpty(majorDescription);
        this.majorLogo = isEmpty(majorLogo);
        this.picture1 = isEmpty(picture1);
        this.picture2 = isEmpty(picture2);
        this.createdAt = isEmpty(createdAt);
        this.updatedAt = isEmpty(updatedAt);
    }

    public static MajorModel fromJson(JSONObject json) {
        return new MajorModel(
                json.optInt("id"),
                json.optString("major_name"),
                json.optString("major_description"),
                json.optString("major_logo"),
                json.optString("picture_1"),
                json.optString("picture_2"),
                json.optString("created_at"),
                json.optString("updated_at")
        );
    }
}
