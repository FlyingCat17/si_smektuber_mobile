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
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.utils.Utils;
import com.nekoid.smektuber.models.ExtracurricularModel;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.screen.home.ekstarkurikuler.DetailExtra;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AdapterDataExtra extends RecyclerView.Adapter<AdapterDataExtra.MyViewHolder> {

    private final Activity activity;

    private final List<ExtracurricularModel> extracurricularModels;

    private int currentLength;

    private boolean isLoad = false;

    private List<View> animateExtracurriculars = new ArrayList<>();

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
        holder.animateExtracurricular.setVisibility(View.GONE);
        if (!extracurricularModels.get(position).photo.isEmpty()) {
            String baseUrl = "https://lutfisobri.my.id/";
            String storagePath = "storage/app/";
            Http.loadImage(baseUrl+ storagePath + extracurricularModels.get(position).photo, holder.ImageExtra, () -> {
                setTextAndModel(holder, extracurricularModels.get(position));
            });
        } else {
            setTextAndModel(holder, extracurricularModels.get(position));
        }
    }

    private void setTextAndModel(MyViewHolder holder, ExtracurricularModel model) {
        isLoadAll(holder.animateExtracurricular);
        if (!model.name.isEmpty()) {
            holder.TitleMenuExtra.setText(model.name);
        }
        holder.extracurricularModel = model;
    }

    private void isLoadAll(View holderView) {
        animateExtracurriculars.add(holderView);
        currentLength++;
        if (getItemCount() <= currentLength) {
            setLoad(true);
            for (View view : animateExtracurriculars) {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull MyViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public int getItemCount() {
        return extracurricularModels.size();
    }

    public boolean isLoad() {
        return isLoad;
    }

    public void setLoad(boolean load) {
        isLoad = load;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addAll(List<ExtracurricularModel> iterator) {
        extracurricularModels.addAll(iterator);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void add(ExtracurricularModel model) {
        extracurricularModels.add(model);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout animateExtracurricular;
        ExtracurricularModel extracurricularModel;
        ImageView ImageExtra;
        TextView TitleMenuExtra;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            animateExtracurricular = itemView.findViewById(R.id.animateExtracurricular);
            ImageExtra = itemView.findViewById(R.id.ImageExtra);
            TitleMenuExtra = itemView.findViewById(R.id.TitleMenuExtra);

            itemView.setOnClickListener(v -> {
                Navigator.of(activity).push(DetailExtra.class, extracurricularModel);
            });
        }
    }
}
