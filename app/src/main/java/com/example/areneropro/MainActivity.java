package com.example.areneropro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText usuario;
    TextView contrasenia;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Registrados");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        usuario=(EditText) findViewById(R.id.usuario);
        contrasenia=(EditText) findViewById(R.id.contraseña);

    }

    public void paginaInicio(View view) {
        Intent i = new Intent(this, MainActivity1.class);

        // Sanitizar el valor del usuario reemplazando caracteres no permitidos
        String usuarioValue = sanitizeUsername(usuario.getText().toString());

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String password = dataSnapshot.child(usuarioValue).getValue(String.class);
                    if (password != null && password.equals(contrasenia.getText().toString())) {
                        startActivity(i);
                        Log.e("Firebase", "ACCESO EXITOSO");
                        showToast("ACCESO EXITOSO");
                    } else {
                        Log.e("Firebase", "Contraseña incorrecta");
                        showToast("Contraseña incorrecta");
                    }
                } else {
                    Log.e("Firebase", "No existe el usuario");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error al leer datos", databaseError.toException());
            }
        });
    }
    private String sanitizeUsername(String username) {
        // Realiza alguna sanitización básica, reemplazando caracteres no permitidos
        return username.replace(".", "_").replace("#", "_").replace("$", "_").replace("[", "_").replace("]", "_");
    }
    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}