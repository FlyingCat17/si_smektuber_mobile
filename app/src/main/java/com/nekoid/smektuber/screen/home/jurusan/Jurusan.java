package com.nekoid.smektuber.screen.home.jurusan;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.adapter.AdapterDataJurusan;
import com.nekoid.smektuber.api.*;
import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.models.MajorModel;
import com.nekoid.smektuber.network.*;

import org.json.*;

import java.util.*;

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
        Http.get(Endpoint.LIST_MAJOR.getUrl(), PublicApi.getHeaders(), this::onResponse);
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
}