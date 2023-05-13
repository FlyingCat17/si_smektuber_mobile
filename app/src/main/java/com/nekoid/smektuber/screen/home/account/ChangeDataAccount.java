package com.nekoid.smektuber.screen.home.account;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.nekoid.smektuber.R;
import com.nekoid.smektuber.config.volley.PublicApi;
import com.nekoid.smektuber.config.volley.UrlsApi;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.models.ArticleModel;
import com.nekoid.smektuber.screen.auth.Register;
import com.nekoid.smektuber.screen.notification.Notif_Succes_change_Data_Account;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangeDataAccount extends AppCompatActivity {
    ImageView UploadImage, ImageUser;

    TextView Username,Name, Email;
//    Button Back;
    Toolbar toolbar;
    Button updateButton;

    Notif_Succes_change_Data_Account notif_succes_change_data_account = new Notif_Succes_change_Data_Account();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_data_account);
        UploadImage = findViewById(R.id.IconUploadImage);
        ImageUser = findViewById(R.id.ImageUserProfil);
        updateButton = findViewById(R.id.BtnMemperbarui);
        Username = findViewById(R.id.Ca_username);
        Name = findViewById(R.id.Ca_NameLengkap);
        Email = findViewById(R.id.Ca_Email);
        UploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ChangeDataAccount.this)
                        .crop()
                        .cropSquare()
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
        setToolbar();
    }

    private void updateProfile() {
        Map <String, String> params = new HashMap<>();
        params.put("username", Username.getText().toString().trim());
        params.put("name", Name.getText().toString());
        params.put("email", Email.getText().toString());
        PublicApi.addParams(params);
        StringRequest Update = PublicApi.put("/user/update", Profile());

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(Update);
    }

    protected Response.Listener<String> Profile() {
        return response -> {
            try {
                JSONObject responses = new JSONObject(response);
                if (responses.getInt("status") == 200) {
                    JSONObject object = responses.getJSONObject("data");
                    Toast.makeText(this, "Behasii", Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.r,notif_succes_change_data_account).commit();
//                    Navigator.of(this).pop();
                } else if (responses.getInt("status") == 400) {
                    Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK

            Uri uri = data.getData();
            ImageUser.setImageURI(uri);
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private void setToolbar(){
        toolbar = findViewById( R.id.backIcon );
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        Navigator.of(this).pop();
        return true;
    }
}