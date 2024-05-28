package com.nekoid.smektuber.models;

import org.json.JSONException;
import org.json.JSONObject;

public class PpdbRegistrationModel extends Models{

    private String nisn;
    private String place_birth;
    private String full_name;
    private String date_birth;
    private String address;
    private String phone;
    private String fatherName;
    private String motherName;
    private String guardianName;
    private String schoolOrigin;
    private String graduationYear;
    private int majorId1;
    private int majorId2;

    public PpdbRegistrationModel(String nisn, String place_birth, String full_name, String date_birth, String address, String phone, String fatherName, String motherName, String guardianName, String schoolOrigin, String graduationYear, int majorId1, int majorId2) {
        this.nisn = nisn;
        this.place_birth = place_birth;
        this.full_name = full_name;
        this.date_birth = date_birth;
        this.address = address;
        this.phone = phone;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.guardianName = guardianName;
        this.schoolOrigin = schoolOrigin;
        this.graduationYear = graduationYear;
        this.majorId1 = majorId1;
        this.majorId2 = majorId2;
    }

    public static PpdbRegistrationModel fromJson(JSONObject json) throws JSONException{
        return new PpdbRegistrationModel(
                json.getString( "nisn" ),
                json.getString("place_birth"),
                json.getString( "full_name" ),
                json.getString( "date_birth" ),
                json.getString( "address" ),
                json.getString( "phone" ),
                json.getString("father_name"),
                json.getString("mother_name"),
                json.getString("guardian_name"),
                json.getString("school_origin"),
                json.getString("graduation_year"),
                json.getInt( "major_id_1" ),
                json.getInt( "major_id_2" )

        );
    }
}
