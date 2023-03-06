package com.example.animeggs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class AnimeDetailsActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textViewNombre;
    private TextView textViewDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anime_details);

        imageView = findViewById(R.id.anime_poster);
        textViewNombre = findViewById(R.id.anime_title);
        textViewDescripcion = findViewById(R.id.anime_descripcion);

        Intent intent = getIntent();
        String titulo = intent.getStringExtra("anime_nombre");
        String descripcion = intent.getStringExtra("anime_descripcion");
        String imageResId = intent.getStringExtra("anime_caratula");

        textViewNombre.setText(titulo);
        Picasso.get().load(imageResId).into(imageView);
        TextView animeReadMore = findViewById(R.id.anime_read_more);
        textViewDescripcion.setText(descripcion);
        if (descripcion.split("\\s+").length > 30) {
            // If the description has more than 30 words, show "Read more"
            textViewDescripcion.setMaxLines(3);
            animeReadMore.setVisibility(View.VISIBLE);
            animeReadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Show the full description
                    textViewDescripcion.setMaxLines(Integer.MAX_VALUE);
                    animeReadMore.setVisibility(View.GONE);
                }
            });
        }
    }
}
