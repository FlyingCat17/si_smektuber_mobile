package com.nekoid.smektuber.helpers.utils;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nekoid.smektuber.api.Endpoint;
import com.nekoid.smektuber.api.PublicApi;
import com.nekoid.smektuber.models.UserModel;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.network.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    private boolean isLogin = false;

    /**
     * <p>Base url</p>
     *
     * <p>Use for set base url</p>
     */
    private static final String BASE_URL = "https://lutfisobri.my.id/api/v1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Utils.setBaseActivity(this);
        State.setCache(new Cache(getCacheDir()));
        super.onCreate(savedInstanceState);
    }

    protected final void deleteCache() {
        List<File> files = new ArrayList<>();
        files.add(getCacheDir());
        files.add(getExternalCacheDir());
        files.add(getCodeCacheDir());

        try {
            com.nekoid.smektuber.network.Cache cache = com.nekoid.smektuber.network.Cache.getInstance();
            cache.clear();
            for (File file : files) {
                deleteDir(file);
            }
        } catch (Exception e) { e.printStackTrace();}
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public boolean isLogin() {
        return isLogin;
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
    private void __requestToken() throws IOException {
        // Request a string response from the provided URL.
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getToken());
        Http.post(Endpoint.REFRESH_TOKEN.getUrl(), headers, this::__onResponseToken);
    }

    /**
     * <p>On response error</p>
     *
     * <p>When we get response error, we will get from preferences</p>
     *
     * @return Response.ErrorListener
     */
    private void __onResponseToken(Response response) {
        if (response.statusCode == 200) {
            try {
                JSONObject responseBody = new JSONObject(response.body.toString());
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
        }
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
        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);
        Http.post(Endpoint.LOGIN.getUrl(), null, body, response -> __onResponseAuth(response, username, password));
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
    private void __onResponseAuth(Response response, String username, String password) {
        if (response.statusCode != 200) {
            onLoginError(response);
            return;
        }
        try {
            JSONObject responseBody = new JSONObject(response.body.toString());
            if (responseBody.getInt("status") == 200) {
                this.isLogin = true;
                setAuthPreferences(username, password);
                JSONObject body = responseBody.getJSONObject("data");
                responseAuthDataToToken(body);
                JSONObject user = body.getJSONObject("user");
                UserModel userModel = UserModel.fromJson(user);
                assert userModel != null;
                State.setUserModel(userModel);
                userModelToPreferences(userModel);
            }
        } catch (JSONException e) {
        }
    }

    /**
     * <p>On login error</p>
     *
     * <p>When we get login error, we will get from preferences</p>
     *
     * @param response
     * @throws JSONException
     */
    protected void onLoginError(Response response) {
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
