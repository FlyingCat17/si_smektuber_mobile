package com.nekoid.smektuber.screen.home.jurusan;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Response;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.adapter.AdapterDataExtra;
import com.nekoid.smektuber.adapter.AdapterDataJurusan;
import com.nekoid.smektuber.config.volley.Endpoint;
import com.nekoid.smektuber.config.volley.PublicApi;
import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.models.ExtracurricularModel;
import com.nekoid.smektuber.models.MajorModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Jurusan extends BaseActivity {
    private RecyclerView recyclerView;
    private List<MajorModel> majorModels = new ArrayList<>();
    private AdapterDataJurusan adapterDataJurusan;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jurusan);
        recyclerView = findViewById(R.id.rvDataJurusan);

        addRequest(PublicApi.get(Endpoint.LIST_MAJOR.getUrl(), onResponse()));
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
                    majorModels.add(MajorModel.fromJson(new JSONObject(listExtra.getString(i))));
                }
                setAdapterExtracurricular();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        };
    }

    protected final void setAdapterExtracurricular() {
        adapterDataJurusan = new AdapterDataJurusan(this, majorModels);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterDataJurusan);
    }
}