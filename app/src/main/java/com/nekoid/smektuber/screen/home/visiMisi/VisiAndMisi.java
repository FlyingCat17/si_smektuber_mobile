package com.nekoid.smektuber.screen.home.visiMisi;

import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.*;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.api.*;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.app.BaseActivity;
import com.nekoid.smektuber.helpers.utils.State;
import com.nekoid.smektuber.helpers.utils.Utils;
import com.nekoid.smektuber.models.AboutModel;
import com.nekoid.smektuber.network.*;

import org.json.*;

public class VisiAndMisi extends BaseActivity {

    private TextView visi, misi;

    private Toolbar toolbar;

    private LinearLayout cardVisi, cardMisi;

    private ScrollView contentVM;
    boolean withAnimation = true;
    private ShimmerFrameLayout shimmerFrameLayout;

    private ScrollView swipeRefreshLayout;

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
            withAnimation = false;
            openRequest();
        } else {
            withAnimation = true;
            openRequest();
        }
    }

    private void openRequest() {
        Http.get(Endpoint.ABOUT.getUrl(), PublicApi.getHeaders(), this::onResponse);
    }

    private void startShimmer() {
        // start shimmer
    }

    private void stopShimmer() {
        // stop shimmer
        shimmerFrameLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        if (withAnimation) swipeRefreshLayout.setAnimation(Utils.animation());
    }

    private void setVariable() {
        aboutModel = (AboutModel) Navigator.getArgs( this );
        visi = findViewById(R.id.TxtVisi);
        misi = findViewById(R.id.TxtMisi);
        cardVisi = findViewById( R.id.cardVisi );
        cardMisi = findViewById( R.id.cardMisi );
        shimmerFrameLayout = findViewById(R.id.progressBarShimmer);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
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
            setModelToView(aboutModel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void setModelToView(AboutModel aboutModel) {
        State.aboutModel = aboutModel;
        visi.setText(Utils.fromHtml(aboutModel.schoolVision));
        misi.setText(Utils.fromHtml(aboutModel.schoolMission));
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
