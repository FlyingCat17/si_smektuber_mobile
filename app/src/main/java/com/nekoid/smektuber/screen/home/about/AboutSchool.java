package com.nekoid.smektuber.screen.home.about;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.config.volley.UrlsApi;
import com.nekoid.smektuber.helpers.navigation.Navigator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AboutSchool extends AppCompatActivity {
    ImageView BtnFB, BtnIG, BtnYoutube, BtnTT, IMGImageSchool, IMGImageHeadMaster;

    TextView TxtAbout, TxtNameHM, TxtAkreditasi, FB, IG, TT, YT;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_school);
        init();
        setToolbar();
        fetchData();

    }
    private void init(){
        BtnFB = findViewById(R.id.FaceBook);
        BtnIG = findViewById(R.id.Instagram);
        BtnYoutube = findViewById(R.id.Youtube);
        BtnTT = findViewById(R.id.Tiktok);
        toolbar = findViewById( R.id.backIcon );
        TxtAbout = findViewById(R.id.TxtAboutSchool);
        TxtNameHM = findViewById(R.id.TxtNameHeadMaster);
        TxtAkreditasi = findViewById(R.id.Akreditasi);
        IMGImageSchool = findViewById(R.id.ImageSchool);
        IMGImageHeadMaster = findViewById(R.id.ImageHeadMaster);

//        BtnFB.setOnClickListener(v -> {
//            String fb = FB.getText().toString();
//            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                    Uri.parse(fb));
//            startActivity(intent);
//        });
//
//        BtnIG.setOnClickListener(v -> {
//            String ig = IG.getText().toString();
//            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                    Uri.parse(ig));
//            startActivity(intent);
//        });
//
//        BtnYoutube.setOnClickListener(v -> {
//            String yt = YT.getText().toString();
//            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                    Uri.parse(yt));
//            startActivity(intent);
//        });

        BtnTT.setOnClickListener(v -> {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("https://www.tiktok.com/@smknegeri7jember"));
            startActivity(intent);
        });
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
                String about = data.getString("school_description");
                String nameHeadmaster = data.getString("headmaster_name");
                String akreditasi = data.getString("accreditation");
                String imageScholl = data.getString("school_photo");
                String imageHeadmaster = data.getString("headmaster_photo");
                String UrlFb = data.getString("facebook");
                String UrlIG = data.getString("instagram");
                String UrlYT = data.getString("youtube");


                TxtAbout.setText(about);
                TxtNameHM.setText(nameHeadmaster);
                TxtAkreditasi.setText(akreditasi);
//                FB.setText(UrlFb);
//                IG.setText(UrlIG);
//                YT.setText(UrlYT);

                Glide.with(AboutSchool.this)
                        .load(imageScholl)
                        .into(IMGImageSchool);
                Glide.with(AboutSchool.this)
                        .load(imageHeadmaster)
                        .into(IMGImageHeadMaster);

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
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        Navigator.of(this).pop();
        return true;
    }
}