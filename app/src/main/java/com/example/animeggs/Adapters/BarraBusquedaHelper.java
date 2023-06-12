package com.example.animeggs.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animeggs.Adapters.AnimeSearchAdapter;
import com.example.animeggs.Objetos.Anime;
import com.example.animeggs.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BarraBusquedaHelper {
    private static AnimeSearchAdapter animeSearchAdapter;
    private static ArrayList<Anime> animeList;

    public static void setupSearchBar(Activity activity) {


        ViewGroup rootView = activity.findViewById(R.id.layout_scroll_vertical);

//        SearchView searchView = activity.findViewById(R.id.barraBusqueda);
        View searchViewLayout = activity.getLayoutInflater().inflate(R.layout.barra_busqueda, null);
        SearchView searchView = searchViewLayout.findViewById(R.id.barraBusqueda);
        rootView.addView(searchViewLayout, 0);


        getAnimeList();
        RecyclerView recyclerView = activity.findViewById(R.id.recycler_view_anime_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        animeSearchAdapter = new AnimeSearchAdapter(animeList, activity);
        recyclerView.setAdapter(animeSearchAdapter);

        // Set up the query text listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.trim().contentEquals("")) {
                    animeSearchAdapter.filter(newText);
                } else {
                    animeSearchAdapter.filter("zzzzzzzzzzzzzzzzzzzzzzzzzzz");
                }
                return true;
            }
        });
        searchView.setOnQueryTextFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                recyclerView.setVisibility(View.VISIBLE);
            }
        });


    }

    public static void getAnimeList() {
        animeList = new ArrayList<>();
        // Initialize the Firebase database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("animes");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot animeSnapshot : dataSnapshot.getChildren()) {
                    Anime anime = animeSnapshot.getValue(Anime.class);
                    animeList.add(anime);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });

    }
}

