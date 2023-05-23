package com.nekoid.smektuber.helpers.utils;

import android.os.Build;

import com.nekoid.smektuber.models.AboutModel;
import com.nekoid.smektuber.models.ArticleModel;
import com.nekoid.smektuber.models.ExtracurricularModel;
import com.nekoid.smektuber.models.MajorModel;
import com.nekoid.smektuber.models.PpdbModel;
import com.nekoid.smektuber.models.UserModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class State {

    public static Cache cache;

    public static PpdbModel PpdbModel;

    public static UserModel userModel;

    public static Api api;

    public static List<ArticleModel> articleModels = new ArrayList<>();

    public static AboutModel aboutModel;

    public static List<ExtracurricularModel> extracurricularModels = new ArrayList<>();

    public static List<MajorModel> majorModels = new ArrayList<>();

    public static void setApi(Api api) {
        State.api = api;
    }

    public static Api getApi() {
        return api;
    }

    public static void setCache(Cache cache) {
        State.cache = cache;
    }

    public static Cache getCache() {
        return cache;
    }

    public static void setUserModel(UserModel userModel) {
        State.userModel = userModel;
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
                this.expiredAt = this.createdAt.plusSeconds(this.expired);
            }
        }

        public boolean isExpired() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                System.out.println("now " + LocalDateTime.now());
                System.out.println("expired " + this.expiredAt);
                return LocalDateTime.now().isAfter(this.expiredAt);
            }
            return false;
        }
    }
}
