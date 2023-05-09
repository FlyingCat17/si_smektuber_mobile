package com.nekoid.smektuber.screen.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nekoid.smektuber.R;

import java.util.List;

public class AdapterDataFasilitasJurusan extends RecyclerView.Adapter<AdapterData.HolderData>{
    List<String> listData;
    LayoutInflater inflater;

    public AdapterDataFasilitasJurusan(Context context, List<String> listData) {
        this.listData = listData;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AdapterData.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_data, parent, false);
        return new AdapterData.HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterData.HolderData holder, int position) {
        holder.txtData.setText(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class HolderData extends RecyclerView.ViewHolder{
        TextView txtData;
        public HolderData(@NonNull View itemView) {
            super(itemView);

            txtData = itemView.findViewById(R.id.DataTextFasilitasExtra);
        }
    }
}
