package com.nekoid.smektuber.models;

import org.json.JSONObject;

public class AboutModel extends Models {
    public int id;
    public String name;
    public String visi;
    public String misi;
    public String headMasterName;
    public String headMasterPhoto;
    public String headMasterQuote;
    public String headMasterDescription;
    public String accreditation;
    public String phone;
    public String email;
    public String address;
    public String facebook;
    public String instagram;
    public String twitter;
    public String youtube;
    public String linkedin;
    public String map;
    public String logo;
    public String favicon;
    public String schoolDescription;
    public String schoolPhoto;
    public String schoolQuote;

    public AboutModel(int id, String name, String visi, String misi, String headMasterName, String headMasterPhoto, String headMasterQuote, String headMasterDescription, String accreditation, String phone, String email, String address, String facebook, String instagram, String twitter, String youtube, String linkedin, String map, String logo, String favicon, String schoolDescription, String schoolPhoto, String schoolQuote) {
        this.id = id;
        this.name = name;
        this.visi = visi;
        this.misi = misi;
        this.headMasterName = headMasterName;
        this.headMasterPhoto = headMasterPhoto;
        this.headMasterQuote = headMasterQuote;
        this.headMasterDescription = headMasterDescription;
        this.accreditation = accreditation;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.facebook = facebook;
        this.instagram = instagram;
        this.twitter = twitter;
        this.youtube = youtube;
        this.linkedin = linkedin;
        this.map = map;
        this.logo = logo;
        this.favicon = favicon;
        this.schoolDescription = schoolDescription;
        this.schoolPhoto = schoolPhoto;
        this.schoolQuote = schoolQuote;

    }

    public static AboutModel fromJson(JSONObject json) {
        return new AboutModel(
                json.optInt( "id" ),
                json.optString( "name" ),
                json.optString( "vission" ),
                json.optString( "mission" ),
                json.optString( "headmaster_name" ),
                json.optString( "headmaster_photo" ),
                json.optString( "headmaster_quote" ),
                json.optString( "headmaster_description" ),
                json.optString( "accreditation" ),
                json.optString( "phone" ),
                json.optString( "email" ),
                json.optString( "address" ),
                json.optString( "facebook" ),
                json.optString( "instagram" ),
                json.optString( "twitter" ),
                json.optString( "youtube" ),
                json.optString( "linkedin" ),
                json.optString( "map" ),
                json.optString( "logo" ),
                json.optString( "favicon" ),
                json.optString( "school_description" ),
                json.optString( "school_photo" ),
                json.optString( "school_quote" )
        );
    }


    // Getter methods
    public String getName() {
        return name;
    }

    public String getVisi() {
        return visi;
    }

    public String getMisi() {
        return misi;
    }

    public String getHeadMasterName() {
        return headMasterName;
    }

    public int getId() {
        return id;
    }
}
