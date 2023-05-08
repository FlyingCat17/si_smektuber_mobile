package com.nekoid.smektuber.screen.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.statusBar.StatusBarUtil;
import com.nekoid.smektuber.helpers.widget.Style;

public class Register extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btnRegister;
    private TextView txtToLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        // Panggil method setTransparentStatusBar()
        StatusBarUtil.setTransparentStatusBar(this);
        init();
    }

    private void init(){
        toolbar = findViewById( R.id.backIcon );
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToLogin = findViewById( R.id.txtToLogin );
        btnRegister = findViewById( R.id.btnRegister );

        // action
        txtToLogin.setOnClickListener( v-> {
//            Navigator.push( this,Login.class );
            Navigator.of( this ).pushReplacement( Login.class );
        } );
    }

    @Override
    public boolean onSupportNavigateUp() {
        Navigator.of(this).pop();
        return true;
    }

}