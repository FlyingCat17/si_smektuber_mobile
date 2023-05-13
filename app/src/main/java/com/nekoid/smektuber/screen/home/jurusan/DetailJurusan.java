package com.nekoid.smektuber.screen.home.jurusan;

import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.Response;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.config.volley.Endpoint;
import com.nekoid.smektuber.config.volley.PublicApi;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.utils.BaseActivity;
import com.nekoid.smektuber.models.ExtracurricularModel;
import com.nekoid.smektuber.models.MajorModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailJurusan extends BaseActivity {

    MajorModel majorModel;

    ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jurusan);

        // set property variable
        setVariable();

        // send request to server
        addRequest(PublicApi.get(Endpoint.GET_MAJOR_BY_ID.getUrl() + majorModel.id, onResponse()));
        runQueue();
    }

    private Response.Listener<String> onResponse() {
        return response -> {
            try {
                JSONObject body = new JSONObject(response);
                if (body.getInt("status") != 200) {
                    return;
                }
                majorModel = MajorModel.fromJson(body.getJSONObject("data"));
                setModelToView();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        };
    }

    private void setModelToView() {
        Picasso.get().load(majorModel.photo).into(photo);
    }

    private void setVariable() {
        // get model from argument
        majorModel = (MajorModel) Navigator.getArgs(this);
        photo = findViewById(R.id.ImageJurusan);
    }
}