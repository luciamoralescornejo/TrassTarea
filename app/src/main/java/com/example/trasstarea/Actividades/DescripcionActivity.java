package com.example.trasstarea.Actividades;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.trasstarea.Modelo.AppDatabase;
import com.example.trasstarea.Modelo.Tarea;
import com.example.trasstarea.Modelo.TareaDao;
import com.example.trasstarea.R;

import java.io.File;
import java.util.concurrent.Executors;

public class DescripcionActivity extends BaseActivity {

    private TareaDao tareaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descripcion);

        Toolbar toolbar = findViewById(R.id.toolbarDescripcion);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        TextView txtDescripcion = findViewById(R.id.txtDescripcion);
        TextView txtDocumento = findViewById(R.id.txtDocumento);
        TextView txtImagen = findViewById(R.id.txtImagen);
        TextView txtAudio = findViewById(R.id.txtAudio);
        TextView txtVideo = findViewById(R.id.txtVideo);

        tareaDao = AppDatabase.getInstance(this).tareaDao();

        // ahora recibimos tareaId, no posicion
        int tareaId = getIntent().getIntExtra("tareaId", -1);
        if (tareaId == -1) {
            finish();
            return;
        }

        // cargar la tarea desde Room
        Executors.newSingleThreadExecutor().execute(() -> {
            Tarea tarea = tareaDao.obtenerPorId(tareaId);

            if (tarea == null) {
                runOnUiThread(this::finish);
                return;
            }

            runOnUiThread(() -> {
                txtDescripcion.setText(tarea.getDescripcion());

                if (tarea.getDocumento() != null) {
                    txtDocumento.setText(new File(tarea.getDocumento()).getName());
                } else {
                    txtDocumento.setText(getString(R.string.sinArchivo));
                }

                if (tarea.getImagen() != null) {
                    txtImagen.setText(new File(tarea.getImagen()).getName());
                } else {
                    txtImagen.setText(getString(R.string.sinArchivo));
                }

                if (tarea.getAudio() != null) {
                    txtAudio.setText(new File(tarea.getAudio()).getName());
                } else {
                    txtAudio.setText(getString(R.string.sinArchivo));
                }

                if (tarea.getVideo() != null) {
                    txtVideo.setText(new File(tarea.getVideo()).getName());
                } else {
                    txtVideo.setText(getString(R.string.sinArchivo));
                }
            });
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
