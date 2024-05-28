package com.nekoid.smektuber.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.api.ImageUrlUtil;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.models.ArticleModel;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.screen.home.article.DetailArticle;

import java.util.ArrayList;
import java.util.List;

public class AdapterData extends RecyclerView.Adapter<AdapterData.MyViewHolder> {
    private final Activity activity;
    private final List<ArticleModel> articleModels;
    private AdapterData.Dialog dialog;

    private boolean isLoad = false;

    private List<View> containerArticles = new ArrayList<>();

    private int currentLength;

    public void setDialog(AdapterData.Dialog dialog) {
        this.dialog = dialog;
    }

    //    sintaks untuk mau membawa data dari card yang di klick
    public interface Dialog{
        void onClick(ArticleModel menuArtikelDashboard);
    }

    public AdapterData(Activity activity, List<ArticleModel> articles) {
        this.activity = activity;
        this.articleModels = articles;
    }

    @NonNull
    @Override
    public AdapterData.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_data, parent, false);
        return new AdapterData.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterData.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.containerArticleHome.setVisibility(View.INVISIBLE);
        String thumbnailUrl = articleModels.get(position).thumbnail;
        if (thumbnailUrl == null || thumbnailUrl.isEmpty() || thumbnailUrl.equals("null")) {
            isLoadAll(holder.containerArticleHome);
            holder.articleDescription.setText(articleModels.get(position).title != null ? articleModels.get(position).title : "");
            holder.articleModel = articleModels.get(position);
        } else {
            Http.loadImage(  articleModels.get(position).thumbnail , holder.articleThumbnail, () -> {
                isLoadAll(holder.containerArticleHome);
                holder.articleDescription.setText(articleModels.get(position).title != null ? articleModels.get(position).title : "");
                holder.articleModel = articleModels.get(position);
            });
        }
    }

    private void isLoadAll(View holderView) {
        containerArticles.add(holderView);
        currentLength++;
        if (currentLength > 0) {
            setLoad(true);
            for (View view : containerArticles) {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    public boolean isLoad() {
        return isLoad;
    }

    public void setLoad(boolean load) {
        isLoad = load;
    }

    @Override
    public int getItemCount() {
        return articleModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout containerArticleHome;
        ArticleModel articleModel;
        ImageView articleThumbnail;
        TextView articleDescription;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            containerArticleHome = itemView.findViewById(R.id.containerArticleHome);
            articleThumbnail = itemView.findViewById(R.id.ImageArtikel);
            articleDescription = itemView.findViewById(R.id.DataTextArtikel);

            itemView.setOnClickListener(v -> {
                Navigator.of(activity).push(DetailArticle.class, articleModel);
                if (dialog!=null){
                    dialog.onClick(articleModels.get(getLayoutPosition()));
                }
            });
        }
    }
}