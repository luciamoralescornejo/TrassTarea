package com.example.trasstarea.Actividades;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.trasstarea.Modelo.Tarea;
import com.example.trasstarea.R;
import com.example.trasstarea.Tareas;

import java.io.File;

public class DescripcionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descripcion);

        // TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbarDescripcion);
        setSupportActionBar(toolbar);

        // mostrar flecha atrás
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Referencias UI
        TextView txtDescripcion = findViewById(R.id.txtDescripcion);
        TextView txtDocumento = findViewById(R.id.txtDocumento);
        TextView txtImagen = findViewById(R.id.txtImagen);
        TextView txtAudio = findViewById(R.id.txtAudio);
        TextView txtVideo = findViewById(R.id.txtVideo);

        // Obtener tarea
        int posicion = getIntent().getIntExtra("posicion", -1);
        if (posicion == -1) {
            finish();
            return;
        }

        Tarea tarea = Tareas.listaTareas.get(posicion);

        // Mostrar descripción (zona larga con scroll)
        txtDescripcion.setText(tarea.getDescripcion());

        // Documento
        if (tarea.getDocumento() != null) {
            txtDocumento.setText(
                    new File(tarea.getDocumento()).getName()
            );
        } else {
            txtDocumento.setText(getString(R.string.sinArchivo));
        }

        // Imagen
        if (tarea.getImagen() != null) {
            txtImagen.setText(
                    new File(tarea.getImagen()).getName()
            );
        } else {
            txtImagen.setText(getString(R.string.sinArchivo));
        }

        // Audio
        if (tarea.getAudio() != null) {
            txtAudio.setText(
                    new File(tarea.getAudio()).getName()
            );
        } else {
            txtAudio.setText(getString(R.string.sinArchivo));
        }

        // Vídeo
        if (tarea.getVideo() != null) {
            txtVideo.setText(
                    new File(tarea.getVideo()).getName()
            );
        } else {
            txtVideo.setText(getString(R.string.sinArchivo));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}