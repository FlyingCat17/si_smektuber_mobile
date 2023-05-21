package com.nekoid.smektuber.screen.home.article;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.adapter.AdapterDataArticleViewAll;
import com.nekoid.smektuber.api.Endpoint;
import com.nekoid.smektuber.api.PublicApi;
import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.utils.ScrollListener;
import com.nekoid.smektuber.models.ArticleModel;
import com.nekoid.smektuber.models.Pagination;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.network.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArticleViewAll extends BaseActivity {

    private RecyclerView recyclerView;

    List<ArticleModel> articleModelList = new ArrayList<ArticleModel>();

    Pagination pagination;

    AdapterDataArticleViewAll adapterDataArticleViewAll;

    ShimmerFrameLayout shimmerFrameLayout;

    private boolean isScroll = false, isFromState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_view_all);
        setToolbar();
        recyclerView = findViewById(R.id.rvDataViewAll);
        shimmerFrameLayout = findViewById(R.id.shimmerArticles);
        setAdapterData();
        startShimmer();
        Http.get(Endpoint.LIST_ARTICLE.getUrl(), PublicApi.getHeaders(), this::listArticles);
    }

    private void startShimmer() {

    }

    private void stopShimmer() {
        shimmerFrameLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
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

    protected void listArticles(Response response) {
        if (response.statusCode != 200) {
            return;
        }

        try {
            JSONObject responses = new JSONObject(response.body.toString());
            pagination = Pagination.fromJson(responses.getJSONObject("pagination"));
            JSONArray arrays = responses.getJSONArray("data");
            for (int i = 0; i < arrays.length(); i++) {
                articleModelList.add(ArticleModel.fromJson(new JSONObject(arrays.getString(i))));
            }
            updateAdapter();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void setAdapterData() {
        adapterDataArticleViewAll = new AdapterDataArticleViewAll(this, articleModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterDataArticleViewAll);
        recyclerView.addOnScrollListener(new ScrollListener() {
            @Override
            public void onScroll(@NonNull RecyclerView recyclerView, int horizontal, int vertical, int newState) {
                if (!recyclerView.canScrollVertically(2) && isScroll) {
                    onLastScroll();
                }
            }
        });
    }

    protected void onLastScroll() {
        if (pagination != null && pagination.nextPageUrl != null && !pagination.nextPageUrl.isEmpty() && !pagination.nextPageUrl.equals("null")) {
            isScroll = false;
            Http.get(pagination.nextPageUrl, PublicApi.getHeaders(), this::listArticles);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    protected void updateAdapter() {
        adapterDataArticleViewAll.notifyDataSetChanged();
        stopShimmer();
        isScroll = true;
    }
}