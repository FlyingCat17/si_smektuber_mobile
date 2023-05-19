package com.nekoid.smektuber.screen.auth;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.statusBar.StatusBarUtil;
import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.helpers.utils.TextChangeListerner;
import com.nekoid.smektuber.network.Response;
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

    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // we will set transparent status bar
        StatusBarUtil.setTransparentStatusBar(this);
        init();
    }

    private void init() {
        dialog = new ProgressDialog(getBaseContext());
        dialog.setCancelable(false);
        txtLayoutUsername = findViewById(R.id.txtLayoutUsername);
        txtLayoutPassword = findViewById(R.id.txtLayoutPassword);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btnLogin);
        txtToRegister = findViewById(R.id.txtToRegister);
        txtToForgotPassword = findViewById(R.id.forgotPw);
        txtToForgotPassword.setOnClickListener(v -> {
            Navigator.push(this, ForgotPassword.class);
        });
        txtToRegister.setOnClickListener(v -> {
            Navigator.push(this, Register.class);
        });
        loadingDialog = new LoadingDialog(Login.this);

        btnLogin.setOnClickListener(v -> {
            if (validate()) {
                // start loading
                loadingDialog.startLoading();

                // we will send data to server
                doLogin(et_username.getText().toString(), et_password.getText().toString());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // we need to peek isLogin value
                        if (isLogin()) {
                            // stop loading
                            loadingDialog.isDismiss();

                            Toast.makeText(Login.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                            Navigator.of(Login.this).pushReplacement(HomeMember.class);
                        }
                    }
                }, 3000);
            }
        });

        et_username.addTextChangedListener(new TextChangeListerner() {
            @Override
            protected void onTextChanged(String before, String old, String aNew, String after) {
                if (!et_username.getText().toString().isEmpty()) {
                    txtLayoutUsername.setErrorEnabled(false);
                }
            }
        });

        et_password.addTextChangedListener(new TextChangeListerner() {
            @Override
            protected void onTextChanged(String before, String old, String aNew, String after) {
                if (!et_password.getText().toString().isEmpty()) {
                    txtLayoutPassword.setErrorEnabled(false);
                }
            }
        });

        et_password.addTextChangedListener(new TextChangeListerner() {
            @Override
            protected void onTextChanged(String before, String old, String aNew, String after) {

            }
        });
    }

    @Override
    protected void onLoginError(Response response) {
        try {
            // stop loading
            loadingDialog.isDismiss();

            JSONObject json = new JSONObject(response.body.toString());
            Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    private boolean validate() {
        String username = et_username.getText().toString();
        String password = et_password.getText().toString().trim();
        if (username.isEmpty()) {
            txtLayoutUsername.setErrorEnabled(true);
            txtLayoutUsername.setError("Mohon isi username anda");
            return false;
        }
        if (password.isEmpty()) {
            txtLayoutPassword.setErrorEnabled(true);
            txtLayoutPassword.setError("Mohon isi Password anda");
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