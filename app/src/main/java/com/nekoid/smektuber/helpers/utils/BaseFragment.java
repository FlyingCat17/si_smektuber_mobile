package com.nekoid.smektuber.helpers.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    /**
     * <p>Access token</p>
     *
     * <p>Use this to access api</p>
     */
    private static final String accessToken = "accessToken";

    /**
     * <p>Token expired at</p>
     *
     * <p>Use this to check token expired</p>
     */
    private static final String tokenExpiredAt = "expiredAt";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * <p>Get access token</p>
     *
     * <p>When we get access token, we will get from preferences</p>
     *
     * @return String
     */
    protected String getToken() {
        return this.getTokenPreferences().getString(accessToken, "");
    }

    /**
     * <p>Get token preferences</p>
     *
     * <p>When we get token preferences, we will get from preferences</p>
     *
     * @return SharedPreferences
     */
    private SharedPreferences getTokenPreferences() {
        return getActivity().getSharedPreferences("__accessToken", Context.MODE_PRIVATE);
    }
}
