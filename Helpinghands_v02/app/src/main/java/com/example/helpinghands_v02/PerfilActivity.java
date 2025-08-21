package com.example.helpinghands_v02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class PerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil);





        Button btnActualizarInfo = findViewById(R.id.btnActualizarInfo);
        btnActualizarInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Código para redirigir a otra pantalla
                Intent intent = new Intent(PerfilActivity.this, ActualizacionActivity.class);
                startActivity(intent);
            }
        });



        Button btnBuscarEmpleo = findViewById(R.id.btnBuscarEmpleo);
        btnBuscarEmpleo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Código para redirigir a otra pantalla
                Intent intent = new Intent(PerfilActivity.this, BuscarEmpleoActivity.class);
                startActivity(intent);
            }
        });






    }
}