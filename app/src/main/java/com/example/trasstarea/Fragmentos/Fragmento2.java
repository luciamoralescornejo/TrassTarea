package com.example.trasstarea.Fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.trasstarea.R;
import com.google.android.material.snackbar.Snackbar;

public class Fragmento2 extends Fragment {
    private Fragmento2Listener listener; // referencia a la actividad que escucha

    // interfaz que la actividad debe implementar para recibir los datos
    public interface Fragmento2Listener {
        void onGuardarDescripcion(String descripcion); // guardar la descripción de la tarea
        void onVolver(); // volver al primer fragmento
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (Fragmento2Listener) context; // asegura que la actividad implementa la interfaz
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmento2, container, false); // infla layout del fragmento
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        EditText etDescripcion = view.findViewById(R.id.descripcion); // campo para la descripción
        Button btnVolver = view.findViewById(R.id.btnVolver); // boton volver al fragmento anterior
        Button btnGuardar = view.findViewById(R.id.btnGuardar); // boton guardar descripción

        btnGuardar.setOnClickListener(v -> {
            String descripcion = etDescripcion.getText().toString().trim(); // leer texto
            if (descripcion.isEmpty()) { // validar campo vacío
                Snackbar.make(view, getString(R.string.descripcionVacia), Snackbar.LENGTH_SHORT).show();
                return;
            }
            listener.onGuardarDescripcion(descripcion); // enviar datos a la actividad
        });

        btnVolver.setOnClickListener(v -> listener.onVolver()); // volver al fragmento anterior
    }
}