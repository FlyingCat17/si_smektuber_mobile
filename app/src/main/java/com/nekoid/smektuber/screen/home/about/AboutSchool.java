package com.nekoid.smektuber.screen.home.about;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.config.volley.Endpoint;
import com.nekoid.smektuber.config.volley.PublicApi;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.models.AboutModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class AboutSchool extends BaseActivity {
   private ImageView BtnFB, BtnIG, BtnYoutube, BtnTT, headMasterPhoto, imageSchool;
   private TextView headmaster, accreditation, schoolDescription;
   private Toolbar toolbar;
   private AboutModel aboutModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_school);
        addRequest(PublicApi.get(Endpoint.ABOUT.getUrl() , onResponse()));
        runQueue();
        setVariable();
        init();
        setToolbar();
    }
    private void init(){
        BtnFB = findViewById(R.id.FaceBook);
        BtnIG = findViewById(R.id.Instagram);
        BtnYoutube = findViewById(R.id.Youtube);
        BtnTT = findViewById(R.id.Tiktok);
        toolbar = findViewById( R.id.backIcon );

        BtnFB.setOnClickListener(v -> {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/profile.php?id=100090674252646&mibextid=ZbWKwL"));
            startActivity(intent);
        });

        BtnIG.setOnClickListener(v -> {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/smkn_7_jember_official/"));
            startActivity(intent);
        });

        BtnYoutube.setOnClickListener(v -> {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/channel/UC3m5Ou436WR_phX72cexPHg"));
            startActivity(intent);
        });

        BtnTT.setOnClickListener(v -> {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("https://www.tiktok.com/@smknegeri7jember"));
            startActivity(intent);
        });
    }
    protected Response.Listener<String> onResponse() {
        return response -> {
            // handle response
//            Log.d(TAG, "onResponse");
//            progressBar.setVisibility( View.GONE);
//            contentVM.setVisibility(View.VISIBLE);

            try {
                JSONObject body = new JSONObject( response );
                if (body.getInt("status") != 200) {
                    return;
                }
                AboutModel aboutModel = AboutModel.fromJson(body.getJSONObject("data"));
                setDataToView(aboutModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };
    }
    private void setDataToView(AboutModel aboutModel) {
        headmaster.setText( aboutModel.headMasterName );
        schoolDescription.setText( aboutModel.schoolDescription );
        accreditation.setText( aboutModel.accreditation );
        Picasso.get().load( aboutModel.headMasterPhoto).into( headMasterPhoto );
        Picasso.get().load( aboutModel.schoolPhoto ).into( imageSchool );
    }
    private void setVariable(){
        aboutModel = (AboutModel) Navigator.getArgs( this );
        headmaster = findViewById( R.id.TxtNameHeadMaster );
        headMasterPhoto = findViewById( R.id.ImageHeadMaster );
        accreditation = findViewById( R.id.Akreditasi );
        schoolDescription = findViewById( R.id.TxtAboutSchool );
        imageSchool = findViewById( R.id.ImageSchool );
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