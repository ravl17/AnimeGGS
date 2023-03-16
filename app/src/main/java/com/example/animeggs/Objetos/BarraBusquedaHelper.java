package com.example.animeggs.Objetos;

import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;

import com.example.animeggs.R;

public class BarraBusquedaHelper {
    public static void setupSearchBar(Activity activity) {
        ViewGroup rootView = activity.findViewById(R.id.layout_scroll_vertical);

//        SearchView searchView = activity.findViewById(R.id.barraBusqueda);
        SearchView searchView = (SearchView) activity.getLayoutInflater().inflate(R.layout.barra_busqueda, null);
        SearchView.LayoutParams params = new SearchView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        searchView.setLayoutParams(params);
        rootView.addView(searchView,0);

        // Set up the query text listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle the search query
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle the search query as the user types
                return true;
            }
        });

        // Add the search bar to the activity's layout

    }
}
