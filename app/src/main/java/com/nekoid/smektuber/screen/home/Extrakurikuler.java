package com.nekoid.smektuber.screen.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.screen.auth.Login;
import com.nekoid.smektuber.screen.auth.Register;

import java.util.ArrayList;
import java.util.List;

public class Extrakurikuler extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<MenuExtra> list = new ArrayList<>();
    private AdapterDataExtra adapterDataExtra;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extrakurikuler);
        recyclerView = findViewById(R.id.rvDataExtra);

        list.add(new MenuExtra("Pasukan Pengibar Bendera", getDrawable(R.drawable.imageuser)));
        list.add(new MenuExtra("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuExtra("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuExtra("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuExtra("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuExtra("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuExtra("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuExtra("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuExtra("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuExtra("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuExtra("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuExtra("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuExtra("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuExtra("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuExtra("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuExtra("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuExtra("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuExtra("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuExtra("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuExtra("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuExtra("Pramuka", getDrawable(R.drawable.imageuser)));
        list.add(new MenuExtra("Pramuka", getDrawable(R.drawable.imageuser)));

        adapterDataExtra = new AdapterDataExtra(getApplicationContext(), list);
        adapterDataExtra.setDialog(new AdapterDataExtra.Dialog() {
            @Override
            public void onClick(MenuExtra menuExtra) {
                Intent x = new Intent(Extrakurikuler.this, DetailExtra.class);
                startActivity(x);
            }
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterDataExtra);
    }
}