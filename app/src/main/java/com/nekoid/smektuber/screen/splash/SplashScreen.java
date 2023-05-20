package com.nekoid.smektuber.screen.splash;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.api.Endpoint;
import com.nekoid.smektuber.api.PublicApi;
import com.nekoid.smektuber.helpers.navigation.Navigator;

import com.nekoid.smektuber.helpers.statusBar.StatusBarUtil;
import com.nekoid.smektuber.helpers.utils.AnimationListener;
import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.helpers.utils.State;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.screen.auth.Login;
import com.nekoid.smektuber.screen.home.HomeMember;

import java.util.Calendar;

public class SplashScreen extends BaseActivity {

    private ImageView logo;
    private Animation anim;

    private TextView txtSplashVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deleteCache();
        setContentView(R.layout.activity_splash_screen);
        txtSplashVersion = findViewById(R.id.splashVersion);
        getVersion();

        // reLogin if user is login.
        if (getUserPreferences().getBoolean("isLogin", false)) {
            doLogin(getAuthPreferences().getString("_username", ""), getAuthPreferences().getString("_credentials", ""));
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
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
            String version = "Version " + packageInfo.versionName + " \n JTI POLIJE NekoID " + year;
            txtSplashVersion.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}