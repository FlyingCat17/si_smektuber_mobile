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
        StatusBarUtil.setTransparentStatusBar(this);
        setToolbar();
        init();
    }

    private void init(){
        txtToLogin = findViewById( R.id.txtToLogin );
        btnRegister = findViewById( R.id.btnRegister );
        btnRegister.setText( "Register" );

        // action
        txtToLogin.setOnClickListener( v-> {
//            Navigator.push( this,Login.class );
//            Navigator.of( this ).pushReplacement( Login.class );
//            Navigator.of( this ).pop();
            onBackPressed();
        } );
    }
    private void setToolbar(){
        toolbar = findViewById( R.id.backIconL );
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        Navigator.of(this).pop();
        return true;
    }

}