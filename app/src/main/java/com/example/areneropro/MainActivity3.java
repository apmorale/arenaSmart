package com.example.areneropro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity3 extends AppCompatActivity {
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("test");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        FirebaseApp.initializeApp(this);

    }

    public void reinicioSi(View view) {
        actualizarValorEnFirebaseNumLimpieza("totalLimpiezas",0);
        Intent i = new Intent(this, MainActivity2.class );
        startActivity(i);
        finish();
    }



    public void reinicioNo(View view) {
        Intent i = new Intent(this, MainActivity2.class );
        startActivity(i);
        finish();

    }

    private void actualizarValorEnFirebaseNumLimpieza(String nodoId, int nuevoValor) {
        // Referencia de nodo requerido
        DatabaseReference nodoReferencia = databaseReference.child(nodoId);

        // Asiganción de nuevoValor
        nodoReferencia.setValue(nuevoValor, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@NonNull DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null) {
                    // Mensaje de actualización exitosa
                    Log.d("Firebase", "Valor actualizado correctamente");
                } else {
                    Log.e("Firebase", "Error al actualizar valor", databaseError.toException());
                }
            }
        });
    }
    @Override
    public void finish() {
        super.finish();
    }
}
/*
* Para realizar consultas
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar Firebase
        FirebaseApp.initializeApp(this);

        // Obtener la referencia de la base de datos
        databaseReference = FirebaseDatabase.getInstance().getReference("nombre_de_tu_tabla");

        // Ejemplo de lectura de datos
        databaseReference.child("usuario1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Manipular los datos obtenidos
                if (dataSnapshot.exists()) {
                    String nombre = dataSnapshot.child("nombre").getValue(String.class);
                    String correo = dataSnapshot.child("correo").getValue(String.class);

                    // Hacer algo con los datos obtenidos
                    Log.d("Firebase", "Nombre: " + nombre + ", Correo: " + correo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores de la base de datos
                Log.e("Firebase", "Error al leer datos", databaseError.toException());
            }
        });
    }
}

* */