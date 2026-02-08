package com.example.trasstarea.Actividades;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trasstarea.Modelo.Tarea;
import com.example.trasstarea.R;
import com.example.trasstarea.Tareas;

public class EditarActivity extends AppCompatActivity {
    private int index; // posición de la tarea en la lista
    private Tarea tarea; // objeto Tarea a editar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar); // carga el layout de edición

        // obtener la posición de la tarea enviada desde la otra actividad
        index = getIntent().getIntExtra("tareaIndex", -1);
        if (index == -1) { // si no se envió posición válida
            finish(); // cierra la actividad
            return;
        }

        tarea = Tareas.listaTareas.get(index); // obtiene la tarea a editar

        // enlaza los elementos de la pantalla con variables
        EditText etTitulo = findViewById(R.id.etTituloEditar);
        EditText etFechaCreacion = findViewById(R.id.etFechaCreacionEditar);
        EditText etFechaObjetivo = findViewById(R.id.etFechaObjetivoEditar);
        Spinner spProgreso = findViewById(R.id.spProgresoEditar);
        CheckBox cbPrioritaria = findViewById(R.id.cbPrioritariaEditar);
        EditText etDescripcion = findViewById(R.id.etDescripcionEditar);
        Button btnGuardar = findViewById(R.id.btnSiguiente);

        // mostrar los datos actuales de la tarea en los campos
        etTitulo.setText(tarea.getTitulo());
        etFechaCreacion.setText(tarea.getFechaCreacion());
        etFechaObjetivo.setText(tarea.getFechaObjetivo());
        etDescripcion.setText(tarea.getDescripcion());
        cbPrioritaria.setChecked(tarea.isPrioritaria());

        // configurar spinner con opciones de progreso
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinnerOpciones, // opciones definidas en strings.xml
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProgreso.setAdapter(adapter);

        // seleccionar el progreso actual en el spinner
        int[] valoresProgreso = {0, 25, 50, 75, 100};
        for (int i = 0; i < valoresProgreso.length; i++) {
            if (valoresProgreso[i] == tarea.getProgreso()) {
                spProgreso.setSelection(i); // selecciona el valor actual
                break;
            }
        }

        // al presionar guardar
        btnGuardar.setOnClickListener(v -> {
            tarea.setTitulo(etTitulo.getText().toString()); // actualizar título
            tarea.setFechaObjetivo(etFechaObjetivo.getText().toString()); // actualizar fecha objetivo
            tarea.setDescripcion(etDescripcion.getText().toString()); // actualizar descripción
            tarea.setPrioritaria(cbPrioritaria.isChecked()); // actualizar prioridad
            tarea.setProgreso(valoresProgreso[spProgreso.getSelectedItemPosition()]); // actualizar progreso

            Toast.makeText(this, getString(R.string.actualizada), Toast.LENGTH_SHORT).show(); // mostrar mensaje
            finish(); // cerrar actividad y volver
        });
    }
}