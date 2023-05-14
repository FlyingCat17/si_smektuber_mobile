package com.nekoid.smektuber.screen.home.visiMisi;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.config.volley.Endpoint;
import com.nekoid.smektuber.config.volley.PublicApi;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.models.AboutModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import android.util.Log;
public class VisiAndMisi extends BaseActivity {
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
        addRequest(PublicApi.get(Endpoint.ABOUT.getUrl() , onResponse()));
        runQueue();
        setVariable();
//        if (isDataLoaded) {
//            setDataToView(aboutModel);
//        } else {
//            fetchData();
//        }
    }

    private void setVariable() {
//        AboutModel aboutModel = new AboutModel();
        aboutModel = (AboutModel) Navigator.getArgs( this );
        progressBar = findViewById(R.id.progressBar);
        visi = findViewById(R.id.TxtVisi);
        misi = findViewById(R.id.TxtMisi);
        cardVisi = findViewById( R.id.cardVisi );
        cardMisi = findViewById( R.id.cardMisi );
        contentVM = findViewById(R.id.cVM);
//        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
//        swipeRefreshLayout.setOnRefreshListener(this);


    }

    protected Response.Listener<String> onResponse() {
        return response -> {
            // handle response
            Log.d(TAG, "onResponse");
            progressBar.setVisibility(View.GONE);
            contentVM.setVisibility(View.VISIBLE);

            try {
                JSONObject body = new JSONObject( response );
                if (body.getInt("status") != 200) {
                    return;
                }
                AboutModel aboutModel = AboutModel.fromJson(body.getJSONObject("data"));
                setDataToView(aboutModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };
    }


    private void setDataToView(AboutModel aboutModel) {
        visi.setText( aboutModel.visi );
        misi.setText( aboutModel.misi );

    }

//
//    @Override
////    public void onRefresh() {
////        fetchData();
////        swipeRefreshLayout.setRefreshing(false);
////    }

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
