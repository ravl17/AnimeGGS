package com.example.animeggs.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.animeggs.Activities.AnimeDetailsActivity;
import com.example.animeggs.Objetos.Anime;
import com.example.animeggs.Objetos.BarraBusquedaHelper;
import com.example.animeggs.Objetos.Episodio;
import com.example.animeggs.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<Anime> animeList = new ArrayList<>();

        BarraBusquedaHelper.setupSearchBar(this);
        // Initialize the Firebase database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("animes");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot animeSnapshot : dataSnapshot.getChildren()) {
                    String caratula = animeSnapshot.child("caratula").getValue(String.class);
                    String nombre = animeSnapshot.child("nombre").getValue(String.class);
                    String estudio = animeSnapshot.child("estudio").getValue(String.class);
                    String generos = animeSnapshot.child("generos").getValue(String.class);
                    String descripcion = animeSnapshot.child("descripcion").getValue(String.class);

                    ArrayList<Episodio> episodios = new ArrayList<>();
                    int cont = 0;
                    for (DataSnapshot episodioSnapshot : animeSnapshot.child("episodios").getChildren()) {
                        String enlaceEpisodio = episodioSnapshot.child("ep").getValue(String.class);
//                        int numero = episodioSnapshot.child("numero").getValue(Integer.class);
                        Episodio episodio = new Episodio(cont, ""+cont,enlaceEpisodio);
                        episodios.add(episodio);
                        cont++;
                    }
                    animeList.add(new Anime(nombre, caratula, estudio, generos, descripcion,episodios));

                }
                crearAnimes(animeList);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
    }

    public void crearAnimes(ArrayList<Anime> animeList) {
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this);
//        horizontalScrollView.setId(R.id.scroll_animes_1);
        horizontalScrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

// create LinearLayout
        LinearLayout linearLayout = new LinearLayout(this);
//        linearLayout.setId(R.id.layout_scroll_animes_1);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout.removeAllViews();
        for (Anime anime : animeList) {
//            CardView animeCover = new CardView(MainActivity.this);
            View cardAnime = getLayoutInflater().inflate(R.layout.card_small_anime, null);

            ImageView cardCaratula = cardAnime.findViewById(R.id.card_caratula);
            Picasso.get().load(anime.getCaratula()).into(cardCaratula);

            TextView cardNombre = cardAnime.findViewById(R.id.card_nombre);
            cardNombre.setText(anime.getNombre());
            cardAnime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Info",anime.getNombre());
                    Intent intent = new Intent(v.getContext(), AnimeDetailsActivity.class);
                    intent.putExtra("anime_nombre", anime.getNombre());
                    intent.putExtra("anime_caratula", anime.getCaratula());
                    intent.putExtra("anime_descripcion", anime.getDescripcion());

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = null;
                    try {
                        oos = new ObjectOutputStream(baos);
                        oos.writeObject(anime.getEpisodios());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    byte[] byteArray = baos.toByteArray();
                    intent.putExtra("anime_episodios", byteArray);
                    startActivity(intent);
                }
            });
            linearLayout.addView(cardAnime, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        }
        ViewGroup rootView = this.findViewById(R.id.layout_scroll_vertical);
        horizontalScrollView.addView(linearLayout);
        rootView.addView(horizontalScrollView);
        Log.d("aa", "crearAnimes: Aaa");
    }


}