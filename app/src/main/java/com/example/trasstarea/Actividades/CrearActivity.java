package com.example.trasstarea.Actividades;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.trasstarea.Fragmentos.Fragmento;
import com.example.trasstarea.Fragmentos.Fragmento2;
import com.example.trasstarea.Modelo.Tarea;
import com.example.trasstarea.R;
import com.example.trasstarea.TareaViewModel;
import com.example.trasstarea.Tareas;

import java.util.Objects;

public class CrearActivity extends BaseActivity
        implements Fragmento.FragmentoListener, Fragmento2.Fragmento2Listener {

    private TareaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear);

        viewModel = new ViewModelProvider(this).get(TareaViewModel.class);

        // 🔹 PRIMER FRAGMENTO (SIN BACKSTACK)
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contenedorFragmento, new Fragmento())
                    .commit();
        }
    }

    // Carga el segundo fragmento
    private void cargarFragmentoDos() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedorFragmento, new Fragmento2())
                .addToBackStack(null)
                .commit();
    }

    // "Siguiente" en Fragmento
    @Override
    public void onDatosIngresados(String titulo, String fechaCreacion, String fechaObjetivo,
                                  int progreso, boolean prioritaria) {

        viewModel.titulo.setValue(titulo);
        viewModel.fechaCreacion.setValue(fechaCreacion);
        viewModel.fechaObjetivo.setValue(fechaObjetivo);
        viewModel.progreso.setValue(progreso);
        viewModel.prioritaria.setValue(prioritaria);

        cargarFragmentoDos();
    }

    // Guardar en Fragmento2
    @Override
    public void onGuardarDescripcion(String descripcion) {

        viewModel.descripcion.setValue(descripcion);

        Tarea tarea = new Tarea(
                Objects.requireNonNullElse(viewModel.titulo.getValue(), ""),
                Objects.requireNonNullElse(viewModel.fechaCreacion.getValue(), ""),
                Objects.requireNonNullElse(viewModel.fechaObjetivo.getValue(), ""),
                Objects.requireNonNullElse(viewModel.progreso.getValue(), 0),
                Objects.requireNonNullElse(viewModel.prioritaria.getValue(), false),
                Objects.requireNonNullElse(viewModel.descripcion.getValue(), ""),

                // AÑADIMOS URIs (si son null, se pasan como null)
                viewModel.uriDocumento.getValue(),
                viewModel.uriImagen.getValue(),
                viewModel.uriAudio.getValue(),
                viewModel.uriVideo.getValue()
        );

        Tareas.listaTareas.add(tarea);

        Toast.makeText(this, getString(R.string.guardadaConExito), Toast.LENGTH_LONG).show();
        finish();
    }

    // Volver desde Fragmento2 al Fragmento
    @Override
    public void onVolver() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    // 🔹 NUEVO MÉTODO (ARCHIVOS)
    @Override
    public void onArchivoSeleccionado(Uri uri, int tipo) {
        switch (tipo) {
            case 1:
                viewModel.uriDocumento.setValue(uri);
                break;
            case 2:
                viewModel.uriImagen.setValue(uri);
                break;
            case 3:
                viewModel.uriAudio.setValue(uri);
                break;
            case 4:
                viewModel.uriVideo.setValue(uri);
                break;
        }
    }
}
