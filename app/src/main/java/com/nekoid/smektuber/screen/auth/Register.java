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
import com.nekoid.smektuber.app.BaseActivity;
import com.nekoid.smektuber.helpers.listener.TextChangeListener;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.network.Response;
import com.nekoid.smektuber.screen.notification.LoadingDialog;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends BaseActivity {
    private Toolbar toolbar;
    private Button btnRegister;
    private TextView txtToLogin;
    private TextInputLayout txtLayoutUsername, txtLayoutname, txtLayoutEmail, txtLayoutPassword;
    private TextInputEditText et_username, et_name, et_email, et_password;
    private LoadingDialog loadingDialog;
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
        loadingDialog = new LoadingDialog( this );
        // action
        setClickListener();
        setTextChangeListener();
    }
    private void setClickListener(){
        txtToLogin.setOnClickListener(v -> onBackPressed());
        btnRegister.setOnClickListener(v -> {
            if (validate()) {
                loadingDialog.startLoading();
                Http.post(Endpoint.REGISTER.getUrl(), null, getBody(), this::doRegister);
            }
        });
    }
    private void setTextChangeListener(){
        et_username.addTextChangedListener(createTextChangeListener(txtLayoutUsername));
        et_name.addTextChangedListener(createTextChangeListener(txtLayoutname));
        et_email.addTextChangedListener(createEmailTextChangeListener(txtLayoutEmail));
        et_password.addTextChangedListener( createPasswordTextChangeListener( txtLayoutPassword ) );
    }
    private TextChangeListener createTextChangeListener(TextInputLayout layout) {
        return new TextChangeListener() {
            @Override
            protected void onTextChanged(String before, String old, String aNew, String after) {
                if (!aNew.isEmpty()) {
                    layout.setErrorEnabled(false);
                }
            }
        };
    }
    private TextChangeListener createEmailTextChangeListener(TextInputLayout layout) {
        return new TextChangeListener() {
            @Override
            protected void onTextChanged(String before, String old, String aNew, String after) {
                if (emailValidator(layout, et_email)) {
                    layout.setErrorEnabled(false);
                }
            }
        };
    }
    private TextChangeListener createPasswordTextChangeListener(TextInputLayout layout){
        return new TextChangeListener() {
            @Override
            protected void onTextChanged(String before, String old, String aNew, String after) {
                if (isPasswordValid(et_password.getText().toString())) {
                    layout.setErrorEnabled(false);
                }else {
                    layout.setErrorEnabled(true);
                    layout.setError("Password harus mengandung huruf besar, huruf kecil, angka, dan minimal 8 karakter.");
                }
            }
        };
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
        loadingDialog.isDismiss();
        if (response.statusCode != 200) {
            try {
                JSONObject responseObject = new JSONObject( String.valueOf( response.body ) );
                String errorMessage = responseObject.optString("message");
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error: " + response.statusCode, Toast.LENGTH_SHORT).show();
            }
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

        if (!emailValidator(txtLayoutEmail, et_email)) {
            return false;
        }

        if (!isPasswordValid(password)) {
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 8) {
            return false;
        }
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")) {
            return false;
        }
        return true;
    }

    private boolean setError(boolean error, String message, TextInputLayout layout) {
        layout.setErrorEnabled(error);
        layout.setError(message);
        layout.setErrorIconDrawable(null);
        return !error;
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.backIcon);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Navigator.of(this).pop();
        return true;
    }
}