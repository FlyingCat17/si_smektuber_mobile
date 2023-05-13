package com.nekoid.smektuber.screen.home.ekstarkurikuler;

import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.Response;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.config.volley.Endpoint;
import com.nekoid.smektuber.config.volley.PublicApi;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.models.ExtracurricularModel;
import com.squareup.picasso.Picasso;

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

        addRequest(PublicApi.get(Endpoint.GET_EXTRACURRICULAR_BY_ID.getUrl() + extracurricularModel.id, onResponse()));
        runQueue();
    }

    private Response.Listener<String> onResponse() {
        return response -> {
            try {
                JSONObject body = new JSONObject(response);
                if (body.getInt("status") != 200) {
                    return;
                }
                extracurricularModel = ExtracurricularModel.fromJson(body.getJSONObject("data"));
                setModelToView();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        };
    }

    private void setModelToView() {
        Picasso.get().load(extracurricularModel.photo).into(photo);
    }

    private void setVariable() {
        photo = findViewById(R.id.ImageExtra);
    }
}