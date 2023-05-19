package com.nekoid.smektuber.screen.home.ekstarkurikuler;

import android.os.Bundle;
import android.widget.ImageView;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.api.Endpoint;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.models.ExtracurricularModel;
import com.nekoid.smektuber.network.Http;
import com.nekoid.smektuber.network.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailExtra extends BaseActivity {

    private ExtracurricularModel extracurricularModel;

    private ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_extra);
        setVariable();
        extracurricularModel = (ExtracurricularModel) Navigator.getArgs(this);
        Http.get(Endpoint.GET_EXTRACURRICULAR_BY_ID.getUrl() + extracurricularModel.id, this::onResponse);
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
            extracurricularModel = ExtracurricularModel.fromJson(body.getJSONObject("data"));
            setModelToView();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void setModelToView() {
        Http.loadImage(extracurricularModel.photo, photo);
    }

    private void setVariable() {
        photo = findViewById(R.id.ImageExtra);
    }
}