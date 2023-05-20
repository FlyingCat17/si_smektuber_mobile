package com.nekoid.smektuber.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Pagination extends Models {

    public int total;

    public int perPage;

    public int currentPage;

    public int lastPage;

    public String firstPageUrl;

    public String lastPageUrl;

    public String nextPageUrl;

    public String prevPageUrl;

    public int from;

    public int to;

    public Pagination(int total, int perPage, int currentPage, int lastPage, String firstPageUrl, String lastPageUrl, String nextPageUrl, String prevPageUrl, int from, int to) {
        this.total = total;
        this.perPage = perPage;
        this.currentPage = currentPage;
        this.lastPage = lastPage;
        this.firstPageUrl = firstPageUrl;
        this.lastPageUrl = lastPageUrl;
        this.nextPageUrl = nextPageUrl;
        this.prevPageUrl = prevPageUrl;
        this.from = from;
        this.to = to;
    }

    public static Pagination fromJson(JSONObject json) throws JSONException {
        return new Pagination(
                json.getInt("total"),
                json.getInt("per_page"),
                json.getInt("current_page"),
                json.getInt("last_page"),
                json.getString("first_page_url"),
                json.getString("last_page_url"),
                json.getString("next_page_url"),
                json.getString("prev_page_url"),
                json.getInt("from"),
                json.getInt("to")
        );
    }
}
