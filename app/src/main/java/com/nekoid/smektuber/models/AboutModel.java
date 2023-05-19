package com.nekoid.smektuber.models;

import org.json.JSONException;
import org.json.JSONObject;

public class AboutModel extends Models {

    public int id;

    public String schoolName;

    public String schoolAccreditation;

    public String schoolHistory;

    public String schoolSlogan;

    public String schoolVision;

    public String schoolMission;

    public String schoolAddress;

    public String schoolPhone;

    public String schoolEmail;

    public String schoolWhatsapp;

    public String schoolFacebook;

    public String schoolInstagram;

    public String schoolTwitter;

    public String schoolYoutube;

    public String schoolLogo;

    public String schoolPicture1;

    public String schoolPicture2;

    public String schoolHeadmasterName;

    public String schoolHeadmasterPicture;

    public String schoolHeadmasterQuote;

    public String createdAt;

    public String updatedAt;

    public AboutModel(int id, String schoolName, String schoolAccreditation, String schoolHistory,
            String schoolSlogan, String schoolVision, String schoolMission, String schoolAddress,
            String schoolPhone, String schoolEmail, String schoolWhatsapp, String schoolFacebook,
            String schoolInstagram, String schoolTwitter, String schoolYoutube, String schoolLogo,
            String schoolPicture1, String schoolPicture2, String schoolHeadmasterName,
            String schoolHeadmasterPicture, String schoolHeadmasterQuote, String createdAt, String updatedAt) {
        this.id = id;
        this.schoolName = schoolName;
        this.schoolAccreditation = schoolAccreditation;
        this.schoolHistory = schoolHistory;
        this.schoolSlogan = schoolSlogan;
        this.schoolVision = schoolVision;
        this.schoolMission = schoolMission;
        this.schoolAddress = schoolAddress;
        this.schoolPhone = schoolPhone;
        this.schoolEmail = schoolEmail;
        this.schoolWhatsapp = schoolWhatsapp;
        this.schoolFacebook = schoolFacebook;
        this.schoolInstagram = schoolInstagram;
        this.schoolTwitter = schoolTwitter;
        this.schoolYoutube = schoolYoutube;
        this.schoolLogo = schoolLogo;
        this.schoolPicture1 = schoolPicture1;
        this.schoolPicture2 = schoolPicture2;
        this.schoolHeadmasterName = schoolHeadmasterName;
        this.schoolHeadmasterPicture = schoolHeadmasterPicture;
        this.schoolHeadmasterQuote = schoolHeadmasterQuote;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static AboutModel fromJson(JSONObject json) throws JSONException {
        return new AboutModel(
                json.getInt("id"),
                json.getString("school_name"),
                json.getString("school_accreditation"),
                json.getString("school_history"),
                json.getString("school_slogan"),
                json.getString("school_vision"),
                json.getString("school_mission"),
                json.getString("school_address"),
                json.getString("school_phone"),
                json.getString("school_email"),
                json.getString("school_whatsapp"),
                json.getString("school_facebook"),
                json.getString("school_instagram"),
                json.getString("school_twitter"),
                json.getString("school_youtube"),
                json.getString("school_logo"),
                json.getString("school_picture_1"),
                json.getString("school_picture_2"),
                json.getString("school_headmaster_name"),
                json.getString("school_headmaster_picture"),
                json.getString("school_headmaster_quote"),
                json.getString("created_at"),
                json.getString("updated_at")
            );
    }
}
