package com.nekoid.smektuber.screen.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.helpers.statusBar.StatusBarUtil;

public class HomeMember extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_member);
        // Panggil method setTransparentStatusBar()
        StatusBarUtil.setTransparentStatusBar(this);
    }
}