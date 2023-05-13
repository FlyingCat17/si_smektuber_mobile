package com.nekoid.smektuber.helpers.utils;

import android.graphics.Bitmap;
import android.os.Build;

import com.nekoid.smektuber.models.UserModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LocalStorage {

    static List<Bitmap> listImageArticleDashboard = new ArrayList<>();

    static List<Bitmap> listArticleAll = new ArrayList<>();

    public static UserModel userModel;

    public static Api api;

    public static void setApi(Api api) {
        LocalStorage.api = api;
    }

    public static Api getApi() {
        return api;
    }

    public static void setUserModel(UserModel userModel) {
        LocalStorage.userModel = userModel;
    }

    public static UserModel getUserModel() {
        return userModel;
    }

    public static class Api {
        private String accessToken;
        private int expired;
        private LocalDateTime createdAt;
        private LocalDateTime expiredAt;

        public Api(String accessToken, int expired) {
            this.accessToken = accessToken;
            this.expired = expired;
            this.setCreatedAt();
            this.setExpired();
        }

        private void setCreatedAt() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.createdAt = LocalDateTime.now();
            }
        }

        private void setExpired() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.expiredAt = this.createdAt.plusSeconds(this.expired - 600);
            }
        }

        public boolean isExpired() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return LocalDateTime.now().isAfter(this.expiredAt);
            }
            return false;
        }
    }
}
