package com.nekoid.smektuber.screen.auth;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.statusBar.StatusBarUtil;
import com.nekoid.smektuber.helpers.widget.Style;

public class WelcomeAuth extends AppCompatActivity {
    private Button btnToLogin, btnToRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_welcome_auth );
        // Panggil method setTransparentStatusBar()
        StatusBarUtil.setTransparentStatusBar(this);
        init();
    }

    private void init(){
        btnToLogin = findViewById( R.id.btnToLogin );
        btnToRegister = findViewById( R.id.btnToRegister );
        new Style(this, btnToLogin).build();
        // action
        btnToLogin.setOnClickListener( v -> {
            Navigator.push(this, Login.class).animation(R.anim.fade_in, R.anim.fade_out);
        } );
        btnToRegister.setOnClickListener( v-> {
            Navigator.of(this).push(Register.class);
        } );
    }
}