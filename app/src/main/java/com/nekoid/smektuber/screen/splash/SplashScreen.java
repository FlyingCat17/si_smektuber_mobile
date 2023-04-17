package com.nekoid.smektuber.screen.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.helpers.navigation.Navigator;

import com.nekoid.smektuber.helpers.statusBar.StatusBarUtil;
import com.nekoid.smektuber.screen.auth.WelcomeAuth;

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
                Navigator.push(SplashScreen.this, WelcomeAuth.class);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        logo.startAnimation(anim);

    }
}