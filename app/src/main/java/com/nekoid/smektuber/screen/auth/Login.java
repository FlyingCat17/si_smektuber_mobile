package com.nekoid.smektuber.screen.auth;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.statusBar.StatusBarUtil;
import com.nekoid.smektuber.screen.home.HomeMember;
import com.nekoid.smektuber.screen.home.Ppdb;

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

        btnLogin.setOnClickListener( v -> {
            Navigator.push(this, HomeMember.class);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        Navigator.of(this).pop();
        return true;
    }
}