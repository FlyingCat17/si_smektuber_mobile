package com.nekoid.smektuber.screen.home.article;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.adapter.AdapterData;
import com.nekoid.smektuber.config.volley.Endpoint;
import com.nekoid.smektuber.config.volley.PublicApi;
import com.nekoid.smektuber.config.volley.UrlsApi;
import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.helpers.utils.Utils;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.models.ArticleModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleViewAll extends BaseActivity {

    private RecyclerView recyclerView;

    List<ArticleModel> articleModelList = new ArrayList<ArticleModel>();

    AdapterData adapterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_view_all);
        setToolbar();
        recyclerView = findViewById(R.id.rvData);
        setAdapterData();
        addRequest(PublicApi.get(Endpoint.LIST_ARTICLE.getUrl(), listArticles()));
        runQueue();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.backIcon);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Navigator.of(this).pop();
        return true;
    }

    protected Response.Listener<String> listArticles() {
        return response -> {
            try {
                JSONObject responses = new JSONObject(response);
                if (responses.getInt("status") != 200) {
                    return;
                }
                JSONArray arrays = responses.getJSONArray("data");
                for (int i = 0; i < arrays.length(); i++) {
                    articleModelList.add(ArticleModel.fromJson(new JSONObject(arrays.getString(i))));
                }
                setAdapterData();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };
    }

    protected void setAdapterData() {
        adapterData = new AdapterData(this, articleModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterData);
    }
}