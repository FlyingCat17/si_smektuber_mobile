package com.nekoid.smektuber.screen.home.article;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.config.volley.Endpoint;
import com.nekoid.smektuber.config.volley.PublicApi;
import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.models.ArticleModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailArticle extends BaseActivity {

    ArticleModel articleModel;
    ImageView thumbnail;
    TextView description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);
        setVariable();
        addRequest(PublicApi.get(Endpoint.GET_ARTICLE_BY_ID.getUrl() + articleModel.id, onResponse()));
        runQueue();
        setArticleModel();
    }

    protected Response.Listener<String> onResponse() {
        return response -> {
            try {
                JSONObject body = new JSONObject(response);
                if (body.getInt("status") != 200) {
                    return;
                }
                articleModel = ArticleModel.fromJson(body.getJSONObject("data"));
            } catch (JSONException e) {
            }
        };
    }

    protected void setArticleModel() {
        description.setText(articleModel.description);
        Picasso.get().load(articleModel.thumbnail).into(thumbnail);
    }

    protected void setVariable() {
        articleModel = (ArticleModel) Navigator.getArgs(this);
        thumbnail = findViewById(R.id.ImageDetailArtikel);
        description = findViewById(R.id.TextArticle);
    }
}