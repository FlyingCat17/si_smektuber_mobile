package com.nekoid.smektuber.screen.home.jurusan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.adapter.AdapterDataJurusan;

import java.util.ArrayList;
import java.util.List;

public class Jurusan extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<MenuJurus> list = new ArrayList<>();
    private AdapterDataJurusan adapterDataJurusan;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jurusan);
        recyclerView = findViewById(R.id.rvDataJurusan);

        list.add(new MenuJurus("Pasukan Pengibar Bendera", getDrawable(R.drawable.imageuser)));
        list.add(new MenuJurus("TKJ (Teknik Komputer dan Jaringan)", getDrawable(R.drawable.imageuser)));
        list.add(new MenuJurus("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuJurus("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuJurus("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuJurus("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuJurus("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuJurus("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuJurus("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuJurus("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuJurus("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuJurus("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuJurus("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuJurus("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuJurus("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuJurus("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuJurus("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuJurus("Pramuka", getDrawable(R.drawable.imageuser)));



        adapterDataJurusan = new AdapterDataJurusan(getApplicationContext(), list);
        adapterDataJurusan.setDialog(new AdapterDataJurusan.Dialog() {
            @Override
            public void onClick(MenuJurus menuJurus) {
                Intent x = new Intent(Jurusan.this, DetailJurusan.class);
                startActivity(x);
            }
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterDataJurusan);
    }
}