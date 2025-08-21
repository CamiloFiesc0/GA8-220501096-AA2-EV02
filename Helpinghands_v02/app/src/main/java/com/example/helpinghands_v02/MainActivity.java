package com.example.helpinghands_v02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText id, nombre, telefono, direccion, correo, contraseña, edad;
    Button insert;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = findViewById(R.id.id);
        nombre = findViewById(R.id.nombre);
        telefono = findViewById(R.id.telefono);
        direccion = findViewById(R.id.direccion);
        correo = findViewById(R.id.correo);
        contraseña = findViewById(R.id.contraseña);
        edad = findViewById(R.id.edad);

        insert = findViewById(R.id.btninsertar);

        DB = new DBHelper(this);

        // INSERTAR
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idTXT = id.getText().toString().trim();
                String nombreTXT = nombre.getText().toString().trim();
                String telefonoTXT = telefono.getText().toString().trim();
                String direccionTXT = direccion.getText().toString().trim();
                String correoTXT = correo.getText().toString().trim();
                String contraseñaTXT = contraseña.getText().toString().trim();
                String edadTXT = edad.getText().toString().trim();

                if (idTXT.isEmpty() || nombreTXT.isEmpty() || telefonoTXT.isEmpty() ||
                        direccionTXT.isEmpty() || correoTXT.isEmpty() ||
                        contraseñaTXT.isEmpty() || edadTXT.isEmpty()) {
                    Toast.makeText(MainActivity.this,
                            "Por favor complete todos los campos",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Boolean checkinsertData = DB.insertuserdata(idTXT, nombreTXT, telefonoTXT, direccionTXT, correoTXT, contraseñaTXT, edadTXT);
                if (checkinsertData) {
                    Toast.makeText(MainActivity.this,
                            "Usuario registrado correctamente",
                            Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Error: ID ya registrado o no se pudo insertar",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // BOTÓN REGRESAR
        ImageButton btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    // ✅ Método fuera de onCreate
    private void limpiarCampos() {
        id.setText("");
        nombre.setText("");
        telefono.setText("");
        direccion.setText("");
        correo.setText("");
        contraseña.setText("");
        edad.setText("");
    }
}
