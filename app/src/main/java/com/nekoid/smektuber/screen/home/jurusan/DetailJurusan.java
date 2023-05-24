package com.nekoid.smektuber.screen.home.jurusan;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.api.Endpoint;
import com.nekoid.smektuber.api.PublicApi;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.app.BaseActivity;
import com.nekoid.smektuber.models.MajorModel;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.network.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailJurusan extends BaseActivity {

    MajorModel majorModel;
    private Toolbar toolbar;
    ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jurusan);
        setToolbar();
        // set property variable
        setVariable();

        // send request to server
        Http.get(Endpoint.GET_MAJOR_BY_ID.getUrl() + majorModel.id, PublicApi.getHeaders(), this::onResponse);
    }

    private void onResponse(Response response) {
        if (response.statusCode != 200) {
            return;
        }
        try {
            JSONObject body = new JSONObject(response.body.toString());
            if (body.getInt("status") != 200) {
                return;
            }
            majorModel = MajorModel.fromJson(body.getJSONObject("data"));
            setModelToView();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void setModelToView() {
        Http.loadImage(majorModel.majorLogo, photo);
    }

    private void setVariable() {
        // get model from argument
        majorModel = (MajorModel) Navigator.getArgs(this);
        photo = findViewById(R.id.ImageJurusan);
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