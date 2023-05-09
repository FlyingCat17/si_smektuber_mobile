package com.nekoid.smektuber.screen.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.screen.auth.Register;

public class ChangeDataAccount extends AppCompatActivity {
    //    Button Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_data_account);
//        Back = findViewById(R.id.backIcon);
//
//        Back.setOnClickListener( v->{
//            Navigator.push( this, Register.class );
//        } );
    }
}