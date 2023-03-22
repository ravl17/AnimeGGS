package com.example.animeggs.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animeggs.Objetos.Anime;
import com.example.animeggs.Adapters.BarraBusquedaHelper;
import com.example.animeggs.Objetos.Episodio;
import com.example.animeggs.Adapters.EpisodioAdapter;
import com.example.animeggs.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AnimeDetailsActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textViewNombre;
    private TextView textViewDescripcion;
    private RecyclerView episodiosRecyclerView;
    private Anime anime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_details);

        BarraBusquedaHelper.setupSearchBar(this);
        Intent intent = getIntent();
        String titulo = intent.getStringExtra("anime_nombre");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("animes");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot animeSnapshot : dataSnapshot.getChildren()) {
                    String nombre = animeSnapshot.child("nombre").getValue(String.class);
                    if (titulo.contentEquals(nombre)) {
                        anime = animeSnapshot.getValue(Anime.class);
                    }
                }
                setAnimeDetails(anime);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });

    }

    public void setAnimeDetails(Anime anime) {
        //Creamos el titulo del anime
        textViewNombre = findViewById(R.id.anime_title);
        textViewNombre.setText(anime.getNombre());
        //Creamos la descripcion del anime
        textViewDescripcion = findViewById(R.id.anime_descripcion);
        String descripcion = anime.getDescripcion();
        TextView animeReadMore = findViewById(R.id.anime_read_more);
        textViewDescripcion.setText(descripcion);
        if (descripcion.split("\\s+").length > 30) {
            textViewDescripcion.setMaxLines(3);
            animeReadMore.setVisibility(View.VISIBLE);
            animeReadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textViewDescripcion.setMaxLines(15);
                    animeReadMore.setVisibility(View.GONE);
                }
            });
        }
        //Creamos la caratula del anime
        imageView = findViewById(R.id.anime_poster);
        String imageResId = anime.getImg();
        Picasso.get().load(imageResId).into(imageView);
        //Creamos los episodios del anime
        episodiosRecyclerView = findViewById(R.id.recycler_view_episodios);
        episodiosRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Episodio> episodios = anime.getEpisodios();
        EpisodioAdapter adapter = new EpisodioAdapter(episodios, this);
        episodiosRecyclerView.setAdapter(adapter);
    }
}

