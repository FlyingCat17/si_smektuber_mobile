package com.nekoid.smektuber.screen.splash;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.helpers.navigation.Navigator;

import com.nekoid.smektuber.helpers.statusBar.StatusBarUtil;
import com.nekoid.smektuber.helpers.animation.AnimationListener;
import com.nekoid.smektuber.app.BaseActivity;
import com.nekoid.smektuber.helpers.utils.Utils;
import com.nekoid.smektuber.screen.auth.Login;
import com.nekoid.smektuber.screen.home.HomeMember;

public class SplashScreen extends BaseActivity {

    private ImageView logo;
    private Animation anim;

    private TextView txtSplashVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        deleteCache();
        setContentView(R.layout.activity_splash_screen);
        txtSplashVersion = findViewById(R.id.splashVersion);
        getVersion();

        // reLogin if user is login.
        if (getUserPreferences().getBoolean("isLogin", false)) {
            doLogin();
        }

        // Panggil method setTransparentStatusBar()
        StatusBarUtil.setTransparentStatusBar(this);

        logo = findViewById(R.id.logo);
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);

        anim.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                animationEnd();
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

    private void redirectToHome() {
        Navigator.of(SplashScreen.this).pushReplacement(HomeMember.class);
    }

    private void getVersion() {
        String wm = Utils.strFormat("Version %s \n JTI POLIJE NekoID %s", Utils.getVersionName(), Utils.getYear());
        txtSplashVersion.setText(wm);
    }
}