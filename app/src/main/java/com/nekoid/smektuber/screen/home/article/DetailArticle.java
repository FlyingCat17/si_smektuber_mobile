package com.nekoid.smektuber.screen.home.article;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.api.Endpoint;
import com.nekoid.smektuber.api.PublicApi;
import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.models.ArticleModel;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.network.Response;

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
        setArticleModel();
        Http.get(Endpoint.GET_ARTICLE_BY_ID.getUrl() + articleModel.id, PublicApi.getHeaders(), this::onResponse);
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
            articleModel = ArticleModel.fromJson(body.getJSONObject("data"));
        } catch (JSONException e) {
        }
    }

    protected void setArticleModel() {
        description.setText(articleModel.description);
        Http.loadImage(articleModel.thumbnail, thumbnail);
    }

    protected void setVariable() {
        articleModel = (ArticleModel) Navigator.getArgs(this);
        thumbnail = findViewById(R.id.ImageDetailArtikel);
        description = findViewById(R.id.TextArticle);
    }
}