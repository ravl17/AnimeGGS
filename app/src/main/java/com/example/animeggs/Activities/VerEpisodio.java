package com.example.animeggs.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.animeggs.Adapters.BarraBusquedaHelper;
import com.example.animeggs.Adapters.NavigationBarHelper;
import com.example.animeggs.Objetos.Anime;
import com.example.animeggs.Objetos.Usuario;
import com.example.animeggs.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class VerEpisodio extends AppCompatActivity {
    private String enlaceEpisodio;
    private String anime;
    private String numeroEpisodio;
    private WebView webView;

    private Button btnPrevEpisodio;
    private Button btnMarcarVisto;
    private Button btnNextEpisodio;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_episodio);

        btnPrevEpisodio = findViewById(R.id.btnPrevEpisodio);
        btnMarcarVisto = findViewById(R.id.btnMarcarVisto);
        btnNextEpisodio = findViewById(R.id.btnNextEpisodio);

        NavigationBarHelper.setupNavigationBar(this);
        Intent intent = getIntent();
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        enlaceEpisodio = intent.getStringExtra("enlace_episodio");
        webView.loadUrl(enlaceEpisodio);
        anime = intent.getStringExtra("anime");
        numeroEpisodio = intent.getStringExtra("numeroEpisodio");

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbUsers = database.getReference("users");
        btnPrevEpisodio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle settings navigation
            }
        });
        btnMarcarVisto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                marcarEpisodioComoVisto();

            }
        });
        btnNextEpisodio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle settings navigation
            }
        });


    }

    public void marcarEpisodioComoVisto() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tableRef = database.getReference("users");
        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String correo = userSnapshot.child("correo").getValue(String.class);
                    if (correo.contentEquals(mAuth.getCurrentUser().getEmail()) && correo != null) {
                        DataSnapshot visualizacionesSnapshot = userSnapshot.child("visualizaciones");
                        for (DataSnapshot serieSnapshot : visualizacionesSnapshot.getChildren()) {
                            Log.d("TAG", "onDataChange: 1" + (serieSnapshot.child("serie").getValue(String.class)));
                            Log.d("TAG", "onDataChange: 2" + anime);

                            if ((serieSnapshot.child("serie").getValue(String.class)).equals(anime)) {
                                String epVistos = serieSnapshot.child("epVistos").getValue(String.class);

                                // Check if the episodeToCheck is already in the list of epVistos
                                List<String> epVistosList = new ArrayList<>();
                                if (epVistos != null && !epVistos.isEmpty()) {
                                    epVistosList = Arrays.asList(epVistos.split(","));
                                }
                                if (epVistosList.size() == 1 && epVistosList.contains(numeroEpisodio)) {
                                    serieSnapshot.child("epVistos").getRef().setValue("");
                                } else {
                                    if (epVistosList.contains(numeroEpisodio)) {
                                        epVistosList.remove(numeroEpisodio);
                                    } else {
                                        epVistosList.add(numeroEpisodio);
                                    }
                                    String updatedEpVistos = TextUtils.join(",", epVistosList);
                                    serieSnapshot.child("epVistos").getRef().setValue(updatedEpVistos);
                                }




                            }

                        }
                    }
                    DataSnapshot epVistosSnapshot = userSnapshot.child("epVistos");
                    String series = userSnapshot.child("serie").getValue(String.class);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });

    }
}
