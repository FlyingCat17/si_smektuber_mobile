package com.nekoid.smektuber.adapter;

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
import com.nekoid.smektuber.models.ExtracurricularModel;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.screen.home.ekstarkurikuler.DetailExtra;

import java.util.List;

public class AdapterDataExtra extends RecyclerView.Adapter<AdapterDataExtra.MyViewHolder> {

    private final Activity activity;

    private final List<ExtracurricularModel> extracurricularModels;

    public AdapterDataExtra(Activity activity, List<ExtracurricularModel> extracurricularModels) {
        this.activity = activity;
        this.extracurricularModels = extracurricularModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.row_menu_extra, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.TitleMenuExtra.setText(extracurricularModels.get(position).name);
        Http.loadImage(extracurricularModels.get(position).photo, holder.ImageExtra);
        holder.extracurricularModel = extracurricularModels.get(position);
    }

    @Override
    public int getItemCount() {
        return extracurricularModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ExtracurricularModel extracurricularModel;
        ImageView ImageExtra;
        TextView TitleMenuExtra;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ImageExtra = itemView.findViewById(R.id.ImageExtra);
            TitleMenuExtra = itemView.findViewById(R.id.TitleMenuExtra);

            itemView.setOnClickListener(v -> {
                Navigator.of(activity).push(DetailExtra.class, extracurricularModel);
            });
        }
    }
}
