package com.nekoid.smektuber.adapter;

import android.app.Activity;
import android.content.Context;
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
import com.nekoid.smektuber.models.JobsModel;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.screen.home.article.DetailArticle;
import com.nekoid.smektuber.screen.home.article.MenuArtikelDashboard;
import com.nekoid.smektuber.screen.home.job.DetailJobs;
import com.nekoid.smektuber.screen.home.job.MenuJobs;

import java.util.ArrayList;
import java.util.List;

public class AdapterDataJobs extends RecyclerView.Adapter<AdapterDataJobs.MyViewHolder> {
    private final Activity activity;
    private final List<JobsModel> list;
    private List<View> containerArticles = new ArrayList<>();
    private boolean isLoad = false;
    private int currentLength;
    private AdapterDataJobs.Dialog dialog;

    public void setDialog(AdapterDataJobs.Dialog dialog) {
        this.dialog = dialog;
    }

    //    sintaks untuk mau membawa data dari card yang di klick
    public interface Dialog{
        void onClick(JobsModel menuJobs);
    }

    public AdapterDataJobs(Activity activity, List<JobsModel> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterDataJobs.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_data_jobs, parent, false);
        return new AdapterDataJobs.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataJobs.MyViewHolder holder, int position) {
        holder.DataTextJob.setText(list.get(position).title != null ? list.get(position).title : "");
        Http.loadImage(list.get(position).thumbnail, holder.ImageJob);
        holder.jobsModel = list.get(position);
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
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView ImageJob;
        JobsModel jobsModel;
        TextView DataTextJob;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ImageJob = itemView.findViewById(R.id.ImageJobs);
            DataTextJob = itemView.findViewById(R.id.DataTextJobs);

            itemView.setOnClickListener(v -> {
                Navigator.of(activity).push(DetailJobs.class, jobsModel);
                if (dialog!=null){
                    dialog.onClick(list.get(getLayoutPosition()));
                }
            });
        }
    }
}
