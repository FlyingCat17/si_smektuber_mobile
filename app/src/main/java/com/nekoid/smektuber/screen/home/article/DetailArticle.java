package com.nekoid.smektuber.screen.home.article;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.nekoid.smektuber.R;
import com.nekoid.smektuber.helpers.Utils;
import com.nekoid.smektuber.helpers.navigation.Navigator;
import com.nekoid.smektuber.models.ArticleModel;

public class DetailArticle extends AppCompatActivity {
    ImageView thumbnail;
    TextView title, description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);
        setVariable();
        loadArticle();
    }

    protected void loadArticle() {
        ArticleModel article = (ArticleModel) Navigator.getArgs(this);
        if (article != null) {
            title.setText(article.title);
            thumbnail.setImageBitmap(Utils.downloadImage(article.thumbnail));
            description.setText(article.description);
        }
    }

    protected void setVariable() {
        thumbnail = findViewById(R.id.ImageDetailArtikel);
        title = findViewById(R.id.TitleDetailArtikel);
        description = findViewById(R.id.TextArticle);
    }
}