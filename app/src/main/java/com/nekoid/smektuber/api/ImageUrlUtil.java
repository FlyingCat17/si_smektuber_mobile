package com.nekoid.smektuber.api;

public class ImageUrlUtil {
    private static final String BASE_URL= "https://lutfisobri.my.id/";
    private static final String STORAGE_PATH = "storage/app/";

    public static String manipulateImageUrl(String imageUrl){
        return BASE_URL+ STORAGE_PATH+ imageUrl;
    }

    public static String modifyAvatarUrl(String imageUrl) {
        String modifiedUrl = imageUrl.replace("/avatars/", "/app/avatars/");
        return modifiedUrl;
    }
}
