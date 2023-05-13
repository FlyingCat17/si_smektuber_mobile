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
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.config.volley.UrlsApi;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.screen.auth.Login;
import com.nekoid.smektuber.screen.auth.Register;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VisiAndMisi extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
//    Dashboard dashboard = new Dashboard();
    private TextView tvVisi , tvMisi;
    Toolbar toolbar;
    private ProgressBar progressBar;
    private LinearLayout cardVisi, cardMisi;
    private ScrollView contentVM;
    private Cache cache;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isDataLoaded = false;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visi_and_misi);
        setToolbar();
        init();
        fetchData();
    }
    private void init(){
        //
        progressBar = findViewById( R.id.progressBar );
        tvVisi = findViewById( R.id.TxtVisi );
        tvMisi = findViewById( R.id.TxtMisi );
        cardVisi = findViewById( R.id.cardVisi );
        cardMisi = findViewById( R.id.cardMisi );
        contentVM = findViewById( R.id.cVM );
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener( (SwipeRefreshLayout.OnRefreshListener) this );
        cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        // Set up network request queue
        Network network = new BasicNetwork(new HurlStack());
        queue = new RequestQueue(cache, network);
        queue.start();

    }
    private void fetchData(){
        String url = UrlsApi.ABOUT;

        SharedPreferences userPref = getSharedPreferences("user", MODE_PRIVATE);
        String accessToken = userPref.getString("access_token", "");

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);
        progressBar.setVisibility(View.VISIBLE);
        contentVM.setVisibility( View.GONE );
        JsonObjectRequest request = new JsonObjectRequest( Request.Method.GET, url, null, response -> {
            progressBar.setVisibility(View.GONE);
            contentVM.setVisibility( View.VISIBLE );

            try {
                JSONObject data = response.getJSONObject("data");
                String visi = data.getString("vission");
                String misi = data.getString("mission");

                tvVisi.setText(visi);
                tvMisi.setText(misi);

                // Save data to cache
                Cache.Entry entry = new Cache.Entry();
                entry.data = response.toString().getBytes();
                entry.softTtl = System.currentTimeMillis() + 5 * 60 * 1000; // 5 minutes
                entry.ttl = System.currentTimeMillis() + 60 * 60 * 1000; // 1 hour
                cache.put(url, entry);

                // Set data loaded flag to true
                isDataLoaded = true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            // handle error
            progressBar.setVisibility(View.GONE);
            contentVM.setVisibility( View.VISIBLE );
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
    @Override
    public void onRefresh() {
        // Clear cache before fetching data
        cache.clear();

        // Fetch data
        fetchData();

        swipeRefreshLayout.setRefreshing(false);
    }
    @Override
    protected void onResume() {
        super.onResume();

        String url = UrlsApi.ABOUT;
        Cache.Entry entry = cache.get(url);

        if (entry != null) {
            try {
                // Load data from cache
                JSONObject response = new JSONObject(new String(entry.data));
                JSONObject data = response.getJSONObject("data");
                String visi = data.getString("vission");
                String misi = data.getString("mission");

                // Update UI with cached data
                tvVisi.setText(visi);
                tvMisi.setText(misi);

                // Set data loaded flag to true
                isDataLoaded = true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (!isDataLoaded) {
            // Fetch data if not in cache
            fetchData();
        }

    }
//    @Override
//    protected void onStop() {
//        super.onStop();
////        RequestQueue queue = null;
//        queue.cancelAll(this);
//    }
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