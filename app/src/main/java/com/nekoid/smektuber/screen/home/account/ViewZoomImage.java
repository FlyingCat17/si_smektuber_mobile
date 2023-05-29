package com.nekoid.smektuber.screen.home.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.api.ImageUrlUtil;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.helpers.utils.State;
import com.nekoid.smektuber.models.UserModel;
import com.nekoid.smektuber.network.Http;

public class ViewZoomImage extends AppCompatActivity {
    Toolbar toolbar;
    private ImageView photo;
    private UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_zoom_image);
        setVariable();
        setToolbar();
        userModel = (UserModel) Navigator.getArgs(this);
        setUserModel();
    }

    private void setVariable() {
        toolbar = findViewById(R.id.toolbarZoom);
        photo = findViewById(R.id.ImageProfilZoom);
    }

    protected void setUserModel() {
        userModel = State.userModel;
        if (userModel != null && userModel.avatar != null && !userModel.avatar.isEmpty()) {
//            if (userModel.avatar.startsWith("http://") || userModel.avatar.startsWith("https://"))
//                Http.loadImage(userModel.avatar, photo);
            if (userModel.avatar.startsWith("http://") || userModel.avatar.startsWith("https://")) {
                String modifiedUrl = ImageUrlUtil.modifyAvatarUrl(userModel.avatar);
                modifiedUrl += "?timestamp=" + System.currentTimeMillis();
                Http.loadImage(modifiedUrl, photo);
            }
        }
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Navigator.of(this).pop().animation( R.anim.fade_in, R.anim.fade_out);
        return true;
    }
}