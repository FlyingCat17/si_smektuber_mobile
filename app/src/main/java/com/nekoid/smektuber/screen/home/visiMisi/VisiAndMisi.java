package com.nekoid.smektuber.screen.home.visiMisi;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.config.volley.Endpoint;
import com.nekoid.smektuber.config.volley.PublicApi;
import com.nekoid.smektuber.config.volley.UrlsApi;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.models.AboutModel;
import com.nekoid.smektuber.models.ArticleModel;
import com.nekoid.smektuber.models.MajorModel;
import com.nekoid.smektuber.screen.auth.Login;
import com.nekoid.smektuber.screen.auth.Register;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import android.util.Log;
public class VisiAndMisi extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private TextView visi, misi;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private LinearLayout cardVisi, cardMisi;
    private ScrollView contentVM;
    private Cache cache;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isDataLoaded = false;
    private RequestQueue queue;
    AboutModel aboutModel;
    private static final String TAG = "test :";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visi_and_misi);
        setToolbar();
        init();
        if (isDataLoaded) {
            setDataToView(aboutModel);
        } else {
            fetchData();
        }
    }

    private void init() {
//        AboutModel aboutModel = new AboutModel();
        progressBar = findViewById(R.id.progressBar);
        visi = findViewById(R.id.TxtVisi);
        misi = findViewById(R.id.TxtMisi);
        cardVisi = findViewById( R.id.cardVisi );
        cardMisi = findViewById( R.id.cardMisi );
        contentVM = findViewById(R.id.cVM);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        // Set up network request queue
//        Network network = new BasicNetwork(new HurlStack());
//        queue = new RequestQueue(cache, network);
//        queue.start();
    }

    protected Response.Listener<JSONObject> onResponse() {
        return response -> {
            // handle response
            Log.d(TAG, "onResponse");
            progressBar.setVisibility(View.GONE);
            contentVM.setVisibility(View.VISIBLE);
            try {
                AboutModel aboutModel = AboutModel.fromJson(response.getJSONObject("data"));
                setDataToView(aboutModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };
    }


    private void fetchData() {
//        addRequest(PublicApi.get(Endpoint.ABOUT.getUrl(), onResponse()));
        String url = Endpoint.ABOUT.getUrl();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, onResponse(),
                error -> {
                    // handle error
                    progressBar.setVisibility(View.GONE);
                    contentVM.setVisibility(View.VISIBLE);
                });
        runQueue();
    }

    private void setDataToView(AboutModel aboutModel) {
        visi.setText(aboutModel.getVisi());
        misi.setText(aboutModel.getMisi());
    }


    @Override
    public void onRefresh() {
        fetchData();
        swipeRefreshLayout.setRefreshing(false);
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
}
