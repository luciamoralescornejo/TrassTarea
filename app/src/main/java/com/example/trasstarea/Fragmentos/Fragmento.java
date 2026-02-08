package com.example.trasstarea.Fragmentos;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.trasstarea.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class Fragmento extends Fragment {
    private FragmentoListener listener; // referencia a la actividad que escucha

    // interfaz que la actividad debe implementar para recibir los datos
    public interface FragmentoListener {
        void onDatosIngresados(String titulo, String fechaCreacion, String fechaObjetivo,
                               int progreso, boolean prioritaria); // enviar datos al activity
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (FragmentoListener) context; // asegura que la actividad implementa la interfaz
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmento, container, false); // infla layout del fragmento
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // enlazar campos de la UI
        EditText etTitulo = view.findViewById(R.id.etTituloEditar);
        EditText etFechaCreacion = view.findViewById(R.id.etFechaCreacionEditar);
        EditText etFechaObjetivo = view.findViewById(R.id.etFechaObjetivoEditar);

        // mostrar DatePicker al pulsar los EditText de fecha
        etFechaCreacion.setOnClickListener(v -> mostrarDatePicker(etFechaCreacion));
        etFechaObjetivo.setOnClickListener(v -> mostrarDatePicker(etFechaObjetivo));

        Spinner spProgreso = view.findViewById(R.id.spProgresoEditar); // spinner de progreso
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.spinnerOpciones, // opciones del spinner
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProgreso.setAdapter(adapter); // asignar adaptador al spinner

        CheckBox cbPrioritaria = view.findViewById(R.id.cbPrioritariaEditar); // checkbox prioridad
        Button btnSiguiente = view.findViewById(R.id.btnSiguiente); // boton siguiente

        btnSiguiente.setOnClickListener(v -> {
            // obtener valores de los campos
            String titulo = etTitulo.getText().toString().trim();
            String fechaCreacion = etFechaCreacion.getText().toString().trim();
            String fechaObjetivo = etFechaObjetivo.getText().toString().trim();
            int[] valoresProgreso = {0, 25, 50, 75, 100};
            int progreso = valoresProgreso[spProgreso.getSelectedItemPosition()]; // valor seleccionado
            boolean prioritaria = cbPrioritaria.isChecked(); // estado checkbox

            // validar campos obligatorios
            if (titulo.isEmpty()) {
                Snackbar.make(view, getString(R.string.tituloVacio), Snackbar.LENGTH_SHORT).show();
                return;
            }
            if (fechaCreacion.isEmpty()) {
                Snackbar.make(view, getString(R.string.fechaCreacionVacia), Snackbar.LENGTH_SHORT).show();
                return;
            }
            if (fechaObjetivo.isEmpty()) {
                Snackbar.make(view, getString(R.string.fechaObjetivoVacia), Snackbar.LENGTH_SHORT).show();
                return;
            }

            // enviar datos a la actividad
            listener.onDatosIngresados(titulo, fechaCreacion, fechaObjetivo, progreso, prioritaria);
        });
    }

    // método para mostrar selector de fecha
    private void mostrarDatePicker(EditText editText) {
        Calendar c = Calendar.getInstance();
        int anio = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) ->
                editText.setText(String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year))
                , anio, mes, dia).show();
    }
}