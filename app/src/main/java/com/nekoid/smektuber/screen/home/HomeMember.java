package com.nekoid.smektuber.screen.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.helpers.statusBar.StatusBarUtil;
import com.nekoid.smektuber.screen.home.account.Account;
import com.nekoid.smektuber.screen.home.dashboard.Dashboard;
import com.nekoid.smektuber.screen.home.job.NoJobs;
import com.nekoid.smektuber.screen.home.ppdb.Ppdb;

public class HomeMember extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Account account = new Account();
    Dashboard dashboard = new Dashboard();
//    No_Information_Ppdb ppdb = new No_Information_Ppdb();
    NoJobs jobs = new NoJobs();
    Ppdb ppdb = new Ppdb();
    boolean doubleBackToExitPressedOnce = false;

<<<<<<< HEAD
//    private Internet internet;

=======
>>>>>>> b24c9c0a5e5f917d2a6c55e2eed14e633e1b266a
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_member);
        bottomNavigationView  = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setItemTextColor(null);
        getSupportFragmentManager().beginTransaction().replace(R.id.r,dashboard).commit();
        StatusBarUtil.setTransparentStatusBar(this);

<<<<<<< HEAD
//        internet = new Internet();

=======
>>>>>>> b24c9c0a5e5f917d2a6c55e2eed14e633e1b266a
        Menu menu = bottomNavigationView.getMenu();
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        String role = sharedPreferences.getString("role", null);
        if (!role.isEmpty() && role.equals("member")) {
            menu.removeItem(R.id.Loker);
        } else if (!role.isEmpty() && role.equals("siswa")) {
            menu.removeItem(R.id.Ppdb);
        }

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
<<<<<<< HEAD

//    protected void onResume() {
//        super.onResume();
//        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        registerReceiver(internet, intentFilter);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        unregisterReceiver(internet);
//    }

//    public class Internet extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (isNetworkAvailable(context)) {
//                Toast.makeText(context, "Koneksi internet tersedia", Toast.LENGTH_SHORT).show();
//                // Lakukan tindakan yang sesuai ketika koneksi tersedia
//            } else {
//                getSupportFragmentManager().beginTransaction().replace(R.id.r,notifNoInternet).commit();
//                Toast.makeText(context, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
//                // Lakukan tindakan yang sesuai ketika koneksi terputus
//            }
//        }

//        private boolean isNetworkAvailable(Context context) {
//            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//            if (connectivityManager != null) {
//                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//            }
//
//            return false;
//        }
//    }

=======
>>>>>>> b24c9c0a5e5f917d2a6c55e2eed14e633e1b266a
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