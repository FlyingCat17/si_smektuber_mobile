package com.nekoid.smektuber.screen.home.about;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.api.Endpoint;
import com.nekoid.smektuber.api.PublicApi;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.helpers.utils.State;
import com.nekoid.smektuber.helpers.utils.Utils;
import com.nekoid.smektuber.models.AboutModel;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.network.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class AboutSchool extends BaseActivity {

    private ImageView BtnFB, BtnIG, BtnYoutube, BtnTT, headMasterPhoto, imageSchool;

    private TextView headmaster, accreditation, schoolDescription;

    private Toolbar toolbar;

    private AboutModel aboutModel;

    private String facebook, instagram, youtube, tiktok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_school);
        startShimmer();
        setVariable();
        init();
        setToolbar();
        if (State.aboutModel != null) {
            setDataToView(State.aboutModel);
        } else {
            Http.get(Endpoint.ABOUT.getUrl(), PublicApi.getHeaders(), this::onResponse);
        }
    }

    private void startShimmer() {
        // start shimmer
    }

    private void stopShimmer() {
        // stop shimmer
    }

    private void init() {
        BtnFB = findViewById(R.id.FaceBook);
        BtnIG = findViewById(R.id.Instagram);
        BtnYoutube = findViewById(R.id.Youtube);
        BtnTT = findViewById(R.id.Tiktok);
        toolbar = findViewById(R.id.backIcon);

        BtnFB.setOnClickListener(v -> Navigator.openApp(this, Utils.toUri(facebook)));

        BtnIG.setOnClickListener(v -> Navigator.openApp(this, Utils.toUri(instagram)));

        BtnYoutube.setOnClickListener(v -> Navigator.openApp(this, Utils.toUri(youtube)));

        BtnTT.setOnClickListener(v -> Navigator.openApp(this, Utils.toUri(tiktok)));
    }

    protected void onResponse(Response response) {
        if (response.statusCode != 200) {
            return;
        }

        try {
            JSONObject body = new JSONObject(response.body.toString());
            if (body.getInt("status") != 200) {
                return;
            }
            AboutModel aboutModel = AboutModel.fromJson(body.getJSONObject("data"));
            setDataToView(aboutModel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setDataToView(AboutModel aboutModel) {
        State.aboutModel = aboutModel;
        Http.loadImage(aboutModel.schoolLogo, imageSchool, () -> {
            Http.loadImage(aboutModel.schoolHeadmasterPicture, headMasterPhoto, () -> {
                headmaster.setText(aboutModel.schoolHeadmasterName);
                schoolDescription.setText(aboutModel.schoolHistory);
                accreditation.setText(aboutModel.schoolAccreditation);
                facebook = aboutModel.schoolFacebook;
                instagram = aboutModel.schoolInstagram;
                youtube = aboutModel.schoolYoutube;
                stopShimmer();
            });
        });
    }

    private void setVariable() {
        aboutModel = (AboutModel) Navigator.getArgs(this);
        headmaster = findViewById(R.id.TxtNameHeadMaster);
        headMasterPhoto = findViewById(R.id.ImageHeadMaster);
        accreditation = findViewById(R.id.Akreditasi);
        schoolDescription = findViewById(R.id.TxtAboutSchool);
        imageSchool = findViewById(R.id.ImageSchool);
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