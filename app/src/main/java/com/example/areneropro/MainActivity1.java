package com.example.areneropro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
    }
    //Función de boton "Inicio ¡miau!"
    public void pagBienvenido(View view) {
        Intent i = new Intent(this, MainActivity2.class );
        startActivity(i);
    }

}