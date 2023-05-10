package com.nekoid.smektuber.screen.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nekoid.smektuber.R;

import java.util.List;

public class AdapterDataJurusan extends RecyclerView.Adapter<AdapterDataJurusan.MyViewHolder> {
    private final Context context;
    private final List<MenuJurus> list;
    private Dialog dialog;

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    //    sintaks untuk mau membawa data dari card yang di klick
    public interface Dialog{
        void onClick(MenuJurus menuJurus);
    }

    public AdapterDataJurusan(Context context, List<MenuJurus> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_menu_jurusan, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.TitleMenuJurusan.setText(list.get(position).getTitleMenuJurus());
        holder.ImageJurus.setImageDrawable(list.get(position).getImageJurus());
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView ImageJurus;
        TextView TitleMenuJurusan;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ImageJurus = itemView.findViewById(R.id.ImageJurus);
            TitleMenuJurusan = itemView.findViewById(R.id.TitleMenuJurusan);

            itemView.setOnClickListener(v -> {
                if (dialog!=null){
                    dialog.onClick(list.get(getLayoutPosition()));
                }
            });
        }
    }
}
