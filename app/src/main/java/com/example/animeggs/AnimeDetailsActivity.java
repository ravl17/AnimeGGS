package com.example.animeggs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class AnimeDetailsActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textViewNombre;
    private TextView textViewDescripcion;
    private RecyclerView episodiosRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anime_details);


        Intent intent = getIntent();

        //Creamos el titulo del anime
        textViewNombre = findViewById(R.id.anime_title);
        String titulo = intent.getStringExtra("anime_nombre");
        textViewNombre.setText(titulo);

        //Creamos la descripcion del anime
        textViewDescripcion = findViewById(R.id.anime_descripcion);
        String descripcion = intent.getStringExtra("anime_descripcion");
        TextView animeReadMore = findViewById(R.id.anime_read_more);
        textViewDescripcion.setText(descripcion);
        if (descripcion.split("\\s+").length > 30) {
            textViewDescripcion.setMaxLines(3);
            animeReadMore.setVisibility(View.VISIBLE);
            animeReadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textViewDescripcion.setMaxLines(Integer.MAX_VALUE);
                    animeReadMore.setVisibility(View.GONE);
                }
            });
        }
        //Creamos la caratula del anime
        imageView = findViewById(R.id.anime_poster);
        String imageResId = intent.getStringExtra("anime_caratula");
        Picasso.get().load(imageResId).into(imageView);

        //Creamos los episodios del anime
        episodiosRecyclerView = findViewById(R.id.recycler_view_episodios);
        episodiosRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        byte[] receivedByteArray = intent.getByteArrayExtra("anime_episodios");
        ByteArrayInputStream bais = new ByteArrayInputStream(receivedByteArray);
        ObjectInputStream ois = null;
        ArrayList<Episodio> receivedStringArray;
        try {
            ois = new ObjectInputStream(bais);
            receivedStringArray = (ArrayList<Episodio>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Episodio> episodios = receivedStringArray;
        EpisodioAdapter adapter = new EpisodioAdapter(episodios);
        episodiosRecyclerView.setAdapter(adapter);
    }
}
