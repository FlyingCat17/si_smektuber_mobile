package com.nekoid.smektuber.screen.home.visiMisi;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.config.volley.UrlsApi;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.screen.auth.Login;
import com.nekoid.smektuber.screen.auth.Register;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VisiAndMisi extends AppCompatActivity {
//    Dashboard dashboard = new Dashboard();
    private TextView tvVisi , tvMisi;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visi_and_misi);
        setToolbar();
        init();
        fetchData();
    }
    private void init(){
        //
        tvVisi = findViewById( R.id.TxtVisi );
        tvMisi = findViewById( R.id.TxtMisi );
    }
    private void fetchData(){
        String url = UrlsApi.ABOUT;

        SharedPreferences userPref = getSharedPreferences("user", MODE_PRIVATE);
        String accessToken = userPref.getString("access_token", "");

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);
        JsonObjectRequest request = new JsonObjectRequest( Request.Method.GET, url, null, response -> {
            try {
                JSONObject data = response.getJSONObject("data");
                String visi = data.getString("vission");
                String misi = data.getString("mission");

                tvVisi.setText(visi);
                tvMisi.setText(misi);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            // handle error
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
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