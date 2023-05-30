package com.nekoid.smektuber.screen.auth;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.api.Endpoint;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.app.BaseActivity;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.network.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OtpVerification extends BaseActivity {
    private TextInputLayout txtLayoutOtp01, txtLayoutOtp02, txtLayoutOtp03, txtLayoutOtp04;
    private TextInputEditText et_otp01, et_otp02, et_otp03, et_otp04;
    private Button btnVeryfyOtp;
    private TextView txtResendOtp;
    private String email;
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
        btnVeryfyOtp = findViewById( R.id.btnSendOtp );
        txtResendOtp = findViewById( R.id.btnReSendOtp );
        email = getIntent().getStringExtra( "email" );
        btnVeryfyOtp.setOnClickListener( v->{
            // todo : Verify otp and if success go to ResetPassword.class
            String otp = getOtpFromInput();
            if (validateOtp( otp )){
                handleOtpVeryfication(otp);
            }

        } );
        txtResendOtp.setOnClickListener( v->{
            // todo : Resend Otp email
            handleResendOtp();
        } );
    }
    private void handleOtpVeryfication(String otp){
        Map<String, String> params = new HashMap<>();
        params.put( "otp", otp );
        Http.post( Endpoint.VALIDATE_OTP.getUrl(), null,params,this::onOtpValidationResponse);

    }
    private void onOtpValidationResponse(Response response) {
        if (response.statusCode != 200) {
            try {
                JSONObject json = new JSONObject(response.body.toString());
                Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        Navigator.of(this).push(ResetPassword.class);
    }
    private void handleResendOtp(){
        Map<String, String> params = new HashMap<>();
        params.put( "email", email );
        Http.post( Endpoint.RESEND_OTP.getUrl(), null, params,this::onResendOtpResponse);
    }
    private void onResendOtpResponse(Response response) {
        if (response.statusCode != 200) {
            try {
                JSONObject json = new JSONObject(response.body.toString());
                Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        Toast.makeText(this, "OTP resent successfully", Toast.LENGTH_SHORT).show();
    }
    private String getOtpFromInput(){
        String otp1 = et_otp01.getText().toString().trim();
        String otp2 = et_otp02.getText().toString().trim();
        String otp3 = et_otp03.getText().toString().trim();
        String otp4 = et_otp04.getText().toString().trim();

        return otp1+ otp2+ otp3+ otp4;
    }
    private boolean validateOtp(String otp) {
        boolean isValid = true;
        if (otp.length() != 4) {
            isValid = false;
            Toast.makeText(this, "OTP must be 4 digits", Toast.LENGTH_SHORT).show();
        }
        return isValid;
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