package com.nekoid.smektuber.screen.home.ekstarkurikuler;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.adapter.AdapterDataExtra;
import com.nekoid.smektuber.api.Endpoint;
import com.nekoid.smektuber.api.PublicApi;
import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.models.ExtracurricularModel;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.network.Response;

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
        Http.get(Endpoint.LIST_EXTRACURRICULAR.getUrl(), PublicApi.getHeaders(), this::onResponse);
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
                extracurricularModels.add(ExtracurricularModel.fromJson(new JSONObject(listExtra.getString(i))));
            }
            setAdapterExtracurricular();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    protected final void setAdapterExtracurricular() {
        adapterDataExtra = new AdapterDataExtra(this, extracurricularModels);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterDataExtra);
    }
}