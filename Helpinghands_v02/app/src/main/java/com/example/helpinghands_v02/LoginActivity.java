package com.example.helpinghands_v02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText txtusuario, txtcontraseña;
    Button btningresar;
    TextView txtregistro;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Referencias
        txtusuario = findViewById(R.id.txtusuario);
        txtcontraseña = findViewById(R.id.txtcontraseña);
        btningresar = findViewById(R.id.btningresar);
        txtregistro = findViewById(R.id.txtregistro);

        DB = new DBHelper(this);

        // Registro
        txtregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Login
        btningresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = txtusuario.getText().toString();
                String pass = txtcontraseña.getText().toString();

                if(correo.equals("") || pass.equals("")){
                    Toast.makeText(LoginActivity.this, "Por favor llene todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkUserPass = DB.checkUser(correo, pass);
                    if(checkUserPass){
                        Toast.makeText(LoginActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), PerfilActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
