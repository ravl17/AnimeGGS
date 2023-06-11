package com.example.animeggs.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animeggs.Adapters.NavigationBarHelper;
import com.example.animeggs.Objetos.Anime;
import com.example.animeggs.Adapters.BarraBusquedaHelper;
import com.example.animeggs.Objetos.Episodio;
import com.example.animeggs.Adapters.EpisodioAdapter;
import com.example.animeggs.Objetos.Usuario;
import com.example.animeggs.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class AnimeDetailsActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textViewNombre;
    private TextView textViewDescripcion;
    private Button btnSiguiendo;
    private RecyclerView episodiosRecyclerView;
    private Anime anime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_details);

//        BarraBusquedaHelper.setupSearchBar(this);
        NavigationBarHelper.setupNavigationBar(this, R.id.page_2);

        btnSiguiendo = findViewById(R.id.btnSiguiendo);

        Intent intent = getIntent();
        anime = intent.getParcelableExtra("anime");
        anime.setEpisodios(intent.getParcelableArrayListExtra("episodios"));
        setTitle(anime.getNombre());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean dejarSeguir = false;
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String correo = userSnapshot.child("correo").getValue(String.class);
                    if (correo.contentEquals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                        DataSnapshot siguiendoSnapshot = userSnapshot.child("siguiendo");
                        for (DataSnapshot serieSnapshot : siguiendoSnapshot.getChildren()) {

                            if ((serieSnapshot.child("serie").getValue(String.class)).equals(anime.getNombre())) {
                                Log.d("TAG", "onDataChange: " + serieSnapshot.child("serie").getValue(String.class));
                                Log.d("TAG", "onDataChange: " + anime.getNombre());
                                dejarSeguir = true;
                            }
                        }
                        cambiarBtnSiguiendo(dejarSeguir);
                        DataSnapshot visualizacionesSnapshot = userSnapshot.child("visualizaciones");
                        String epVistos = "";
                        for (DataSnapshot serie : visualizacionesSnapshot.getChildren()) {
                            if ((serie.child("serie").getValue(String.class)).equals(anime.getNombre())) {
                                epVistos = serie.child("epVistos").getValue(String.class);
                            }
                        }
                        setAnimeDetails(anime, epVistos);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });

        btnSiguiendo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seguirAnime();

            }
        });
    }

    public void setAnimeDetails(Anime anime, String epVistos) {
        //Creamos el titulo del anime
        textViewNombre = findViewById(R.id.anime_title);
        textViewNombre.setText("");
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
        imageView.getLayoutParams().height = this.getResources().getDisplayMetrics().heightPixels / 3;
        String imageResId = anime.getImg();
        Picasso.get().load(imageResId).into(imageView);
        //Creamos los episodios del anime
        episodiosRecyclerView = findViewById(R.id.recycler_view_episodios);
        episodiosRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        EpisodioAdapter adapter = new EpisodioAdapter(anime, epVistos, this);
        episodiosRecyclerView.setAdapter(adapter);
    }

    public void cambiarBtnSiguiendo(boolean dejarSeguir) {
        if (!dejarSeguir) {
            btnSiguiendo.setText("SEGUIR");
        } else {
            btnSiguiendo.setText("SIGUIENDO");
        }
    }

    public void seguirAnime() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tableRef = database.getReference("users");
        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String correo = userSnapshot.child("correo").getValue(String.class);
                    if (correo.contentEquals(FirebaseAuth.getInstance().getCurrentUser().getEmail()) && correo != null) {
                        DataSnapshot siguiendoSnapshot = userSnapshot.child("siguiendo");
                        ArrayList<Usuario.SiguiendoItem> newAnimeSiguiendo = new ArrayList<>();
                        for (DataSnapshot serieSnapshot : siguiendoSnapshot.getChildren()) {
                            Usuario.SiguiendoItem siguiendoItem = new Usuario.SiguiendoItem();
                            siguiendoItem.setSerie(serieSnapshot.child("serie").getValue(String.class));
                            newAnimeSiguiendo.add(siguiendoItem);
                        }
                        Usuario.SiguiendoItem us = null;
                        for (Usuario.SiguiendoItem u : newAnimeSiguiendo) {
                            if (u.getSerie().contentEquals(anime.getNombre())) {
                                us = u;
                            }
                        }
                        if (us == null) {
                            Usuario.SiguiendoItem siguiendoItem = new Usuario.SiguiendoItem();
                            siguiendoItem.setSerie(anime.getNombre());
                            newAnimeSiguiendo.add(siguiendoItem);
                            cambiarBtnSiguiendo(true);
                        } else {
                            newAnimeSiguiendo.remove(us);
                            cambiarBtnSiguiendo(false);
                        }
                        siguiendoSnapshot.getRef().setValue(newAnimeSiguiendo);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

