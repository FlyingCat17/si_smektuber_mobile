package com.nekoid.smektuber.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.models.ArticleModel;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.screen.home.article.DetailArticle;

import java.util.List;

public class AdapterDataArticleViewAll extends RecyclerView.Adapter<AdapterDataArticleViewAll.MyViewHolder> {
    private final Activity activity;
    private final List<ArticleModel> articleModels;
    private AdapterDataArticleViewAll.Dialog dialog;

    public void setDialog(AdapterDataArticleViewAll.Dialog dialog) {
        this.dialog = dialog;
    }

    //    sintaks untuk mau membawa data dari card yang di klick
    public interface Dialog{
        void onClick(ArticleModel menuArtikelDashboard);
    }

    public AdapterDataArticleViewAll(Activity activity, List<ArticleModel> articles) {
        this.activity = activity;
        this.articleModels = articles;
    }


    @NonNull
    @Override
    public AdapterDataArticleViewAll.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_data_article, parent, false);
        return new AdapterDataArticleViewAll.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataArticleViewAll.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.articleDescription.setText(articleModels.get(position).title != null ? articleModels.get(position).title : "");
        Http.loadImage(articleModels.get(position).thumbnail, holder.articleThumbnail);
        holder.articleModel = articleModels.get(position);
    }

    @Override
    public int getItemCount() {
        return articleModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ArticleModel articleModel;
        ImageView articleThumbnail;
        TextView articleDescription;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            articleThumbnail = itemView.findViewById(R.id.ImageArtikelViewAll);
            articleDescription = itemView.findViewById(R.id.DataTextArtikelViewAll);

            itemView.setOnClickListener(v -> {
                Navigator.of(activity).push(DetailArticle.class, articleModel);
                if (dialog!=null){
                    dialog.onClick(articleModels.get(getLayoutPosition()));
                }
            });
        }
    }
}
