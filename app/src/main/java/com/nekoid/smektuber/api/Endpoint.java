package com.nekoid.smektuber.api;

/**
 * Endpoints for API
 * 
 * How to use:
 *
 * Endpoint.LOGIN.getUrl()
 */
public enum Endpoint {
    // AUTH
    REGISTER("/auth/register"),
    LOGIN("/auth/login"),
    LOGOUT("/auth/logout"),
    REFRESH_TOKEN("/auth/refresh"),
    UPDATE_PASSWORD("/auth/update-password"),
    FORGOT_PASSWORD("/auth/forgot-password"),
    VALIDATE_OTP("/auth/otp"),

    // ARTICLE
    LIST_ARTICLE("/article"),
    GET_ARTICLE_BY_ID("/article/"),

    // USER
    GET_USER("/user/detail"),
    UPDATE_USER("/user/update"),
    UPDATE_AVATAR("/user/avatar"),

    // PPDB
    PPDB("/ppdb"),

    // LOKER
    LIST_JOBS("/jobs"),
    GET_JOB_BY_ID("/jobs/"),

    // ABOUT OF SCHOOL
    ABOUT("/about"),

    // MAJOR
    LIST_MAJOR("/major"),
    GET_MAJOR_BY_ID("/major/"),

    // EXTRACURRICULAR
    LIST_EXTRACURRICULAR("/extracurricular"),
    GET_EXTRACURRICULAR_BY_ID("/extracurricular/"),
    ;

    private String url;

    Endpoint(String url) {
        this.url = url;
    }

    public String getUrl() {
        String baseUrl = "https://lutfisobri.my.id/api/v1";
        return baseUrl + url;
    }
}
