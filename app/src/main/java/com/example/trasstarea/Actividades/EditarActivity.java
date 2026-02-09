package com.example.trasstarea.Actividades;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.example.trasstarea.Modelo.AppDatabase;
import com.example.trasstarea.Modelo.Tarea;
import com.example.trasstarea.Modelo.TareaDao;
import com.example.trasstarea.R;

import java.io.File;
import java.util.concurrent.Executors;

public class EditarActivity extends BaseActivity {

    private Tarea tarea;
    private TareaDao tareaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar);

        Toolbar toolbar = findViewById(R.id.toolbarEditar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        tareaDao = AppDatabase.getInstance(this).tareaDao();

        // ahora recibimos tareaId
        int tareaId = getIntent().getIntExtra("tareaId", -1);
        if (tareaId == -1) {
            finish();
            return;
        }

        // VIEWS
        EditText etTitulo = findViewById(R.id.etTituloEditar);
        EditText etFechaCreacion = findViewById(R.id.etFechaCreacionEditar);
        EditText etFechaObjetivo = findViewById(R.id.etFechaObjetivoEditar);
        Spinner spProgreso = findViewById(R.id.spProgresoEditar);
        CheckBox cbPrioritaria = findViewById(R.id.cbPrioritariaEditar);
        EditText etDescripcion = findViewById(R.id.etDescripcionEditar);
        Button btnGuardar = findViewById(R.id.btnSiguiente);

        TextView txtDocumento = findViewById(R.id.txtDocumentoEditar);
        TextView txtImagen = findViewById(R.id.txtImagenEditar);
        TextView txtAudio = findViewById(R.id.txtAudioEditar);
        TextView txtVideo = findViewById(R.id.txtVideoEditar);

        ImageButton btnBorrarDocumento = findViewById(R.id.btnBorrarDocumento);
        ImageButton btnBorrarImagen = findViewById(R.id.btnBorrarImagen);
        ImageButton btnBorrarAudio = findViewById(R.id.btnBorrarAudio);
        ImageButton btnBorrarVideo = findViewById(R.id.btnBorrarVideo);

        // spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinnerOpciones,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProgreso.setAdapter(adapter);

        int[] valoresProgreso = {0, 25, 50, 75, 100};

        // CARGAR LA TAREA DESDE ROOM
        Executors.newSingleThreadExecutor().execute(() -> {
            tarea = tareaDao.obtenerPorId(tareaId);

            if (tarea == null) {
                runOnUiThread(this::finish);
                return;
            }

            runOnUiThread(() -> {
                etTitulo.setText(tarea.getTitulo());
                etFechaCreacion.setText(tarea.getFechaCreacion());
                etFechaObjetivo.setText(tarea.getFechaObjetivo());
                etDescripcion.setText(tarea.getDescripcion());
                cbPrioritaria.setChecked(tarea.isPrioritaria());

                for (int i = 0; i < valoresProgreso.length; i++) {
                    if (valoresProgreso[i] == tarea.getProgreso()) {
                        spProgreso.setSelection(i);
                        break;
                    }
                }

                actualizarNombresArchivos(txtDocumento, txtImagen, txtAudio, txtVideo);
            });
        });

        // BOTONES BORRAR
        btnBorrarDocumento.setOnClickListener(v -> borrarArchivoAdjunto(
                tarea.getDocumento(),
                () -> {
                    tarea.setDocumento(null);
                    txtDocumento.setText(getString(R.string.sinArchivo));
                }
        ));

        btnBorrarImagen.setOnClickListener(v -> borrarArchivoAdjunto(
                tarea.getImagen(),
                () -> {
                    tarea.setImagen(null);
                    txtImagen.setText(getString(R.string.sinArchivo));
                }
        ));

        btnBorrarAudio.setOnClickListener(v -> borrarArchivoAdjunto(
                tarea.getAudio(),
                () -> {
                    tarea.setAudio(null);
                    txtAudio.setText(getString(R.string.sinArchivo));
                }
        ));

        btnBorrarVideo.setOnClickListener(v -> borrarArchivoAdjunto(
                tarea.getVideo(),
                () -> {
                    tarea.setVideo(null);
                    txtVideo.setText(getString(R.string.sinArchivo));
                }
        ));

        // GUARDAR
        btnGuardar.setOnClickListener(v -> {
            if (tarea == null) return;

            tarea.setTitulo(etTitulo.getText().toString());
            tarea.setFechaObjetivo(etFechaObjetivo.getText().toString());
            tarea.setDescripcion(etDescripcion.getText().toString());
            tarea.setPrioritaria(cbPrioritaria.isChecked());
            tarea.setProgreso(valoresProgreso[spProgreso.getSelectedItemPosition()]);

            Executors.newSingleThreadExecutor().execute(() -> {
                tareaDao.actualizar(tarea);
            });

            Toast.makeText(this, getString(R.string.actualizada), Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void actualizarNombresArchivos(TextView doc, TextView img, TextView aud, TextView vid) {
        if (tarea.getDocumento() != null)
            doc.setText(new File(Uri.parse(tarea.getDocumento()).getPath()).getName());
        else doc.setText(getString(R.string.sinArchivo));

        if (tarea.getImagen() != null)
            img.setText(new File(Uri.parse(tarea.getImagen()).getPath()).getName());
        else img.setText(getString(R.string.sinArchivo));

        if (tarea.getAudio() != null)
            aud.setText(new File(Uri.parse(tarea.getAudio()).getPath()).getName());
        else aud.setText(getString(R.string.sinArchivo));

        if (tarea.getVideo() != null)
            vid.setText(new File(Uri.parse(tarea.getVideo()).getPath()).getName());
        else vid.setText(getString(R.string.sinArchivo));
    }

    private void borrarArchivoAdjunto(String uriString, Runnable onSuccess) {
        if (uriString == null) {
            Toast.makeText(this, getString(R.string.sinArchivo), Toast.LENGTH_SHORT).show();
            return;
        }

        Uri uri = Uri.parse(uriString);

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.confirmarBorrar))
                .setMessage(getString(R.string.confirmarBorrarMensaje))
                .setPositiveButton("Borrar", (dialog, which) -> {
                    File f = new File(uri.getPath());
                    if (f.exists()) f.delete();
                    onSuccess.run();
                    Toast.makeText(this, getString(R.string.archivoBorrado), Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
