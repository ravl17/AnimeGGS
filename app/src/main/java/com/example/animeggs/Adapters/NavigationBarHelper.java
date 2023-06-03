package com.example.animeggs.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.animeggs.Activities.AnimeDetailsActivity;
import com.example.animeggs.Activities.BusquedaAnimes;
import com.example.animeggs.Activities.MainActivity;
import com.example.animeggs.R;

public class NavigationBarHelper {
    public static void setupNavigationBar(Activity activity){
        ViewGroup rootView = activity.findViewById(R.id.navigation_bar);

        View navigationBarLayout = activity.getLayoutInflater().inflate(R.layout.barra_navegacion, null);
        View item1 = navigationBarLayout.findViewById(R.id.bottom_navigation).findViewById(R.id.page_1);
        View item2 = navigationBarLayout.findViewById(R.id.bottom_navigation).findViewById(R.id.page_2);
        View item3 = navigationBarLayout.findViewById(R.id.bottom_navigation).findViewById(R.id.page_3);
        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleItemSelected(v.getId(),activity);
            }
        });
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleItemSelected(v.getId(),activity);
            }
        });
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleItemSelected(v.getId(),activity);
            }
        });
        rootView.addView(navigationBarLayout);
    }
    public static void handleItemSelected(int itemId, Activity context) {
        Intent intent= null;
        switch (itemId) {
            case R.id.page_1:
                intent = new Intent(context, MainActivity.class);
                break;
            case R.id.page_2:
                 intent = new Intent(context, BusquedaAnimes.class);
                break;
            case R.id.page_3:
                break;
        }
        context.startActivity(intent);
    }

}
