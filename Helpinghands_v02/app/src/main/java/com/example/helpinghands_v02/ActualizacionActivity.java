package com.example.helpinghands_v02;

import android.content.Intent;
import android.database.Cursor; // 🔴 IMPORTANTE
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ActualizacionActivity extends AppCompatActivity {

    EditText id, nombre, telefono, direccion, correo, contraseña, edad;
    Button update, delete, show;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizacion);

        // Referencias
        id = findViewById(R.id.id);
        nombre = findViewById(R.id.nombre);
        telefono = findViewById(R.id.telefono);
        direccion = findViewById(R.id.direccion);
        correo = findViewById(R.id.correo);
        contraseña = findViewById(R.id.contraseña);
        edad = findViewById(R.id.edad);

        update = findViewById(R.id.btnmodificar);
        delete = findViewById(R.id.btneliminar);
        show   = findViewById(R.id.btnmostrar);

        DB = new DBHelper(this);

        // ----------- BOTÓN ACTUALIZAR -----------
        update.setOnClickListener(v -> {
            String idTXT = id.getText().toString();
            String nombreTXT = nombre.getText().toString();
            String telefonoTXT = telefono.getText().toString();
            String direccionTXT = direccion.getText().toString();
            String correoTXT = correo.getText().toString();
            String contraseñaTXT = contraseña.getText().toString();
            String edadTXT = edad.getText().toString();

            Boolean checkupdateData = DB.updateuserdata(idTXT, nombreTXT, telefonoTXT, direccionTXT, correoTXT, contraseñaTXT, edadTXT);
            if (checkupdateData) {
                Toast.makeText(ActualizacionActivity.this, "Dato actualizado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ActualizacionActivity.this, "Dato no actualizado", Toast.LENGTH_SHORT).show();
            }
        });

        // ----------- BOTÓN ELIMINAR -----------
        delete.setOnClickListener(v -> {
            String idTXT = id.getText().toString();

            Boolean checkdeletedata = DB.deletedata(idTXT);
            if (checkdeletedata) {
                Toast.makeText(ActualizacionActivity.this, "Dato eliminado", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            } else {
                Toast.makeText(ActualizacionActivity.this, "Dato no eliminado", Toast.LENGTH_SHORT).show();
            }
        });

        // ----------- BOTÓN MOSTRAR -----------
        show.setOnClickListener(v -> {
            Cursor res = DB.getdata();
            if (res.getCount() == 0) {
                Toast.makeText(ActualizacionActivity.this, "No existen registros", Toast.LENGTH_SHORT).show();
                res.close(); // 🔴 Cerrar cursor
                return;
            }

            StringBuilder buffer = new StringBuilder();
            while (res.moveToNext()) {
                buffer.append("ID: ").append(res.getString(res.getColumnIndexOrThrow("id"))).append("\n");
                buffer.append("Nombre: ").append(res.getString(res.getColumnIndexOrThrow("nombre"))).append("\n");
                buffer.append("Teléfono: ").append(res.getString(res.getColumnIndexOrThrow("telefono"))).append("\n");
                buffer.append("Dirección: ").append(res.getString(res.getColumnIndexOrThrow("direccion"))).append("\n");
                buffer.append("Correo: ").append(res.getString(res.getColumnIndexOrThrow("correo"))).append("\n");
                buffer.append("Contraseña: ").append(res.getString(res.getColumnIndexOrThrow("contraseña"))).append("\n");
                buffer.append("Edad: ").append(res.getString(res.getColumnIndexOrThrow("edad"))).append("\n\n");
            }
            res.close(); // 🔴 Muy importante cerrar el cursor

            // Mostrar en un AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(ActualizacionActivity.this);
            builder.setCancelable(true);
            builder.setTitle("Usuarios Registrados");
            builder.setMessage(buffer.toString());
            builder.show();
        });

        // ----------- BOTÓN REGRESAR -----------
        ImageButton btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(v -> {
            Intent intent = new Intent(ActualizacionActivity.this, PerfilActivity.class);
            startActivity(intent);
        });
    }

    // Método para limpiar los campos después de eliminar
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
