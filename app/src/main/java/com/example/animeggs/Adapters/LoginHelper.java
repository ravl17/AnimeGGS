package com.example.animeggs.Adapters;

import androidx.annotation.NonNull;

import com.example.animeggs.Objetos.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginHelper {
    public static void almacenarEnRealTime(String correo) {
        Usuario user = new Usuario();
        user.setNick("");
        user.setCorreo(correo);

        ArrayList<Usuario.SiguiendoItem> siguiendoItems =new ArrayList<>();
        Usuario.SiguiendoItem siguiendoItem= new Usuario.SiguiendoItem();
        siguiendoItem.setSerie("");
        siguiendoItems.add(siguiendoItem);
        user.setSiguiendo(siguiendoItems);

        ArrayList<Usuario.VisualizacionItem> visualizacionItems =new ArrayList<>();
        Usuario.VisualizacionItem visualizacionItem= new Usuario.VisualizacionItem();
        visualizacionItem.setEpVistos("");
        visualizacionItem.setSerie("");
        visualizacionItems.add(visualizacionItem);
        user.setVisualizaciones(visualizacionItems);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean noExiste = true;
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Usuario usuario = userSnapshot.getValue(Usuario.class);
                    if (usuario.getCorreo().contentEquals(correo)) {
                        noExiste = false;
                    }
                }
                if (noExiste) {
                    String userId = usersRef.push().getKey();
                    usersRef.child(userId).setValue(user)
                            .addOnSuccessListener(aVoid -> {
                                // User added successfully
                            })
                            .addOnFailureListener(e -> {
                                // Error occurred while adding the user
                            });
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });


    }
}
