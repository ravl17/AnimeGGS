package com.example.animeggs.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animeggs.Adapters.AnimeScrollAdapter;
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

public class Perfil extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Usuario usuarioActivo;
    private TextView textViewUsuarioCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getUsuarioActivo();

        NavigationBarHelper.setupNavigationBar(this, R.id.page_3);
        mAuth = FirebaseAuth.getInstance();

        Button btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        textViewUsuarioCorreo = findViewById(R.id.textUsuarioCorreo);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(view.getContext(), Login.class);

                view.getContext().startActivity(intent);
                ((Activity) view.getContext()).overridePendingTransition(0, 0);
                ((Activity) view.getContext()).finish();

            }
        });
        ArrayList<Anime> animeList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbAnimes = database.getReference("animes");

        dbAnimes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot animeSnapshot : dataSnapshot.getChildren()) {
                    Anime anime = animeSnapshot.getValue(Anime.class);
                    animeList.add(anime);
                }
                crearListaAnimeViendo(animeList);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });

    }

    public void getUsuarioActivo() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbUsers = database.getReference("users");

        dbUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Usuario usuario = userSnapshot.getValue(Usuario.class);

                    if (usuario.getCorreo().contentEquals(mAuth.getCurrentUser().getEmail())) {
                        usuarioActivo = new Usuario();
                        usuarioActivo.setNick(usuario.getNick());
                        usuarioActivo.setCorreo(usuario.getCorreo());
                        usuarioActivo.setSiguiendo(usuario.getSiguiendo());
                        usuarioActivo.setVisualizaciones(usuario.getVisualizaciones());

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
    }


    public void crearListaAnimeViendo(ArrayList<Anime> animeList) {
        ArrayList<Anime> animeViendo = new ArrayList<>();
        if (usuarioActivo != null) {
            for (Anime anime : animeList) {
                for (Usuario.VisualizacionItem visualizacion : usuarioActivo.getVisualizaciones()) {
                    if (visualizacion.getSerie().contentEquals(anime.getNombre()) && !visualizacion.getEpVistos().isEmpty()) {
                        animeViendo.add(anime);
                    }
                }
            }
            crearCardAnimes("Viendo", animeViendo);
        }

    }

    public void crearCardAnimes(String nombreListaAnimes, ArrayList<Anime> animeList) {

        if (animeList != null && animeList.size() != 0) {
            RecyclerView recyclerView = new RecyclerView(this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            AnimeScrollAdapter adapter = new AnimeScrollAdapter(animeList, this);
            recyclerView.setAdapter(adapter);

            LinearLayout layout = findViewById(R.id.layout_scroll_vertical);
            TextView textView = new TextView(this);
            textView.setTextColor(0xFFFFFFFF);
            textView.setText(nombreListaAnimes);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            textView.setPadding(20, 20, 20, 20);
            layout.addView(textView);
            layout.addView(recyclerView);
        }
        setTextUserCorreo();
    }

    public void setTextUserCorreo() {

        textViewUsuarioCorreo.setTextColor(0xFFFFFFFF);
        textViewUsuarioCorreo.setText(usuarioActivo.getCorreo());
        textViewUsuarioCorreo.setTypeface(null, Typeface.BOLD);
        textViewUsuarioCorreo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
    }
}