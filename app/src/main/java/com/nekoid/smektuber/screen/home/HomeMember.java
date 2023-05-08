package com.nekoid.smektuber.screen.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.statusBar.StatusBarUtil;

public class HomeMember extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Account account = new Account();
    Dashboard dashboard = new Dashboard();

    NoJobs jobs = new NoJobs();
    Ppdb ppdb = new Ppdb();
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_member);
        bottomNavigationView  = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setItemTextColor(null);
        getSupportFragmentManager().beginTransaction().replace(R.id.r,dashboard).commit();
        StatusBarUtil.setTransparentStatusBar(this);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Dashboard:
                        getSupportFragmentManager().beginTransaction().replace(R.id.r,dashboard).commit();
                        return true;
                    case R.id.Ppdb:
                        getSupportFragmentManager().beginTransaction().replace(R.id.r,ppdb).commit();
                        return true;
                    case R.id.Loker:
                        getSupportFragmentManager().beginTransaction().replace(R.id.r,jobs).commit();
                        return true;
                    case R.id.Akun:
                        getSupportFragmentManager().beginTransaction().replace(R.id.r,account).commit();
                        return true;
                }
                return false;
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Ketuk sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }
}