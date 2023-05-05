package com.nekoid.smektuber.screen.home;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toolbar;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.screen.auth.Login;
import com.nekoid.smektuber.screen.auth.Register;

public class VisiAndMisi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visi_and_misi);
//        Toolbar myToolbar = findViewById(R.id.toolbar);
//        Button myButton = myToolbar.findViewById(R.id.backIcon);
//        myButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentManager fragmentManager = getSupportFragmentManager();
//
//                // Membuat instance dari FragmentTransaction
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//                // Membuat instance dari Fragment yang akan ditambahkan
//                Dashboard myFragment = new Dashboard();
//
//                // Menambahkan Fragment ke dalam Activity
//                fragmentTransaction.add(R.id.r,myFragment);
//
//                // Menjalankan transaksi Fragment
//                fragmentTransaction.commit();
//            }
//        });
    }
}