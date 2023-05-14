package com.nekoid.smektuber.screen.auth;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.statusBar.StatusBarUtil;
import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.screen.home.HomeMember;
import com.nekoid.smektuber.screen.notification.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends BaseActivity {

    private Button btnLogin;

    private TextView txtToRegister, txtToForgotPassword;

    private TextInputLayout txtLayoutUsername, txtLayoutPassword;

    private TextInputEditText et_username, et_password;

    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        // we will set transparent status bar
        StatusBarUtil.setTransparentStatusBar(this);
        init();
    }

    private void init(){
        dialog = new ProgressDialog( getBaseContext() );
        dialog.setCancelable( false );
        txtLayoutUsername = findViewById( R.id.txtLayoutUsername );
        txtLayoutPassword = findViewById( R.id.txtLayoutPassword );
        et_username = findViewById( R.id.et_username );
        et_password = findViewById( R.id.et_password );
        btnLogin = findViewById( R.id.btnLogin );
        txtToRegister = findViewById( R.id.txtToRegister );
        txtToForgotPassword = findViewById( R.id.forgotPw );
        txtToForgotPassword.setOnClickListener( v->{
            Navigator.push( this,ForgotPassword.class );
        } );
        txtToRegister.setOnClickListener( v->{
            Navigator.push( this,Register.class );
        } );

        btnLogin.setOnClickListener( v -> {
            if (validate()){
                // we will send data to server
                doLogin(et_username.getText().toString(), et_password.getText().toString());
                // Create a LoadingDialog object and start loading
                LoadingDialog loadingDialog = new LoadingDialog(Login.this);
                loadingDialog.startLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // we need to peek isLogin value
                        if (isLogin()) {
                            Toast.makeText( Login.this, "Login Berhasil", Toast.LENGTH_SHORT ).show();
                            Navigator.of(Login.this).pushReplacement(HomeMember.class);
                        }
                        Toast.makeText( Login.this, "Login Gagal", Toast.LENGTH_SHORT ).show();
                        loadingDialog.isDismiss();
                    }
                }, 3000);
            }
        });

        et_username.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!et_username.getText().toString().isEmpty()){
                    txtLayoutUsername.setErrorEnabled( false );
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        } );

        et_password.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!et_password.getText().toString().isEmpty()){
                    txtLayoutPassword.setErrorEnabled( false );
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        } );
    }

    @Override
    protected void onLoginError(JSONObject response) throws JSONException {
        Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResponseError(VolleyError error) {
        super.onResponseError(error);
    }

    private boolean validate(){
        String username = et_username.getText().toString();
        String password = et_password.getText().toString().trim();
        if (username.isEmpty()){
            txtLayoutUsername.setErrorEnabled( true );
            txtLayoutUsername.setError( "Mohon isi username anda" );
            return false;
        }
        if(password.isEmpty()){
            txtLayoutPassword.setErrorEnabled( true );
            txtLayoutPassword.setError( "Mohon isi Password anda" );
            txtLayoutPassword.setErrorIconDrawable(null);
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Navigator.of(this).pop();
        return true;
    }
}