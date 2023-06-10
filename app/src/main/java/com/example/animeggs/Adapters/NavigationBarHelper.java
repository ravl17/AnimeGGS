package com.example.animeggs.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.animeggs.Activities.AnimeDetailsActivity;
import com.example.animeggs.Activities.BusquedaAnimes;
import com.example.animeggs.Activities.MainActivity;
import com.example.animeggs.R;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationBarHelper {
    public static void setupNavigationBar(Activity activity, int itemSelected) {
        ViewGroup rootView = activity.findViewById(R.id.navigation_bar);

        BottomNavigationView navigationBarLayout = (BottomNavigationView) activity.getLayoutInflater().inflate(R.layout.barra_navegacion, null);
        navigationBarLayout.setSelectedItemId(itemSelected);
        navigationBarLayout.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.page_1:
                        if (activity.getClass() != MainActivity.class) {
                            intent = new Intent(activity, MainActivity.class);
                            activity.startActivity(intent);
                            activity.overridePendingTransition(0, 0);
                            return true;
                        }
                        break;
                    case R.id.page_2:
                        if (activity.getClass() != BusquedaAnimes.class) {
                            intent = new Intent(activity, BusquedaAnimes.class);
                            activity.startActivity(intent);
                            activity.overridePendingTransition(0, 0);
                            return true;
                        }
                        break;
                    case R.id.page_3:
                        break;
                }
                Log.d("TAG", "onClick: " + item.getItemId());
                return false;
            }
        });
        Log.d("TAG", "setupNavigationBar: "+itemSelected);
        Log.d("TAG", "setupNavigationBar: "+R.id.page_1);


        rootView.addView(navigationBarLayout);
    }

}
