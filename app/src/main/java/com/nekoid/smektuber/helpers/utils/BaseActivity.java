package com.nekoid.smektuber.helpers.utils;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nekoid.smektuber.config.volley.Endpoint;
import com.nekoid.smektuber.config.volley.PublicApi;
import com.nekoid.smektuber.models.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Base activity</p>
 * 
 * <p>We can use this class to extend activity</p>
 * <p>And we use this class to handle refresh token</p>
 * 
 * @see com.nekoid.smektuber.screen.auth.Login
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * <p>Access token</p>
     * 
     * <p>Use this to access api</p>
     */
    private static final String accessToken = "accessToken";

    /**
     * <p>Token expired at</p>
     * 
     * <p>Use this to check token expired</p>
     */
    private static final String tokenExpiredAt = "expiredAt";

    /**
     * <p>Request queue</p>
     * 
     * <p>Use for run queue</p>
     */
    private RequestQueue requestQueue;

    /**
     * <p>Request list</p>
     * 
     * <p>Use for add request to queue</p>
     * 
     * @see #addRequest(StringRequest)
     */
    private List<StringRequest> requestList = new ArrayList<>();

    private boolean isLogin = false;

    /**
     * <p>Base url</p>
     * 
     * <p>Use for set base url</p>
     */
    private static final String BASE_URL = "https://lutfisobri.my.id/api/v1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        PublicApi.setBaseActivity(this);
        LocalStorage.setCache(new Cache(getCacheDir()));
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (requestQueue != null) {
            requestQueue.stop();
        }
        super.onDestroy();
    }

    public boolean isLogin() {
        return isLogin;
    }

    /**
     * <p>Add request to queue</p>
     * 
     * <p>Use this to add request to queue</p>
     * <p>And then run queue</p>
     * 
     * @param request
     */
    protected final void addRequest(StringRequest request) {
        requestList.add(request);
    }

    /**
     * <p>Run queue</p>
     * 
     * <p>Use this to run queue</p>
     * 
     * @see #addRequest(StringRequest)
     */
    protected final void runQueue() {
        // Instantiate the RequestQueue.
        requestQueue = Volley.newRequestQueue(this);

        // Add request to queue
        for (int i = 0; i < requestList.size(); i++) {
            requestQueue.add(requestList.get(i));
        }
    }

    /**
     * <p>Request token</p>
     * 
     * <p>Use this to request token</p>
     * 
     * @see #doRefreshToken()
     */
    public final void doRefreshToken() {
        __requestToken();
        PublicApi.addToken(getToken(), getExpired());
    }

    /**
     * <p>Set access token</p>
     * 
     * <p>Use this to set access token</p>
     * 
     * @param token
     * @param expired
     */
    public final void setAccessToken(String token, int expired) {
        PublicApi.addToken(token, expired);
        getTokenPreferences().edit().putString(accessToken, token).putInt(tokenExpiredAt, expired).apply();
    }

    /**
     * <p>Get access token</p>
     * 
     * <p>When we get access token, we will get from preferences</p>
     * 
     * @return String
     */
    protected String getToken() {
        return this.getTokenPreferences().getString(accessToken, "");
    }

    /**
     * <p>Get expired</p>
     * 
     * <p>When we get expired, we will get from preferences</p>
     * 
     * @return int
     */
    private int getExpired() {
        return this.getTokenPreferences().getInt(tokenExpiredAt, 0);
    }

    /**
     * <p>Get token preferences</p>
     * 
     * <p>When we get token preferences, we will get from preferences</p>
     * 
     * @return SharedPreferences
     */
    private SharedPreferences getTokenPreferences() {
        return getSharedPreferences("__accessToken", MODE_PRIVATE);
    }

    /**
     * <p>Get auth preferences</p>
     * 
     * <p>When we get auth preferences, we will get from preferences</p>
     * 
     * @return SharedPreferences
     */
    public final SharedPreferences getAuthPreferences() {
        return getSharedPreferences("__auth", MODE_PRIVATE);
    }

    protected final String getCredentials() {
        return getAuthPreferences().getString("_credentials", "");
    }

    /**
     * <p>Get user preferences</p>
     * 
     * <p>When we get user preferences, we will get from preferences</p>
     * 
     * @return SharedPreferences
     */
    public final SharedPreferences getUserPreferences() {
        return getSharedPreferences("user", MODE_PRIVATE);
    }

    /**
     * <p>Set Auth from shared preferences</p>
     * 
     * <p>When we set auth from shared preferences, we will set to preferences</p>
     * 
     * @param username
     * @param credentials
     */
    public final void setAuthPreferences(String username, String credentials) {
        getAuthPreferences().edit().putString("_username", username).putString("_credentials", credentials).apply();
    }

    /**
     * <p>Set user preferences</p>
     * 
     * <p>When we set user preferences, we will set to preferences</p>
     * 
     * @param name
     * @param username
     * @param email
     * @param role
     * @param emailVerified
     * @param isLogin
     */
    public final void setUserPreferences(String name, String username, String email, String role, boolean emailVerified, boolean isLogin) {
        getUserPreferences().edit()
                    .putString("name", name)
                    .putString("username", username)
                    .putString("email", email)
                    .putString("role", role)
                    .putBoolean("emailVerified", emailVerified)
                    .putBoolean("isLogin", isLogin)
                    .apply();
    }

    /**
     * <p>Get user preferences</p>
     * 
     * <p>When we get user preferences, we will get from preferences</p>
     * 
     * @return User
     */
    private void __requestToken() {
        // Request a string response from the provided URL.
        StringRequest refreshToken = new StringRequest(Request.Method.POST, BASE_URL + Endpoint.REFRESH_TOKEN.getUrl(), __onResponseToken(), null) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + getToken());
                return params;
            }
        };

        // Add the request to the RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(refreshToken);
    }

    /**
     * <p>On response error</p>
     * 
     * <p>When we get response error, we will get from preferences</p>
     * 
     * @return Response.ErrorListener
     */
    private Response.Listener<String> __onResponseToken() {
        return response -> {
            try {
                JSONObject responseBody = new JSONObject(response);
                if (responseBody.getInt("status") == 200) {
                    // Get data from response
                    JSONObject body = responseBody.getJSONObject("data");

                    // Get token and expired
                    String token = body.getString("access_token");
                    int expired = body.getInt("expires_in");

                    // Set access token
                    setAccessToken(token, expired);
                }
            } catch (JSONException e) {
            }
        };
    }

    /**
     * <p>Do login</p>
     * 
     * <p>When we do login, we will do request to server</p>
     * 
     * @param username
     * @param password
     */
    public final void doLogin(String username, String password) {
        StringRequest doAuth = new StringRequest(Request.Method.POST,  BASE_URL + Endpoint.LOGIN.getUrl(), __onResponseAuth(username, password), error -> {onResponseError(error);}) {

            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                return getHeader();
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(doAuth);
    }

    /**
     * <p>On response error</p>
     * 
     * <p>When we get response error, we will get from preferences</p>
     * 
     * @return Response.ErrorListener
     */
    protected void onResponseError(VolleyError error) {

    }

    /**
     * <p>On response auth</p>
     * 
     * <p>When we get response auth, we will get from preferences</p>
     * 
     * @param username
     * @param password
     * @return Response.Listener<String>
     */
    private Response.Listener<String> __onResponseAuth(String username, String password) {
        return response -> {
            try {
                JSONObject responseBody = new JSONObject(response);
                if (responseBody.getInt("status") == 200) {
                    this.isLogin = true;
                    setAuthPreferences(username, password);
                    JSONObject body = responseBody.getJSONObject("data");
                    responseAuthDataToToken(body);
                    JSONObject user = body.getJSONObject("user");
                    UserModel userModel = UserModel.fromJson(user);
                    assert userModel != null;
                    LocalStorage.setUserModel(userModel);
                    userModelToPreferences(userModel);
                } else {
                    onLoginError(responseBody);
                }
            } catch (JSONException e) {
            }
        };
    }

    /**
     * <p>On login error</p>
     * 
     * <p>When we get login error, we will get from preferences</p>
     * 
     * @param response
     * @throws JSONException
     */
    protected void onLoginError(JSONObject response) throws JSONException {
        // Do something if login error
    }

    /**
     * <p>Response auth data to token</p>
     * 
     * <p>When we get response auth data to token, we will get from preferences</p>
     * 
     * @param auth
     * @throws JSONException
     */
    private void responseAuthDataToToken(JSONObject auth) throws JSONException {
        setAccessToken(auth.getString("access_token"), auth.getInt("expires_in"));
    }

    /**
     * <p>Merge user model to preferences</p>
     * 
     * <p>When we merge user model to preferences, we will get from preferences</p>
     * 
     * @param userModel
     */
    private void userModelToPreferences(UserModel userModel) {
        setUserPreferences(userModel.name, userModel.username, userModel.email, userModel.role, false, true);
    }

    /**
     * <p>Get header</p>
     * 
     * <p>When we get header, we will get from preferences</p>
     * 
     * @return Map<String, String>
     */
    private Map<String, String> getHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        return headers;
    }

    /**
     * <p> the function which triggered when the VALIDATE button is clicked </p>
     * <p>which validates the email address entered by the user</p>
     */
    public final boolean emailValidator(TextInputLayout textInputLayout, TextInputEditText textInputEditText) {

        // extract the entered data from the EditText
        String emailToText = textInputEditText.getText().toString();

        // Android offers the inbuilt patterns which the entered
        // data from the EditText field needs to be compared with
        // In this case the entered data needs to compared with
        // the EMAIL_ADDRESS, which is implemented same below
        if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
            textInputLayout.setErrorEnabled(false);
            return true;
        }
        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError("silahkan isi email dengan benar");
        return false;
    }
}
