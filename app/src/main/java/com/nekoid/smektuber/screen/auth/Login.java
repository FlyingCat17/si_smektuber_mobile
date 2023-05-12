package com.nekoid.smektuber.screen.auth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.config.volley.UrlsApi;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.statusBar.StatusBarUtil;
import com.nekoid.smektuber.models.User;
import com.nekoid.smektuber.screen.home.HomeMember;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private User user;
    private Button btnLogin;
    private TextView txtToRegister;
    private TextInputLayout txtLayoutUsername, txtLayoutPassword;
//    private EditText et_username, et_password;
    private TextInputEditText et_username, et_password;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        // Panggil method setTransparentStatusBar()
        StatusBarUtil.setTransparentStatusBar(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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

        txtToRegister.setOnClickListener( v->{
            Navigator.push( this,Register.class );
        } );

        btnLogin.setOnClickListener( v -> {
            if (validate()){
//                Navigator.push( this, HomeMember.class);
                goLogin();
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

    private void goLogin() {
//        if (isFinishing()) {
//            return;
//        }
//        dialog.setMessage( "Sedang Login !!!" );
//        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, UrlsApi.LOGIN, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e( "data object",jsonObject+"" );
                if (jsonObject.getInt( "status" )==200) {
                    user = User.fromJson(jsonObject.getJSONObject("data"));
//                    String message = jsonObject.getJSONObject( "Success" );
                    JSONObject userData = jsonObject.getJSONObject("data").getJSONObject("user");
                    String accessToken = jsonObject.getJSONObject("data").getString("access_token");
                    String name = userData.getString("name");
                    String email = userData.getString("email");
                    String role = userData.getString("role");
                    String username = userData.getString("username");

                    // save user data to SharedPreferences
                    SharedPreferences userPref = getSharedPreferences("user", MODE_PRIVATE);
                    SharedPreferences.Editor editor = userPref.edit();
                    editor.putString("access_token", accessToken);
                    editor.putString("name", name);
                    editor.putString("email", email);
                    editor.putString("role", role);
                    editor.putString("username", username);
                    editor.putBoolean( "isLoggedIn",true );
                    editor.apply();

                    // start HomeMember activity
                    Navigator.of( this ).pushReplacement( HomeMember.class );
                    Toast.makeText( this, "Login Berhasil", Toast.LENGTH_SHORT ).show();
                } else {
                    Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        }, error -> {
            error.printStackTrace();
            if (error instanceof NetworkError) {
                Toast.makeText(this, "No network available", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else if (error instanceof ServerError) {
                Toast.makeText(this, "Server error", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else if (error instanceof AuthFailureError) {
                Toast.makeText(this, "Auth failure", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else if (error instanceof ParseError) {
                Toast.makeText(this, "Parse error", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else if (error instanceof TimeoutError) {
                Toast.makeText(this, "Timeout error", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Unknown error", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
            dialog.dismiss();
        }) {
            // add parameters to POST request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("username", et_username.getText().toString().trim());
                params.put("password",et_password.getText().toString().trim());

                return params;
            }

            // add headers to POST request
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        // add request to Volley request queue
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        queue.add(request);
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