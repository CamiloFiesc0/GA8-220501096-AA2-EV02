package com.example.helpinghands_v02;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BuscarEmpleoActivity extends AppCompatActivity {

    EditText idAsignado, idSolicitud;
    Spinner spinnerEstado, spinnerDistancia;
    Button btnInsertar, btnActualizar, btnEliminar, btnVer, btnVerTodo;
    TextView txtResultados;
    ImageButton btnRegresar;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_empleo);

        // Referencias a vistas
        idAsignado = findViewById(R.id.idasignado);
        idSolicitud = findViewById(R.id.idsolicitud);
        spinnerEstado = findViewById(R.id.spinnerEstado);
        spinnerDistancia = findViewById(R.id.spinnerDistancia);
        btnInsertar = findViewById(R.id.btninsertar);
        btnActualizar = findViewById(R.id.btnactualizar);
        btnEliminar = findViewById(R.id.btneliminar);
        btnVer = findViewById(R.id.btnver);
        btnVerTodo = findViewById(R.id.btnverTodo);
        txtResultados = findViewById(R.id.txtResultados);
        btnRegresar = findViewById(R.id.btnRegresar);

        // Inicializar DBHelper
        dbHelper = new DBHelper(this);

        // Adaptadores para los Spinners
        ArrayAdapter<CharSequence> adapterEstado = ArrayAdapter.createFromResource(
                this, R.array.estados_array, android.R.layout.simple_spinner_item);
        adapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(adapterEstado);

        ArrayAdapter<CharSequence> adapterDistancia = ArrayAdapter.createFromResource(
                this, R.array.distancias_array, android.R.layout.simple_spinner_item);
        adapterDistancia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistancia.setAdapter(adapterDistancia);

        // Botón Insertar
        btnInsertar.setOnClickListener(v -> {
            String idAsig = idAsignado.getText().toString();
            String estado = spinnerEstado.getSelectedItem().toString();
            String distancia = spinnerDistancia.getSelectedItem().toString();
            String idSol = idSolicitud.getText().toString();

            if (idAsig.isEmpty() || idSol.isEmpty()) {
                Toast.makeText(this, "Faltan campos por llenar", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean insertado = dbHelper.insertData(idAsig, estado, distancia, idSol);
            Toast.makeText(this, insertado ? "Registro insertado correctamente" : "Error al insertar", Toast.LENGTH_SHORT).show();
        });

        // Botón Actualizar
        btnActualizar.setOnClickListener(v -> {
            String idAsig = idAsignado.getText().toString();
            String estado = spinnerEstado.getSelectedItem().toString();
            String distancia = spinnerDistancia.getSelectedItem().toString();
            String idSol = idSolicitud.getText().toString();

            if (idAsig.isEmpty() || idSol.isEmpty()) {
                Toast.makeText(this, "Faltan campos por llenar", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean actualizado = dbHelper.updateData(idAsig, estado, distancia, idSol);
            Toast.makeText(this, actualizado ? "Registro actualizado correctamente" : "Error al actualizar", Toast.LENGTH_SHORT).show();
        });

        // Botón Eliminar
        btnEliminar.setOnClickListener(v -> {
            String idAsig = idAsignado.getText().toString();
            boolean eliminado = dbHelper.deleteData(idAsig);
            Toast.makeText(this, eliminado ? "Registro eliminado" : "Error al eliminar", Toast.LENGTH_SHORT).show();
        });

        // 🔹 Botón Ver por ID (corregido con Cursor)
        btnVer.setOnClickListener(v -> {
            String idAsig = idAsignado.getText().toString().trim();
            if (idAsig.isEmpty()) {
                Toast.makeText(this, "Ingresa el ID Asignado", Toast.LENGTH_SHORT).show();
                return;
            }

            Cursor res = dbHelper.getDataById(idAsig);
            if (res != null && res.moveToFirst()) {
                String estado = res.getString(1);
                String distancia = res.getString(2);
                String idSol = res.getString(3);

                setSpinnerSelectionByValue(spinnerEstado, estado);
                setSpinnerSelectionByValue(spinnerDistancia, distancia);
                idSolicitud.setText(idSol);

                txtResultados.setText(
                        "ID Asignado: " + idAsig + "\n" +
                                "Estado: " + estado + "\n" +
                                "Distancia: " + distancia + "\n" +
                                "ID Solicitud: " + idSol
                );
            } else {
                Toast.makeText(this, "No se encontró el registro", Toast.LENGTH_SHORT).show();
                txtResultados.setText("Sin resultados para ID: " + idAsig);
            }
            if (res != null) res.close();
        });

        // Botón Ver Todos
        btnVerTodo.setOnClickListener(v -> {
            String data = dbHelper.getAllData();
            txtResultados.setText(data);
        });

        // Botón Regresar
        btnRegresar.setOnClickListener(v -> finish());
    }

    // 🔹 Método auxiliar para setear valor en Spinner
    private void setSpinnerSelectionByValue(Spinner spinner, String value) {
        if (value == null) return;
        for (int i = 0; i < spinner.getCount(); i++) {
            Object item = spinner.getItemAtPosition(i);
            if (item != null && value.equalsIgnoreCase(item.toString())) {
                spinner.setSelection(i);
                return;
            }
        }
    }
}
