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
import com.nekoid.smektuber.screen.home.ekstarkurikuler.MenuExtra;

import java.util.List;

public class AdapterDataExtra extends RecyclerView.Adapter<AdapterDataExtra.MyViewHolder> {
    private final Context context;
    private final List<MenuExtra> list;
    private Dialog dialog;

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    //    sintaks untuk mau membawa data dari card yang di klick
    public interface Dialog{
        void onClick(MenuExtra menuExtra);
    }

    public AdapterDataExtra(Context context, List<MenuExtra> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_menu_extra, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.TitleMenuExtra.setText(list.get(position).getTitleMenuExtra());
        holder.ImageExtra.setImageDrawable(list.get(position).getImageExtra());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView ImageExtra;
        TextView TitleMenuExtra;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ImageExtra = itemView.findViewById(R.id.ImageExtra);
            TitleMenuExtra = itemView.findViewById(R.id.TitleMenuExtra);

            itemView.setOnClickListener(v -> {
                if (dialog!=null){
                    dialog.onClick(list.get(getLayoutPosition()));
                }
            });
        }
    }
}
