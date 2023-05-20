package com.nekoid.smektuber.screen.home.jurusan;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.adapter.AdapterDataJurusan;
import com.nekoid.smektuber.api.*;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.models.MajorModel;
import com.nekoid.smektuber.network.*;

import org.json.*;

import java.util.*;

public class Jurusan extends BaseActivity {
    private RecyclerView recyclerView;
    private ShimmerFrameLayout shimmerFrameLayout;
    private Toolbar toolbar;
    private List<MajorModel> majorModels = new ArrayList<>();
    private AdapterDataJurusan adapterDataJurusan;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jurusan);
        shimmerFrameLayout = findViewById(R.id.rvDataJurusanShimmer);
        shimmerFrameLayout.startShimmer();
        recyclerView = findViewById(R.id.rvDataJurusan);
        Http.get(Endpoint.LIST_MAJOR.getUrl(), PublicApi.getHeaders(), this::onResponse);
        setToolbar();
    }

    protected final void onResponse(Response response) {

        if (response.statusCode != 200) {
            return;
        }
        try {
            JSONObject rawBody = new JSONObject(response.body.toString());
            if (rawBody.getInt("status") != 200) {
                return;
            }
            JSONArray listExtra = rawBody.getJSONArray("data");
            for (int i = 0; i < listExtra.length(); i++) {
                majorModels.add(MajorModel.fromJson(new JSONObject(listExtra.getString(i))));
            }
            setAdapterExtracurricular();
            Animation animation = new AlphaAnimation(0,1);
            animation.setDuration(1000);
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAnimation(animation);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    protected final void setAdapterExtracurricular() {
        adapterDataJurusan = new AdapterDataJurusan(this, majorModels);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterDataJurusan);
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
    @Override
    protected void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    protected void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }

}