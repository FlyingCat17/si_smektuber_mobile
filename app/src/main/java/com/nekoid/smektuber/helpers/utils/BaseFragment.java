package com.nekoid.smektuber.helpers.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.network.Response;
import com.nekoid.smektuber.screen.auth.Login;

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

    protected SharedPreferences getUserSharedPreferences() {
        return getActivity().getSharedPreferences("user", MODE_PRIVATE);
    }

    /**
     * <p>Get token preferences</p>
     *
     * <p>When we get token preferences, we will get from preferences</p>
     *
     * @return SharedPreferences
     */
    protected SharedPreferences getTokenPreferences() {
        return getActivity().getSharedPreferences("__accessToken", Context.MODE_PRIVATE);
    }

    protected SharedPreferences getAuthPreferences() {
        return getActivity().getSharedPreferences("__auth", MODE_PRIVATE);
    }

    private void deleteAllUserPreferences() {
        getUserSharedPreferences().edit().clear().apply();
        getTokenPreferences().edit().clear().apply();
        getAuthPreferences().edit().clear().apply();
    }

    protected void doLogout(Response response) {
        if (response.statusCode != 200) {
            Toast.makeText(getActivity(), "Logout gagal, silahkan coba lagi", Toast.LENGTH_SHORT).show();
            return;
        }

        deleteAllUserPreferences();
        Navigator.of(getActivity()).pushReplacement(Login.class);
    }
}
