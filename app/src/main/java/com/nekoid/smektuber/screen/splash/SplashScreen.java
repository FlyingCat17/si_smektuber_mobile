package com.nekoid.smektuber.screen.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.config.volley.UrlsApi;
import com.nekoid.smektuber.helpers.navigation.Navigator;

import com.nekoid.smektuber.helpers.statusBar.StatusBarUtil;
import com.nekoid.smektuber.helpers.widget.Style;
import com.nekoid.smektuber.screen.auth.Login;
import com.nekoid.smektuber.screen.auth.WelcomeAuth;
import com.nekoid.smektuber.screen.home.HomeMember;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {

    private ImageView logo;
    private Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // Panggil method setTransparentStatusBar()
        StatusBarUtil.setTransparentStatusBar(this);

        logo = findViewById(R.id.logo);
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                SharedPreferences userPref = getSharedPreferences("user", MODE_PRIVATE);
                String accessToken = userPref.getString("access_token", "");
                long expiresAt = userPref.getLong("expires_at", 0);

                if (!accessToken.isEmpty() && System.currentTimeMillis() >= expiresAt) {
                    // Token has expired
                    //redirectToLogin();
                    refreshToken();
                } else {
                    boolean isLoggedIn = userPref.getBoolean("isLoggedIn", false);
                    if (isLoggedIn) {
                        redirectToHome();
                    } else {
                        redirectToLogin();
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        logo.startAnimation(anim);

    }
    private void redirectToLogin(){
        Navigator.of(SplashScreen.this).pushReplacement(Login.class);
    }
    private void redirectToHome(){
        Navigator.of(SplashScreen.this).pushReplacement(HomeMember.class);
    }
    private void refreshToken() {
        String url = UrlsApi.REFRESH_TOKEN;
        SharedPreferences userPref = getSharedPreferences("user", MODE_PRIVATE);
        String refreshToken = userPref.getString("refresh_token", "");

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("status") == 200) {
                    String newAccessToken = jsonObject.getJSONObject("data").getString("access_token");
                    int expiresIn = jsonObject.getJSONObject("data").getInt("expires_in");

                    SharedPreferences.Editor editor = userPref.edit();
                    editor.putString("access_token", newAccessToken);
                    editor.putLong("expires_at", System.currentTimeMillis() + (expiresIn * 1000));
                    editor.apply();

                    redirectToHome();
                    Toast.makeText( SplashScreen.this, "Test : Access_Token Refresh", Toast.LENGTH_SHORT ).show();
                } else {
                    redirectToLogin();
                    Toast.makeText( SplashScreen.this, "Test : Token Fail to refresh", Toast.LENGTH_SHORT ).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
            redirectToLogin();
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("refresh_token", refreshToken);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String accessToken = userPref.getString("access_token", "");
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                headers.putAll(super.getHeaders());
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        queue.add(request);
    }

}