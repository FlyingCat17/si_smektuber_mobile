package com.nekoid.smektuber.screen.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.widget.Style;

public class ResetPassword extends AppCompatActivity {
    TextInputLayout txtLayoutPw, txtLayoutPwConfirm;
    TextInputEditText et_Password, et_PasswordConfirm;
    Toolbar toolbar;
    private Button btnResetPw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_reset_password );
        setToolbar();
    }

    private void init(){
        txtLayoutPw = findViewById( R.id.txtLayoutPassword );
        txtLayoutPwConfirm = findViewById( R.id.txtLayoutPasswordConfirm );
        et_Password = findViewById( R.id.et_password );
        et_PasswordConfirm = findViewById( R.id.et_passwordConfirm );
        btnResetPw = findViewById( R.id.btnSendResetPw );
        btnResetPw.setText( "Daftar" );
        btnResetPw.setOnClickListener( v->{
            // todo : reset pw
            doResetPassword();
        } );

    }
    private void doResetPassword(){

    }
    private void setToolbar(){
        toolbar = findViewById( R.id.backIcon );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
    }

    @Override
    public boolean onSupportNavigateUp() {
        Navigator.of( this ).pop();
        return true;
    }
}