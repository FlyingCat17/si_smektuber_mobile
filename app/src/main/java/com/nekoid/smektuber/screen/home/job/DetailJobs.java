package com.nekoid.smektuber.screen.home.job;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.api.Endpoint;
import com.nekoid.smektuber.api.ImageUrlUtil;
import com.nekoid.smektuber.api.PublicApi;
import com.nekoid.smektuber.app.BaseActivity;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.utils.Utils;
import com.nekoid.smektuber.models.ArticleModel;
import com.nekoid.smektuber.models.ExtracurricularModel;
import com.nekoid.smektuber.models.JobsModel;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.network.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailJobs extends BaseActivity {
    private Toolbar toolbar;
    JobsModel jobsModel;

    ImageView thumbnail;
    TextView description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jobs);
        setVariable();
        setToolbar();
        setArticleModel();
    }

    protected void onResponse(Response response) {
        if (response.statusCode != 200) {
            return;
        }

        try {
            JSONObject body = new JSONObject(response.body.toString());
            if (body.getInt("status") != 200) {
                return;
            }
            jobsModel = JobsModel.fromJson(body.getJSONObject("data"));
        } catch (JSONException e) {
        }
    }

    protected void setArticleModel() {
        Http.get(Endpoint.GET_JOB_BY_ID.getUrl() + jobsModel.id, PublicApi.getHeaders(), this::onResponse);
        String JobsImageUrl = jobsModel.thumbnail ;
        Http.loadImage( JobsImageUrl,thumbnail );
        CharSequence htmlDescEkstra = Utils.fromHtml( jobsModel.description );
        description.setText( htmlDescEkstra );
    }

    protected void setVariable() {
        jobsModel = (JobsModel) Navigator.getArgs(this);
        thumbnail = findViewById(R.id.ImageJobsDetail);
        description = findViewById(R.id.TextDetailJobs);
    }

    private void setToolbar(){
        toolbar = findViewById(R.id.backIconJobs);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Navigator.of(this).pop();
        return true;
    }
}