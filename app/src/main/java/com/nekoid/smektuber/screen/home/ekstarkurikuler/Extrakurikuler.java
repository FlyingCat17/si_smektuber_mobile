package com.nekoid.smektuber.screen.home.ekstarkurikuler;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.adapter.AdapterDataExtra;
import com.nekoid.smektuber.api.Endpoint;
import com.nekoid.smektuber.api.PublicApi;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.app.BaseActivity;
import com.nekoid.smektuber.helpers.listener.ScrollListener;
import com.nekoid.smektuber.helpers.utils.State;
import com.nekoid.smektuber.helpers.utils.Utils;
import com.nekoid.smektuber.models.ExtracurricularModel;
import com.nekoid.smektuber.models.Pagination;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.network.Response;
import com.nekoid.smektuber.helpers.thread.Threads;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Extrakurikuler extends BaseActivity {

    private Pagination pagination;

    private List<ExtracurricularModel> extracurricularModels = new ArrayList<>();

    private final AdapterDataExtra adapterDataExtra = new AdapterDataExtra(this, extracurricularModels);

    private RecyclerView recyclerView;
    boolean withAnimation = true;
    private Toolbar toolbar;

    private boolean isScroll = false, isFromState = false;
    ShimmerFrameLayout shimmerFrameLayout;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onLoad();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extrakurikuler);
        startShimmer();
        setToolbar();
        shimmerFrameLayout = findViewById(R.id.rvDataExtrakurikulerShimmer);
        recyclerView = findViewById(R.id.rvDataExtra);
        recyclerView.setAdapter(adapterDataExtra);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(scrollListener());
        if (!State.extracurricularModels.isEmpty()) {
            isFromState = true;
            withAnimation = false;
            extracurricularModels.addAll(State.extracurricularModels);
            setAdapterExtracurricular();
            openRequest();
        } else {
            withAnimation = true;
            isFromState = false;
            openRequest();
        }
        openRequest();
    }

    private void openRequest() {
        Http.get(Endpoint.LIST_EXTRACURRICULAR.getUrl(), PublicApi.getHeaders(), this::onResponse);
    }

    private void startShimmer() {
        // start shimmer
    }

    private void stopShimmer() {
        // stop shimmer
        shimmerFrameLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        if (withAnimation) recyclerView.setAnimation(Utils.animation());
    }

    protected final void onResponse(Response response) {
        if (response.statusCode != 200) {
            return;
        }
        try {
            JSONObject rawBody = new JSONObject(response.body.toString());
            if (isFromState) {
                pagination = Pagination.fromJson(rawBody.getJSONObject("pagination"));
                isFromState = false;
                return;
            }
            pagination = Pagination.fromJson(rawBody.getJSONObject("pagination"));
            JSONArray listExtra = rawBody.getJSONArray("data");
            for (int i = 0; i < listExtra.length(); i++) {
                JSONObject json = listExtra.getJSONObject(i);
                extracurricularModels.add(ExtracurricularModel.fromJson(json));
            }
            setAdapterExtracurricular();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    protected void onLastScroll() {
        if (pagination != null && pagination.nextPageUrl != null && !pagination.nextPageUrl.isEmpty() && !pagination.nextPageUrl.equals("null")) {
            isScroll = false;
            Http.get(pagination.nextPageUrl, PublicApi.getHeaders(), this::onResponse);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    protected final void setAdapterExtracurricular() {
        isScroll = true;
        if (State.extracurricularModels.size() <= 10) {
            for (ExtracurricularModel model : extracurricularModels) {
                if (State.extracurricularModels.size() >= 10) break;
                State.extracurricularModels.add(model);
            }
        }
        adapterDataExtra.notifyDataSetChanged();
    }

    private ScrollListener scrollListener() {
        return new ScrollListener() {
            @Override
            public void onScroll(@NonNull RecyclerView recyclerView, int horizontal, int vertical, int newState) {
                if (!recyclerView.canScrollVertically(2) && isScroll) {
                    onLastScroll();
                }
            }
        };
    }

    private void onLoad() {
        Threads.execute((executor, handler) -> {
            executor.execute(() -> {
                while (!adapterDataExtra.isLoad()) {
                    // don't delete this line;
                }
                handler.post(this::stopShimmer);
            });
        });
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