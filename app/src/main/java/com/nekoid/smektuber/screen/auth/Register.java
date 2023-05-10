package com.nekoid.smektuber.screen.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;
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
import com.nekoid.smektuber.screen.home.HomeMember;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btnRegister;
    private TextView txtToLogin;
    private TextInputLayout txtLayoutUsername, txtLayoutname, txtLayoutEmail,txtLayoutPassword;
    private TextInputEditText et_username, et_name, et_email, et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        StatusBarUtil.setTransparentStatusBar(this);
        setToolbar();
        init();
    }

    private void init(){
        txtLayoutUsername = findViewById( R.id.txtLayoutUsername );
        txtLayoutEmail = findViewById( R.id.txtLayoutEmail );
        txtLayoutname = findViewById( R.id.txtLayoutFullName );
        txtLayoutPassword = findViewById( R.id.txtLayoutPassword );
        et_username = findViewById( R.id.et_username );
        et_email = findViewById( R.id.et_email );
        et_name = findViewById( R.id.et_name );
        et_password = findViewById( R.id.et_password );

        txtToLogin = findViewById( R.id.txtToLogin );
        btnRegister = findViewById( R.id.btnRegister );
        btnRegister.setText( "Register" );

        // action
        txtToLogin.setOnClickListener( v-> {
//            Navigator.push( this,Login.class );
//            Navigator.of( this ).pushReplacement( Login.class );
//            Navigator.of( this ).pop();
            onBackPressed();
        } );
        btnRegister.setOnClickListener( v-> {
            if (validate()){
                goRegister();
            }
        } );
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
        et_name.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!et_name.getText().toString().isEmpty()){
                    txtLayoutname.setErrorEnabled( false );
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        } );
        et_email.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isEmailValid(et_email.getText().toString())){
                    txtLayoutEmail.setErrorEnabled( false );
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
                if (isPasswordValid(et_password.getText().toString())){
                    txtLayoutPassword.setErrorEnabled( false );
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        } );

    }
    private void goRegister(){
        StringRequest request = new StringRequest( Request.Method.POST, UrlsApi.REGISTER, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e( "data object",jsonObject+"" );
                if (jsonObject.getInt( "status" )==200) {
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
                    editor.apply();

                    // start HomeMember activity
//                    Navigator.of( this ).pushReplacement( Login.class );
                    Toast.makeText( this, "Register Berhasil", Toast.LENGTH_SHORT ).show();
                }else if (jsonObject.getInt( "status" )==409) {
                    Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }  else {
                    Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
            if (error instanceof NetworkError) {
                Toast.makeText(this, "No network available", Toast.LENGTH_SHORT).show();

            } else if (error instanceof ServerError) {
                Toast.makeText(this, "Server error", Toast.LENGTH_SHORT).show();

            } else if (error instanceof AuthFailureError) {
                Toast.makeText(this, "Auth failure", Toast.LENGTH_SHORT).show();

            } else if (error instanceof ParseError) {
                Toast.makeText(this, "Parse error", Toast.LENGTH_SHORT).show();

            } else if (error instanceof TimeoutError) {
                Toast.makeText(this, "Timeout error", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Unknown error", Toast.LENGTH_SHORT).show();

            }

        }) {
            // add parameters to POST request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("username", et_username.getText().toString().trim());
                params.put( "name",et_name.getText().toString() );
                params.put( "email",et_email.getText().toString() );
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
    private boolean validate(){
        String username = et_username.getText().toString();
        String name = et_name.getText().toString();
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        if (username.isEmpty()){
            txtLayoutUsername.setErrorEnabled( true );
            txtLayoutUsername.setError( "Mohon isi Username anda" );
            return false;
        }
        if (name.isEmpty()){
            txtLayoutname.setErrorEnabled( true );
            txtLayoutname.setError( "Mohon isi nama lengkap anda" );
            return false;
        }
        if (!isEmailValid(email)){
            return false;
        }
        if (!isPasswordValid( password )){
            return false;
        }

        return true;
    }
    private boolean isPasswordValid(String password){
        if (password.isEmpty()) {
            txtLayoutPassword.setErrorEnabled(true);
            txtLayoutPassword.setError("Mohon isi password anda");
            txtLayoutPassword.setErrorIconDrawable(null);
            return false;
        } else if (password.length() < 8) {
            txtLayoutPassword.setErrorEnabled(true);
            txtLayoutPassword.setError("Password minimal harus 8 karakter");
            txtLayoutPassword.setErrorIconDrawable(null);
            return false;
        } else if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
            txtLayoutPassword.setErrorEnabled(true);
            txtLayoutPassword.setError("Password harus terdiri dari kombinasi huruf besar, huruf kecil, angka, dan karakter seperti @#$%^&+=");
            txtLayoutPassword.setErrorIconDrawable(null);
            return false;
        } else {
            txtLayoutPassword.setErrorEnabled(false);
            txtLayoutPassword.setErrorIconDrawable(null);
            return true;
        }

    }
    private boolean isEmailValid(String email) {
        if (email.isEmpty()) {
            txtLayoutEmail.setErrorEnabled(true);
            txtLayoutEmail.setError("Mohon isi email anda");
            return false;
        } else {
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+(\\.[a-z]+)?";
            if (!email.matches(emailPattern)) {
                txtLayoutEmail.setErrorEnabled(true);
                txtLayoutEmail.setError("Email tidak valid");
                return false;
            } else {
                txtLayoutUsername.setErrorEnabled(false);
                return true;
            }
        }
    }
    private void setToolbar(){
        toolbar = findViewById( R.id.backIconL );
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        Navigator.of(this).pop();
        return true;
    }

}