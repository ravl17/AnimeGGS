package com.example.animeggs.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.animeggs.R;

public class NavigationBarHelper {
    public static void setupNavigationBar(Activity activity){
        ViewGroup rootView = activity.findViewById(R.id.navigation_bar);

        View navigationBarLayout = activity.getLayoutInflater().inflate(R.layout.barra_navegacion, null);
//        Button boton1 = navigationBarLayout.findViewById(R.id.list_item);
        rootView.addView(navigationBarLayout);
    }
    public void handleItemSelected(int itemId) {
        // Perform actions based on the selected item
        switch (itemId) {
            case R.id.page_1:
                break;
            case R.id.page_2:
                break;
            case R.id.page_3:
                break;
        }
    }

}
