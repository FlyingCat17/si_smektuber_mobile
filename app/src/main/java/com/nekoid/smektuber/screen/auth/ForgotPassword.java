package com.nekoid.smektuber.screen.auth;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.api.Endpoint;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.app.BaseActivity;
import com.nekoid.smektuber.helpers.listener.TextChangeListener;
import com.nekoid.smektuber.helpers.utils.Utils;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.network.Response;
import com.nekoid.smektuber.screen.notification.NotifNoInternet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends BaseActivity {

    private TextInputLayout txtLayoutEmail;

    private TextInputEditText et_email;

    private Button btnSendEmail;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setToolbar();
        init();
        onListen();
    }

    private void onListen() {
        btnSendEmail.setOnClickListener(v -> {
            if (!Utils.isNetworkAvailable()){
                fragmentNoInternet();
                return;
            }
            if (emailValidator(txtLayoutEmail, et_email)) {
                Map<String, String> params = new HashMap<>();
                params.put("email", et_email.getText().toString());
                Http.post(Endpoint.FORGOT_PASSWORD.getUrl(), null, params, this::onResponse);
            }
        });

        et_email.addTextChangedListener(new TextChangeListener() {
            @Override
            protected void onTextChanged(String before, String old, String aNew, String after) {
                if (!et_email.getText().toString().isEmpty()) {
                    txtLayoutEmail.setErrorEnabled(false);
                }
            }
        });
    }

    public void fragmentNoInternet() {
        findViewById(R.id.forgotPasswordFragment).setVisibility(View.VISIBLE);
        findViewById(R.id.forgotPasswordRelative).setVisibility(View.INVISIBLE);
        replaceFragment(R.id.forgotPasswordFragment, new NotifNoInternet(view -> {
            if (Utils.isNetworkAvailable()){
                findViewById(R.id.forgotPasswordFragment).setVisibility(View.INVISIBLE);
                findViewById(R.id.forgotPasswordRelative).setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, "Please connect to internet, and try again", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void init() {
        btnSendEmail = findViewById(R.id.btnSendEmail);
        txtLayoutEmail = findViewById(R.id.etLayoutEmail);
        et_email = findViewById(R.id.et_email);
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

    private void onResponse(Response response) {
        if (response.statusCode != 200) {
            try {
                JSONObject json = new JSONObject(response.body.toString());
                Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        Navigator.of(this).pushReplacement(OtpVerification.class, et_email.getText().toString());
    }
}