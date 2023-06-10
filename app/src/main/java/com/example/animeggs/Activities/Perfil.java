package com.example.animeggs.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.animeggs.Adapters.BarraBusquedaHelper;
import com.example.animeggs.Adapters.NavigationBarHelper;
import com.example.animeggs.R;

public class Perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        NavigationBarHelper.setupNavigationBar(this,R.id.page_3);



    }

}