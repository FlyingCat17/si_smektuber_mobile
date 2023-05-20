package com.nekoid.smektuber.screen.home.visiMisi;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.api.*;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.helpers.utils.State;
import com.nekoid.smektuber.models.AboutModel;
import com.nekoid.smektuber.network.*;

import org.json.*;

public class VisiAndMisi extends BaseActivity {

    private TextView visi, misi;

    private Toolbar toolbar;

    private ProgressBar progressBar;

    private LinearLayout cardVisi, cardMisi;

    private ScrollView contentVM;

    private boolean isDataLoaded = false;

    AboutModel aboutModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visi_and_misi);
        startShimmer();
        setToolbar();
        setVariable();
        if (State.aboutModel != null) {
            setModelToView(State.aboutModel);
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

    private void setVariable() {
        aboutModel = (AboutModel) Navigator.getArgs( this );
        progressBar = findViewById(R.id.progressBar);
        visi = findViewById(R.id.TxtVisi);
        misi = findViewById(R.id.TxtMisi);
        cardVisi = findViewById( R.id.cardVisi );
        cardMisi = findViewById( R.id.cardMisi );
        contentVM = findViewById(R.id.cVM);
    }

    protected void onResponse(Response response) {
        progressBar.setVisibility(View.GONE);
        contentVM.setVisibility(View.VISIBLE);

        if (response.statusCode != 200) {
            return;
        }

        try {
            JSONObject body = new JSONObject(response.body.toString());
            if (body.getInt("status") != 200) {
                return;
            }
            AboutModel aboutModel = AboutModel.fromJson(body.getJSONObject("data"));
            setModelToView(aboutModel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void setModelToView(AboutModel aboutModel) {
        State.aboutModel = aboutModel;
        visi.setText(aboutModel.schoolVision);
        misi.setText(aboutModel.schoolMission);
        stopShimmer();
    }

    private void setToolbar(){
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
