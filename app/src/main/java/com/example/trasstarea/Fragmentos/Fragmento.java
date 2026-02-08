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

    private FragmentoListener listener;

    public interface FragmentoListener {
        void onDatosIngresados(String titulo, String fechaCreacion, String fechaObjetivo,
                               int progreso, boolean prioritaria);

        void onVolver();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (FragmentoListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " debe implementar FragmentoListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmento, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        EditText etTitulo = view.findViewById(R.id.etTituloEditar);
        EditText etFechaCreacion = view.findViewById(R.id.etFechaCreacionEditar);
        EditText etFechaObjetivo = view.findViewById(R.id.etFechaObjetivoEditar);

        etFechaCreacion.setOnClickListener(v -> mostrarDatePicker(etFechaCreacion));
        etFechaObjetivo.setOnClickListener(v -> mostrarDatePicker(etFechaObjetivo));

        Spinner spProgreso = view.findViewById(R.id.spProgresoEditar);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.spinnerOpciones,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProgreso.setAdapter(adapter);

        CheckBox cbPrioritaria = view.findViewById(R.id.cbPrioritariaEditar);
        Button btnSiguiente = view.findViewById(R.id.btnSiguiente);

        btnSiguiente.setOnClickListener(v -> {
            String titulo = etTitulo.getText().toString().trim();
            String fechaCreacion = etFechaCreacion.getText().toString().trim();
            String fechaObjetivo = etFechaObjetivo.getText().toString().trim();
            int[] valoresProgreso = {0, 25, 50, 75, 100};
            int progreso = valoresProgreso[spProgreso.getSelectedItemPosition()];
            boolean prioritaria = cbPrioritaria.isChecked();

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

            listener.onDatosIngresados(titulo, fechaCreacion, fechaObjetivo, progreso, prioritaria);
        });

        Button btnVolver = view.findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(v -> {
            if (listener != null) {
                listener.onVolver();
            }
        });
    }

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
