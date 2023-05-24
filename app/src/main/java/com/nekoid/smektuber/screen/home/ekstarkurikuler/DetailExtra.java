package com.nekoid.smektuber.screen.home.ekstarkurikuler;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.app.BaseActivity;
import com.nekoid.smektuber.models.ExtracurricularModel;
import com.nekoid.smektuber.network.Http;

public class DetailExtra extends BaseActivity {

    private ExtracurricularModel extracurricularModel;
    private Toolbar toolbar;
    private TextView title, description;

    private ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_extra);
        setVariable();
        setToolbar();
        extracurricularModel = (ExtracurricularModel) Navigator.getArgs(this);
        if (extracurricularModel != null) {
            setModelToView();
        }
    }

    private void setModelToView() {
        Http.loadImage(extracurricularModel.photo, photo, () -> {
            title.setText(extracurricularModel.name);
            description.setText(extracurricularModel.description);
        });
    }

    private void setVariable() {
        title = findViewById(R.id.TitleDetailExtra);
        description = findViewById(R.id.TxtAboutExtra);
        photo = findViewById(R.id.ImageExtra);
    }

    private void setToolbar(){
        toolbar = findViewById(R.id.backIcon);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Navigator.of(this).pop();
        return true;
    }
}