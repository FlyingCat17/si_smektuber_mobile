package com.nekoid.smektuber.screen.splash;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.helpers.navigation.Navigator;

import com.nekoid.smektuber.helpers.statusBar.StatusBarUtil;
import com.nekoid.smektuber.helpers.animation.AnimationListener;
import com.nekoid.smektuber.app.BaseActivity;
import com.nekoid.smektuber.helpers.utils.Utils;
import com.nekoid.smektuber.screen.auth.Login;
import com.nekoid.smektuber.screen.home.HomeMember;
import com.nekoid.smektuber.screen.notification.NotifNoInternet;

public class SplashScreen extends BaseActivity {

    private ImageView logo;
    private Animation anim;

    private TextView txtSplashVersion, titleSplash;
    private FrameLayout notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        deleteCache();
        setContentView(R.layout.activity_splash_screen);
        txtSplashVersion = findViewById(R.id.splashVersion);
        notification = findViewById( R.id.notifContainer );
        getVersion();

        // reLogin if user is login.
        if (getUserPreferences().getBoolean("isLogin", false)) {
            doLogin();
        }

        // Panggil method setTransparentStatusBar()
        StatusBarUtil.setTransparentStatusBar(this);

        logo = findViewById(R.id.logo);
        titleSplash = findViewById( R.id.titleSplash );
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);

        anim.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                animationEnd();
//                checkInternetConnection();
            }
        });

        logo.startAnimation(anim);
        titleSplash.startAnimation( anim );
    }

    private void animationEnd() {
        if (Utils.isNetworkAvailable()) {
            validateToLogin();
        } else {
            showNoInternetMessage();
        }
    }
    private void validateToLogin(){
        if (getUserPreferences().getBoolean("isLogin", false)) {
            doLogin(getAuthPreferences().getString("_username", ""), getAuthPreferences().getString("_credentials", ""));
            redirectToHome();
            return;
        }
        redirectToLogin();
    }
    private void showNoInternetMessage(){
        logo.setVisibility( View.INVISIBLE );
        titleSplash.setVisibility( View.INVISIBLE );
        txtSplashVersion.setVisibility( View.INVISIBLE );
        notification.setVisibility( View.VISIBLE );
        replaceFragment( R.id.notifContainer , new NotifNoInternet(v->{
            if (Utils.isNetworkAvailable()){
                validateToLogin();
            }else {
                Toast.makeText( this, "Tidak ada internet", Toast.LENGTH_SHORT ).show();
            }
        }));
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