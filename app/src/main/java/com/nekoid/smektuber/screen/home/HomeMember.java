package com.nekoid.smektuber.screen.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.statusBar.StatusBarUtil;
import com.nekoid.smektuber.app.BaseActivity;
import com.nekoid.smektuber.helpers.utils.Network;
import com.nekoid.smektuber.screen.home.account.Account;
import com.nekoid.smektuber.screen.home.dashboard.Dashboard;
import com.nekoid.smektuber.screen.home.job.Jobs;
import com.nekoid.smektuber.screen.home.ppdb.Ppdb;
import com.nekoid.smektuber.screen.notification.NotifNoInternet;

public class HomeMember extends BaseActivity {
    private static final int REQUEST_PERMISSIONS = 100;
    BottomNavigationView bottomNavigationView;
    Account account = new Account();
    Dashboard dashboard = new Dashboard();
    Jobs jobs = new Jobs();
    Ppdb ppdb = new Ppdb();
    boolean doubleBackToExitPressedOnce = false;

    Fragment fragment;

    boolean networkIsAvailable = false;

    private NotifNoInternet noInternet = new NotifNoInternet();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_member);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setItemTextColor(null);
        fragment = dashboard;
        replaceFragment(R.id.r, dashboard);
        StatusBarUtil.setTransparentStatusBar(this);

        Menu menu = bottomNavigationView.getMenu();
        SharedPreferences sharedPreferences = getUserPreferences();
        String role = sharedPreferences.getString("role", null);
        if (!role.isEmpty() && role.equals("member")) {
            menu.removeItem(R.id.Loker);
        } else if (!role.isEmpty() && role.equals("siswa")) {
            menu.removeItem(R.id.Ppdb);
        }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Dashboard:
                        fragment = dashboard;
                        replaceFragment(R.id.r, dashboard);
                        if (!networkIsAvailable) {
                            fragmentNoInternet();
                        }
                        return true;
                    case R.id.Ppdb:
                        fragment = ppdb;
                        replaceFragment(R.id.r, ppdb);
                        if (!networkIsAvailable) {
                            fragmentNoInternet();
                        }
                        return true;
                    case R.id.Loker:
                        fragment = jobs;
                        replaceFragment(R.id.r, jobs);
                        if (!networkIsAvailable) {
                            fragmentNoInternet();
                        }
                        return true;
                    case R.id.Akun:
                        fragment = account;
                        replaceFragment(R.id.r, account);
                        if (!networkIsAvailable) {
                            fragmentNoInternet();
                        }
                        return true;
                }
                return false;
            }
        });

        new Network(this, new Network.Listener() {
            @Override
            public void onNetworkAvailable() {
                networkIsAvailable = true;
                replaceFragment(R.id.r, fragment);
            }

            @Override
            public void onNetworkUnavailable() {
                networkIsAvailable = false;
                fragmentNoInternet();
            }
        });
    }

    public void fragmentNoInternet() {
        replaceFragment(R.id.r, new NotifNoInternet());
    }

    public void onClickTryAgain(View view) {
        if (!networkIsAvailable) {
            Toast.makeText(this, "Please connect to internet, and try again", Toast.LENGTH_SHORT).show();
        }
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