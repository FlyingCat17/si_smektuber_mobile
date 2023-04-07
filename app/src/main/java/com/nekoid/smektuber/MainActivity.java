package com.nekoid.smektuber;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nekoid.smektuber.models.User;
import com.nekoid.smektuber.navigation.Navigator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
    }
}