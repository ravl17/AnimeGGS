package com.example.animeggs.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.example.animeggs.Objetos.Episodio;
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
    private Anime anime;
    private ArrayList<Episodio> episodios;
    private ArrayList<String> epVistosList;
    private ArrayList<String> epVistosListIntent;

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

        Intent intent = getIntent();
        enlaceEpisodio = intent.getStringExtra("enlaceEpisodio");
        numeroEpisodio = intent.getStringExtra("numeroEpisodio");
        anime = intent.getParcelableExtra("anime");
        episodios = intent.getParcelableArrayListExtra("episodios");
        epVistosListIntent= intent.getStringArrayListExtra("episodiosVistos");
        epVistosList=epVistosListIntent;
        setTitle("Episodio " + (Integer.parseInt(numeroEpisodio) + 1));

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(enlaceEpisodio);

        if (epVistosListIntent.contains(numeroEpisodio)) {
            cambiarBotonVisto(true);
        } else {
            cambiarBotonVisto(false);
        }

        mAuth = FirebaseAuth.getInstance();
        btnPrevEpisodio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(numeroEpisodio) != 0) {
                    Intent intent = new Intent(v.getContext(), VerEpisodio.class);
                    intent.putExtra("enlaceEpisodio", episodios.get(Integer.parseInt(numeroEpisodio) - 1).getEp());
                    intent.putExtra("numeroEpisodio", "" + (Integer.parseInt(numeroEpisodio) - 1));
                    intent.putExtra("anime", (Parcelable) anime);
                    intent.putStringArrayListExtra("episodiosVistos", epVistosList);
                    intent.putParcelableArrayListExtra("episodios", episodios);

                    v.getContext().startActivity(intent);
                    ((Activity) v.getContext()).overridePendingTransition(0, 0);
                    ((Activity) v.getContext()).finish();
                }


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
                if (Integer.parseInt(numeroEpisodio) < episodios.size() - 1) {
                    Log.d("TAG", "555555555555555: " + Integer.parseInt(numeroEpisodio));
                    Log.d("TAG", "555555555555555: " + episodios.size());
                    Intent intent = new Intent(v.getContext(), VerEpisodio.class);
                    intent.putExtra("enlaceEpisodio", episodios.get(Integer.parseInt(numeroEpisodio) + 1).getEp());
                    intent.putExtra("numeroEpisodio", "" + (Integer.parseInt(numeroEpisodio) + 1));
                    intent.putExtra("anime", (Parcelable) anime);
                    intent.putStringArrayListExtra("episodiosVistos", epVistosList);
                    intent.putParcelableArrayListExtra("episodios", episodios);

                    v.getContext().startActivity(intent);
                    ((Activity) v.getContext()).overridePendingTransition(0, 0);
                    ((Activity) v.getContext()).finish();
                }
            }
        });
        if (Integer.parseInt(numeroEpisodio) == episodios.size() - 1) {
            btnNextEpisodio.setEnabled(false);
            btnNextEpisodio.setText("");
            btnNextEpisodio.setCompoundDrawables(null, null, null, null);
        }
        if (Integer.parseInt(numeroEpisodio) == 0) {
            btnPrevEpisodio.setEnabled(false);
            btnPrevEpisodio.setText("");
            btnPrevEpisodio.setCompoundDrawables(null, null, null, null);
        }

    }

    public void marcarEpisodioComoVisto() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tableRef = database.getReference("users");
        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String correo = userSnapshot.child("correo").getValue(String.class);
                    if (correo.contentEquals(mAuth.getCurrentUser().getEmail())) {
                        DataSnapshot visualizacionesSnapshot = userSnapshot.child("visualizaciones");
                        ArrayList<Usuario.VisualizacionItem> newVisualizacionAnime = new ArrayList<>();

                        for (DataSnapshot serieSnapshot : visualizacionesSnapshot.getChildren()) {

                            Usuario.VisualizacionItem visualizacionItem = new Usuario.VisualizacionItem();
                            visualizacionItem.setSerie(serieSnapshot.child("serie").getValue(String.class));
                            visualizacionItem.setEpVistos(serieSnapshot.child("epVistos").getValue(String.class));
                            newVisualizacionAnime.add(visualizacionItem);
                        }
                        boolean estaEnBD = false;
                        for (Usuario.VisualizacionItem n : newVisualizacionAnime) {
                            if (n.getSerie().equals(anime.getNombre())) {
                                estaEnBD = true;
                                epVistosList = new ArrayList<>();
                                if (n.getEpVistos() != null && !n.getEpVistos().isEmpty()) {
                                    epVistosList = new ArrayList<>(Arrays.asList(n.getEpVistos().split(",")));
                                    cambiarBotonVisto(false);
                                }
                                if (epVistosList.size() == 1 && epVistosList.contains(numeroEpisodio)) {
                                    n.setEpVistos("");
                                    cambiarBotonVisto(false);
                                } else {
                                    if (epVistosList.contains(numeroEpisodio)) {
                                        cambiarBotonVisto(false);
                                        epVistosList.remove(numeroEpisodio);
                                    } else {
                                        cambiarBotonVisto(true);
                                        epVistosList.add(numeroEpisodio);
                                    }
                                    String updatedEpVistos = TextUtils.join(",", epVistosList);
                                    n.setEpVistos(updatedEpVistos);
                                }
                            }
                        }
                        if (!estaEnBD) {
                            cambiarBotonVisto(true);
                            Usuario.VisualizacionItem v = new Usuario.VisualizacionItem();
                            v.setSerie(anime.getNombre());
                            v.setEpVistos(numeroEpisodio);
                            newVisualizacionAnime.add(v);
                        }
                        visualizacionesSnapshot.getRef().setValue(newVisualizacionAnime);
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });


    }
    public void cambiarBotonVisto(boolean estaVisto){
        if (estaVisto) {
            btnMarcarVisto.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.baseline_remove_red_eye_24, 0, 0);
            btnMarcarVisto.setText("Visto");
        } else {
            btnMarcarVisto.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.baseline_visibility_off_24, 0, 0);
            btnMarcarVisto.setText("No visto");
        }
    }
}
