package com.nekoid.smektuber.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.screen.home.article.MenuArtikelDashboard;

import java.util.List;

public class AdapterDataJobs extends RecyclerView.Adapter<AdapterDataJobs.MyViewHolder> {
    private final Context context;
    private final List<MenuArtikelDashboard> list;
    private AdapterDataJobs.Dialog dialog;

    public void setDialog(AdapterDataJobs.Dialog dialog) {
        this.dialog = dialog;
    }

    //    sintaks untuk mau membawa data dari card yang di klick
    public interface Dialog{
        void onClick(MenuArtikelDashboard menuArtikelDashboard);
    }

    public AdapterDataJobs(Context context, List<MenuArtikelDashboard> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterDataJobs.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_data_jobs, parent, false);
        return new AdapterDataJobs.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataJobs.MyViewHolder holder, int position) {
        holder.DataTextArtikel.setText(list.get(position).getDataTextArtikel());
        holder.ImageArtikel.setImageDrawable(list.get(position).getImageArtikel());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView ImageArtikel;
        TextView DataTextArtikel;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ImageArtikel = itemView.findViewById(R.id.ImageArtikel);
            DataTextArtikel = itemView.findViewById(R.id.DataTextArtikel);

            itemView.setOnClickListener(v -> {
                if (dialog!=null){
                    dialog.onClick(list.get(getLayoutPosition()));
                }
            });
        }
    }
}
