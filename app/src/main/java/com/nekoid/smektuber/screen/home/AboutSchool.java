package com.nekoid.smektuber.screen.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.nekoid.smektuber.R;

public class AboutSchool extends AppCompatActivity {
    ImageView BtnFB, BtnIG, BtnYoutube, BtnTT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_school);
        BtnFB = findViewById(R.id.FaceBook);
        BtnIG = findViewById(R.id.Instagram);
        BtnYoutube = findViewById(R.id.Youtube);
        BtnTT = findViewById(R.id.Tiktok);

        BtnFB.setOnClickListener(v -> {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/profile.php?id=100090674252646&mibextid=ZbWKwL"));
            startActivity(intent);
        });

        BtnIG.setOnClickListener(v -> {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/smkn_7_jember_official/"));
            startActivity(intent);
        });

        BtnYoutube.setOnClickListener(v -> {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/channel/UC3m5Ou436WR_phX72cexPHg"));
            startActivity(intent);
        });

        BtnTT.setOnClickListener(v -> {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("https://www.tiktok.com/@smknegeri7jember"));
            startActivity(intent);
        });
    }
}