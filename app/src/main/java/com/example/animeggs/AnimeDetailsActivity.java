package com.example.animeggs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class AnimeDetailsActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anime_details);

        imageView = findViewById(R.id.anime_poster);
        textView = findViewById(R.id.anime_title);

        Intent intent = getIntent();
        String title = intent.getStringExtra("anime_nombre");
        String imageResId = intent.getStringExtra("anime_caratula");

        textView.setText(title);
        Picasso.get().load(imageResId).into(imageView);
    }
}
