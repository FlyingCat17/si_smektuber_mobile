package com.nekoid.smektuber.screen.home.article;

import androidx.appcompat.app.AppCompatActivity;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.adapter.AdapterData;
import com.nekoid.smektuber.config.volley.UrlsApi;
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

public class ArticleViewAll extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    List<ArticleModel> listArtcile = new ArrayList<ArticleModel>();
    AdapterData adapterData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_view_all);
        setToolbar();
        recyclerView = findViewById(R.id.rvData);
        getArticles();
    }
    private void setToolbar(){
        toolbar = findViewById( R.id.backIcon );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
    }

    @Override
    public boolean onSupportNavigateUp() {
        Navigator.of( this ).pop();
        return true;
    }

    protected void getArticles() {

        StringRequest request = new StringRequest(Request.Method.GET, UrlsApi.BASE_URL + "/article", response -> {
            try {
                JSONObject responses = new JSONObject(response);
                if (responses.getInt("status") == 200) {
                    JSONArray arrays = responses.getJSONArray("data");
                    if (arrays.length() > 0) {
                        for (int i = 0; i < arrays.length(); i++) {
                            String data = arrays.getString(i);
                            JSONObject object = new JSONObject(data);
                            String title = object.getString("title");
                            Bitmap image = Utils.downloadImage(object.getString("thumbnail"));
                            listArtcile.add(ArticleModel.fromJson(object));
                        }
                        adapterData = new AdapterData(this, listArtcile);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapterData);
                    }
                }
            } catch (JSONException e) {
            }
        }, error -> {
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + sharedPreferences.getString("access_token", ""));
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}