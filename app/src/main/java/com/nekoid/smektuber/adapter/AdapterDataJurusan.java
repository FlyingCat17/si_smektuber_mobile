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
import com.nekoid.smektuber.api.ImageUrlUtil;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.models.MajorModel;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.screen.home.jurusan.DetailJurusan;

import java.util.List;

public class AdapterDataJurusan extends RecyclerView.Adapter<AdapterDataJurusan.MyViewHolder> {
    private final Activity activity;
    private final List<MajorModel> majorModels;

    public AdapterDataJurusan(Activity activity, List<MajorModel> list) {
        this.activity = activity;
        this.majorModels = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.row_menu_jurusan, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.TitleMenuJurusan.setText(majorModels.get(position).majorName);
        Http.loadImage(  majorModels.get(position).majorLogo , holder.ImageJurus);
        holder.majorModel = majorModels.get(position);
    }

    @Override
    public int getItemCount() {

        return majorModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        MajorModel majorModel;
        ImageView ImageJurus;
        TextView TitleMenuJurusan;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ImageJurus = itemView.findViewById(R.id.ImageJurus);
            TitleMenuJurusan = itemView.findViewById(R.id.TitleMenuJurusan);

            itemView.setOnClickListener(v -> {
                Navigator.of(activity).push(DetailJurusan.class, majorModel);
            });
        }
    }
}
