package com.nekoid.smektuber.screen.auth;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.api.Endpoint;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.statusBar.StatusBarUtil;
import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.helpers.utils.TextChangeListener;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.network.Response;

import java.util.HashMap;
import java.util.Map;

public class Register extends BaseActivity {
    private Toolbar toolbar;
    private Button btnRegister;
    private TextView txtToLogin;
    private TextInputLayout txtLayoutUsername, txtLayoutname, txtLayoutEmail, txtLayoutPassword;
    private TextInputEditText et_username, et_name, et_email, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        StatusBarUtil.setTransparentStatusBar(this);
        setToolbar();
        init();
    }

    private void init() {
        txtLayoutUsername = findViewById(R.id.txtLayoutUsername);
        txtLayoutEmail = findViewById(R.id.txtLayoutEmail);
        txtLayoutname = findViewById(R.id.txtLayoutFullName);
        txtLayoutPassword = findViewById(R.id.txtLayoutPassword);
        et_username = findViewById(R.id.et_username);
        et_email = findViewById(R.id.et_email);
        et_name = findViewById(R.id.et_name);
        et_password = findViewById(R.id.et_password);

        txtToLogin = findViewById(R.id.txtToLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setText("Register");

        // action
        txtToLogin.setOnClickListener(v -> {
            onBackPressed();
        });
        btnRegister.setOnClickListener(v -> {
            if (validate()) {
                Http.post(Endpoint.REGISTER.getUrl(), null, getBody(), this::doRegister);
            }
        });

        et_username.addTextChangedListener(new TextChangeListener() {
            @Override
            protected void onTextChanged(String before, String old, String aNew, String after) {
                if (!et_username.getText().toString().isEmpty()) {
                    txtLayoutUsername.setErrorEnabled(false);
                }
            }
        });

        et_name.addTextChangedListener(new TextChangeListener() {
            @Override
            protected void onTextChanged(String before, String old, String aNew, String after) {
                if (!et_name.getText().toString().isEmpty()) {
                    txtLayoutname.setErrorEnabled(false);
                }
            }
        });

        et_email.addTextChangedListener(new TextChangeListener() {
            @Override
            protected void onTextChanged(String before, String old, String aNew, String after) {
                if (emailValidator(txtLayoutEmail, et_email)) {
                    txtLayoutEmail.setErrorEnabled(false);
                }
            }
        });

        et_password.addTextChangedListener(new TextChangeListener() {
            @Override
            protected void onTextChanged(String before, String old, String aNew, String after) {
                if (isPasswordValid(et_password.getText().toString())) {
                    txtLayoutPassword.setErrorEnabled(false);
                }
            }
        });
    }

    private Map<String, String> getBody() {
        HashMap<String, String> body = new HashMap<>();
        body.put("username", et_username.getText().toString().trim());
        body.put("name", et_name.getText().toString());
        body.put("email", et_email.getText().toString());
        body.put("password", et_password.getText().toString().trim());
        return body;
    }

    private void doRegister(Response response) {
        if (response.statusCode != 200) {
            Toast.makeText(this, "Auth failure", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show();
        Navigator.of(this).pushReplacement(Login.class);
    }

    private boolean validate() {
        String username = et_username.getText().toString();
        String name = et_name.getText().toString();
        String password = et_password.getText().toString().trim();

        if (username.isEmpty()) {
            return setError(true, "Mohon isi Username anda", txtLayoutUsername);
        }

        if (name.isEmpty()) {
            return setError(true, "Mohon isi nama lengkap anda", txtLayoutname);
        }

        if (emailValidator(txtLayoutEmail, et_email)) {
            return false;
        }

        if (!isPasswordValid(password)) {
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(String password) {
        if (password.isEmpty()) {
            return setError(true, "Mohon isi password anda", txtLayoutPassword);
        }

        if (password.length() < 8) {
            return setError(true, "Password minimal harus 8 karakter", txtLayoutPassword);
        }

        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$")) {
            return setError(true, "Password harus terdiri dari kombinasi huruf besar, huruf kecil, angka, dan karakter seperti !@#$%^&+=", txtLayoutPassword);
        }

        return setError(false, null, txtLayoutPassword);
    }

    private boolean setError(boolean error, String message, TextInputLayout layout) {
        layout.setErrorEnabled(error);
        layout.setError(message);
        layout.setErrorIconDrawable(null);
        return !error;
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.backIconL);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Navigator.of(this).pop();
        return true;
    }
}