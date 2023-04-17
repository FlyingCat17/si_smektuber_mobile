package com.nekoid.smektuber.screen.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
//import androidx.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.statusBar.StatusBarUtil;

public class Login extends AppCompatActivity {
    private Button btnLogin;
    private TextView txtToRegister;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        // Panggil method setTransparentStatusBar()
        StatusBarUtil.setTransparentStatusBar(this);
        init();
    }

    private void init(){
        btnLogin = findViewById( R.id.btnLogin );
        txtToRegister = findViewById( R.id.txtToRegister );
        toolbar = findViewById( R.id.backIcon );
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtToRegister.setOnClickListener( v->{
            Navigator.push( this,Register.class );
        } );
        toolbar.setOnClickListener( v->{
            onBackPressed();
        } );
    }
    @Override
    public void onBackPressed() {
        // Custom route
        // startActivity(new Intent(this, WelcomeAuth.class));
        Navigator.goBack( this,WelcomeAuth.class );
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}