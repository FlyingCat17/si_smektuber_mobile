package com.nekoid.smektuber.screen.auth;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.app.BaseActivity;

public class OtpVerification extends BaseActivity {
    private TextInputLayout txtLayoutOtp01, txtLayoutOtp02, txtLayoutOtp03, txtLayoutOtp04;
    private TextInputEditText et_otp01, et_otp02, et_otp03, et_otp04;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_otp_verification );
        setToolbar();
        init();
    }

    private void init(){
        txtLayoutOtp01 = findViewById( R.id.txtLayoutotp01 );
        txtLayoutOtp02 = findViewById( R.id.txtLayoutotp02 );
        txtLayoutOtp03 = findViewById( R.id.txtLayoutotp03 );
        txtLayoutOtp04 = findViewById( R.id.txtLayoutotp04 );
        et_otp01 = findViewById( R.id.et_otp01 );
        et_otp02 = findViewById( R.id.et_otp02 );
        et_otp03 = findViewById( R.id.et_otp03 );
        et_otp04 = findViewById( R.id.et_otp04 );
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