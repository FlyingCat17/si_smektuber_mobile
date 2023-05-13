package com.nekoid.smektuber.screen.home.ekstarkurikuler;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Response;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.adapter.AdapterDataExtra;
import com.nekoid.smektuber.config.volley.Endpoint;
import com.nekoid.smektuber.config.volley.PublicApi;
import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.models.ExtracurricularModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Extrakurikuler extends BaseActivity {
    private RecyclerView recyclerView;
    private AdapterDataExtra adapterDataExtra;

    private List<ExtracurricularModel> extracurricularModels = new ArrayList<>();
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extrakurikuler);
        recyclerView = findViewById(R.id.rvDataExtra);
        addRequest(PublicApi.get(Endpoint.LIST_EXTRACURRICULAR.getUrl(), onResponse()));
        runQueue();
    }

    protected final Response.Listener<String> onResponse() {
        return response -> {
            try {
                JSONObject rawBody = new JSONObject(response);
                if (rawBody.getInt("status") != 200) {
                    return;
                }
                JSONArray listExtra = rawBody.getJSONArray("data");
                for (int i = 0; i < listExtra.length(); i++) {
                    extracurricularModels.add(ExtracurricularModel.fromJson(new JSONObject(listExtra.getString(i))));
                }
                setAdapterExtracurricular();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        };
    }

    protected final void setAdapterExtracurricular() {
        adapterDataExtra = new AdapterDataExtra(this, extracurricularModels);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterDataExtra);
    }
}