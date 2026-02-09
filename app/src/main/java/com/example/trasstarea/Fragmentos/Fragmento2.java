package com.example.trasstarea.Fragmentos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.trasstarea.R;
import com.google.android.material.snackbar.Snackbar;

public class Fragmento2 extends Fragment {

    private Fragmento2Listener listener;

    // Códigos para cada tipo de archivo
    private static final int PICK_DOCUMENT = 1;
    private static final int PICK_IMAGE = 2;
    private static final int PICK_AUDIO = 3;
    private static final int PICK_VIDEO = 4;

    public interface Fragmento2Listener {
        void onGuardarDescripcion(String descripcion);
        void onVolver();

        // nuevos métodos para recibir archivos seleccionados
        void onArchivoSeleccionado(Uri uri, int tipo);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (Fragmento2Listener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmento2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        EditText etDescripcion = view.findViewById(R.id.descripcion);
        Button btnVolver = view.findViewById(R.id.btnVolver);
        Button btnGuardar = view.findViewById(R.id.btnGuardar);

        // ImageButtons de archivos
        ImageButton btnDocumento = view.findViewById(R.id.btnDocumento);
        ImageButton btnImagen = view.findViewById(R.id.btnImagen);
        ImageButton btnAudio = view.findViewById(R.id.btnAudio);
        ImageButton btnVideo = view.findViewById(R.id.btnVideo);

        btnGuardar.setOnClickListener(v -> {
            String descripcion = etDescripcion.getText().toString().trim();
            if (descripcion.isEmpty()) {
                Snackbar.make(view, getString(R.string.descripcionVacia), Snackbar.LENGTH_SHORT).show();
                return;
            }
            listener.onGuardarDescripcion(descripcion);
        });

        btnVolver.setOnClickListener(v -> listener.onVolver());

        // Abrir selector de archivos según tipo
        btnDocumento.setOnClickListener(v -> abrirSelector("*/*", PICK_DOCUMENT));
        btnImagen.setOnClickListener(v -> abrirSelector("image/*", PICK_IMAGE));
        btnAudio.setOnClickListener(v -> abrirSelector("audio/*", PICK_AUDIO));
        btnVideo.setOnClickListener(v -> abrirSelector("video/*", PICK_VIDEO));
    }

    // metodo para abrir selector de archivos
    private void abrirSelector(String tipo, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(tipo);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar archivo"), requestCode);
    }

    // Recibir el archivo seleccionado
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                listener.onArchivoSeleccionado(uri, requestCode);
            }
        }
    }
}
