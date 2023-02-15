package com.example.animeggs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<Anime> animeList = new ArrayList<>();


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
                    animeList.add(new Anime(nombre, caratula, estudio, generos, descripcion));
                    crearAnimes(animeList);

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });

    }

    public void crearAnimes(ArrayList<Anime> animeList) {

        LinearLayout linearLayout = findViewById(R.id.layout_scroll_animes_1);
        linearLayout.removeAllViews();
        for (Anime anime : animeList) {
//            CardView animeCover = new CardView(MainActivity.this);
            View view = getLayoutInflater().inflate(R.layout.card_small_anime, null);

            ImageView cardCaratula = view.findViewById(R.id.card_caratula);
            Picasso.get().load(anime.getCaratula()).into(cardCaratula);

            TextView cardNombre = view.findViewById(R.id.card_nombre);
            cardNombre.setText(anime.getNombre());

            linearLayout.addView(view, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        }

    }
//    public void crearAnimes(ArrayList<Anime> animeList) {
//        HorizontalScrollView scrollView = new HorizontalScrollView(this);
//
//        LinearLayout linearLayout = new LinearLayout(this);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        linearLayout.setLayoutParams(params);
//        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
//
//        for(Anime anime : animeList) {
//            CardView animeCover = new CardView(MainActivity.this);
//            View view = getLayoutInflater().inflate(R.layout.card_small_anime, null);
//
//            ImageView cardCaratula = view.findViewById(R.id.card_caratula);
//            Picasso.get().load(anime.getCaratula()).into(cardCaratula);
//
//            TextView cardNombre = view.findViewById(R.id.card_nombre);
//            cardNombre.setText(anime.getNombre());
//
//            animeCover.addView(view);
//            linearLayout.addView(animeCover, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
//        }
//
//        scrollView.addView(linearLayout);
//        setContentView(scrollView);
//    }


//    public void crearAnimes(ArrayList<Anime> animeList) {
//        HorizontalScrollView scrollView = new HorizontalScrollView(this);
//
//        LinearLayout linearLayout = new LinearLayout(this);
//        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
//
//        for(Anime anime : animeList) {
//            CardView animeCover = new CardView(MainActivity.this);
//            View view = getLayoutInflater().inflate(R.layout.card_small_anime, animeCover);
//
//            ImageView cardCaratula = view.findViewById(R.id.card_caratula);
//            Picasso.get().load(anime.getCaratula()).into(cardCaratula);
//
//            TextView cardNombre = view.findViewById(R.id.card_nombre);
//            cardNombre.setText(anime.getNombre());
//
//            linearLayout.addView(animeCover);
//        }
//
//        scrollView.addView(linearLayout);
//        setContentView(scrollView);
//    }


}