package com.example.animeggs.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.animeggs.Adapters.NavigationBarHelper;
import com.example.animeggs.R;
import com.google.firebase.auth.FirebaseAuth;

public class Perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        NavigationBarHelper.setupNavigationBar(this, R.id.page_3);

        Button btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
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

    }

}