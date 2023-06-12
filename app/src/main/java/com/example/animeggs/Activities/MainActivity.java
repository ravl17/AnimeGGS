package com.example.animeggs.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.animeggs.Adapters.AnimeScrollAdapter;
import com.example.animeggs.Adapters.NavigationBarHelper;
import com.example.animeggs.Objetos.Anime;
import com.example.animeggs.Adapters.BarraBusquedaHelper;
import com.example.animeggs.Objetos.Usuario;
import com.example.animeggs.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public Usuario usuarioActivo;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        ArrayList<Anime> animeList = new ArrayList<>();

        NavigationBarHelper.setupNavigationBar(this, R.id.page_1);
        getUsuarioActivo();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbAnimes = database.getReference("animes");

        dbAnimes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot animeSnapshot : dataSnapshot.getChildren()) {
                    Anime anime = animeSnapshot.getValue(Anime.class);
                    animeList.add(anime);
                }
                crearListaAnimes(animeList);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = null;
        intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        this.overridePendingTransition(0, 0);

    }


    public void crearListaAnimes(ArrayList<Anime> animeList) {
        crearCardAnimes("Siguiendo", crearListaAnimesSiguiendoUsuario(animeList));
        crearCardAnimes("Sobrenatural", crearListaAnimesPorGenero(animeList, "Sobrenatural"));
        crearCardAnimes("Accion", crearListaAnimesPorGenero(animeList, "Accion"));
        crearCardAnimes("Aventuras", crearListaAnimesPorGenero(animeList, "Aventuras"));

    }

    public void crearCardAnimes(String nombreListaAnimes, ArrayList<Anime> animeList) {

        if (animeList != null && animeList.size() != 0) {
            RecyclerView recyclerView = new RecyclerView(this);
            recyclerView.addItemDecoration(new PaddingItemDecoration(5));
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
    }

    public ArrayList<Anime> crearListaAnimesSiguiendoUsuario(ArrayList<Anime> animeList) {
        if (usuarioActivo.getSiguiendo() != null) {
            ArrayList<Anime> animeSiguiendoUsuario = new ArrayList<>();
            for (Usuario.SiguiendoItem animeSiguiendo : usuarioActivo.getSiguiendo()) {
                for (Anime anime : animeList) {
                    if (animeSiguiendo != null) {
                        if (animeSiguiendo.getSerie().contentEquals(anime.getNombre())) {
                            animeSiguiendoUsuario.add(anime);
                        }
                    }
                }
            }
            return animeSiguiendoUsuario;
        } else {
            return null;
        }

    }

    public ArrayList<Anime> crearListaAnimesPorGenero(ArrayList<Anime> animeList, String genero) {
        ArrayList<Anime> animePorGenero = new ArrayList<>();
        for (Anime anime : animeList) {
            if ((anime.getGeneros().toLowerCase()).contains(genero.toLowerCase(Locale.ROOT))) {
                animePorGenero.add(anime);
            }
        }
        Collections.shuffle(animePorGenero);
        return animePorGenero;
    }

    public void getUsuarioActivo() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbUsers = database.getReference("users");
        dbUsers.addValueEventListener(new ValueEventListener() {
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
    private class PaddingItemDecoration extends RecyclerView.ItemDecoration {
        private final int size;

        public PaddingItemDecoration(int size) {
            this.size = size;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            // Apply offset only to the first item
                outRect.right += size;
                outRect.left += size;
        }
    }
}