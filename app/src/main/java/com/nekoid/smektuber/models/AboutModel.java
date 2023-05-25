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
        this.schoolName = isEmpty(schoolName);
        this.schoolAccreditation = isEmpty(schoolAccreditation);
        this.schoolHistory = isEmpty(schoolHistory);
        this.schoolSlogan = isEmpty(schoolSlogan);
        this.schoolVision = isEmpty(schoolVision);
        this.schoolMission = isEmpty(schoolMission);
        this.schoolAddress = isEmpty(schoolAddress);
        this.schoolPhone = isEmpty(schoolPhone);
        this.schoolEmail = isEmpty(schoolEmail);
        this.schoolWhatsapp = isEmpty(schoolWhatsapp);
        this.schoolFacebook = isEmpty(schoolFacebook);
        this.schoolInstagram = isEmpty(schoolInstagram);
        this.schoolTwitter = isEmpty(schoolTwitter);
        this.schoolYoutube = isEmpty(schoolYoutube);
        this.schoolLogo = isEmpty(schoolLogo);
        this.schoolPicture1 = isEmpty(schoolPicture1);
        this.schoolPicture2 = isEmpty(schoolPicture2);
        this.schoolHeadmasterName = isEmpty(schoolHeadmasterName);
        this.schoolHeadmasterPicture = isEmpty(schoolHeadmasterPicture);
        this.schoolHeadmasterQuote = isEmpty(schoolHeadmasterQuote);
        this.createdAt = isEmpty(createdAt);
        this.updatedAt = isEmpty(updatedAt);
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
