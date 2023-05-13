package com.nekoid.smektuber.screen.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.adapter.AdapterData;
import com.nekoid.smektuber.config.volley.PublicApi;
import com.nekoid.smektuber.config.volley.UrlsApi;
import com.nekoid.smektuber.helpers.navigation.Navigator;

import com.nekoid.smektuber.helpers.statusBar.StatusBarUtil;
import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.helpers.utils.LocalStorage;
import com.nekoid.smektuber.helpers.widget.Style;
import com.nekoid.smektuber.models.ArticleModel;
import com.nekoid.smektuber.screen.auth.Login;
import com.nekoid.smektuber.screen.auth.WelcomeAuth;
import com.nekoid.smektuber.screen.home.HomeMember;
import com.nekoid.smektuber.screen.home.dashboard.Dashboard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplashScreen extends BaseActivity {

    private ImageView logo;
    private Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // reLogin if user is login.
        if (getUserPreferences().getBoolean("isLogin", false)) {
            doLogin(getAuthPreferences().getString("_username", ""), getAuthPreferences().getString("_credentials", ""));
        }

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
                animationEnd();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        logo.startAnimation(anim);
    }

    private void animationEnd() {
        if (getUserPreferences().getBoolean("isLogin", false)) {
            doLogin(getAuthPreferences().getString("_username", ""), getAuthPreferences().getString("_credentials", ""));
            redirectToHome();
            return;
        }
        redirectToLogin();
    }

    private void redirectToLogin(){
        Navigator.of(SplashScreen.this).pushReplacement(Login.class);
    }
    private void redirectToHome(){
        Navigator.of(SplashScreen.this).pushReplacement(HomeMember.class);
    }
}