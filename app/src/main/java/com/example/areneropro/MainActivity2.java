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


public class MainActivity2 extends AppCompatActivity {
    TextView txt6;
    TextView txt4;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("test");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Inicializacion de Firebase en app Android Studio
        FirebaseApp.initializeApp(this);

        // Inicialización de Text View
        txt4=findViewById(R.id.textView4);
        txt6=findViewById(R.id.usoArena);

        // Función de numero de limpiezas a mostrar
        numeroLimpiezas();

        // Función de estado del arenero acorde a los ciclos de limpieza que se hayan realizado
        cambioEstado();
    }

    // Funcion para boton "Limpieza Ahora", cambia dato en base de datos para que se realice una limpieza.
    // "False"--Arenero Autonomo y "True"--Limpieza Remota
    public void LimpiezaRemota(View view) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Manipulación de datos en firebase mediante función "actualizarValorEnFirebase(String nodo, String valor)"
                if (dataSnapshot.exists()) {
                    actualizarValorEnFirebase("activacionLimpieza", "True");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error al leer datos", databaseError.toException());
            }
        });
    }

    private void actualizarValorEnFirebase(String nodoId, String nuevoValor) {
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

    //Función cambio de estado en activity2 acorde al numero de ciclos de uso del arenero,
    // dado por la función que devuelve un String, "estadoArena(int limpiezas)".
    public void cambioEstado(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Manipular los datos obtenidos
                if (dataSnapshot.exists()) {
                    String numLimpiezas =dataSnapshot.child("totalLimpiezas").getValue().toString();
                    String estado=estadoArena(Integer.parseInt(numLimpiezas));
                    txt4.setText(estado);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error al leer datos", databaseError.toException());
            }
        });
    }

    // Función que devuelve un String con el estado del arenero acorde a sus numero de limpiezas.
    // Este puede ser: "Excelente", "Buen estado", "Próximo a cambio", "Necesita cambio"
    private String estadoArena(int limpiezas){
        String estado="";
        if(limpiezas<50){
            estado="Excelente";
        } else if ((limpiezas>49) & (limpiezas<100)) {
            estado="Buen estado";
        } else if ((limpiezas>99) & (limpiezas<150)) {
            estado="Próximo a cambio";
        } else if (limpiezas>149) {
            estado="Necesita cambio";
        }
        return estado;
    }

    // Función que consulta a base de datos, el número de limpiezas realizada.
    private void numeroLimpiezas(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String ciclos = dataSnapshot.child("totalLimpiezas").getValue().toString();
                    txt6.setText(ciclos);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error al leer datos", databaseError.toException());
            }
        });
    }

    public void confReinicio(View view) {
        Intent i = new Intent(this, MainActivity3.class );
        startActivity(i);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
    }
}
/*
private void actualizarValorEnFirebase(String nodoId, String nuevoValor) {
        // Obtener la referencia del nodo específico que deseas actualizar
        DatabaseReference nodoReferencia = databaseReference.child(nodoId);

        // Asignar el nuevo valor al nodo
        nodoReferencia.setValue(nuevoValor, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@NonNull DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null) {
                    // La actualización se completó con éxito
                    Log.d("Firebase", "Valor actualizado correctamente");
                } else {
                    // Manejar errores de la base de datos
                    Log.e("Firebase", "Error al actualizar valor", databaseError.toException());
                }
            }
        });
    }

    public void pagHistorial(View view) {
        Intent i = new Intent(this, MainActivity3.class );
        startActivity(i);
    }


    private void actualizarNombreUsuario(Boolean estado) {
        // Crear un mapa para almacenar los datos que se actualizarán
        Map<String, Object> actualizaciones = new HashMap<>();
        actualizaciones.put("bool", estado);

        // Realizar la actualización en la base de datos
        databaseReference.child("test").updateChildren(actualizaciones, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@NonNull DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null) {
                    // La actualización se completó con éxito
                    Log.d("Firebase", "Nombre actualizado correctamente");
                } else {
                    // Manejar errores de la base de datos
                    Log.e("Firebase", "Error al actualizar nombre", databaseError.toException());
                }
            }
        });
    }*/
