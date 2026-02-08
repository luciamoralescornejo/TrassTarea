package com.example.trasstarea.Actividades;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.trasstarea.Fragmentos.Fragmento;
import com.example.trasstarea.Fragmentos.Fragmento2;
import com.example.trasstarea.Modelo.Tarea;
import com.example.trasstarea.R;
import com.example.trasstarea.TareaViewModel;
import com.example.trasstarea.Tareas;

import java.util.Objects;

public class CrearActivity extends BaseActivity
        implements Fragmento.FragmentoListener, Fragmento2.Fragmento2Listener{

    // ViewModel para almacenar temporalmente los datos de la tarea
    private TareaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear); // establece el layout de la actividad

        viewModel = new ViewModelProvider(this).get(TareaViewModel.class); // inicializa el ViewModel

        // carga el primer fragmento en el contenedor
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedorFragmento, new Fragmento())
                .commit();
    }

    // método para cargar el segundo fragmento
    private void cargarFragmentoDos() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedorFragmento, new Fragmento2()) // reemplaza el fragmento actual
                .addToBackStack(null) // permite volver al fragmento anterior con el botón atrás
                .commit();
    }

    // se llama cuando se presiona "siguiente" en el primer fragmento
    @Override
    public void onDatosIngresados(String titulo, String fechaCreacion, String fechaObjetivo,
                                  int progreso, boolean prioritaria) {

        // guarda los datos ingresados en el ViewModel
        viewModel.titulo.setValue(titulo);
        viewModel.fechaCreacion.setValue(fechaCreacion);
        viewModel.fechaObjetivo.setValue(fechaObjetivo);
        viewModel.progreso.setValue(progreso);
        viewModel.prioritaria.setValue(prioritaria);

        cargarFragmentoDos(); // muestra el segundo fragmento
    }

    // se llama cuando se guarda la tarea en el segundo fragmento
    @Override
    public void onGuardarDescripcion(String descripcion) {

        viewModel.descripcion.setValue(descripcion); // guarda la descripción en el ViewModel

        // crea un objeto Tarea con los datos guardados en el ViewModel y con requireNonNullElse comprueba si están vacíos
        Tarea tarea = new Tarea(
                Objects.requireNonNullElse(viewModel.titulo.getValue(), ""),
                Objects.requireNonNullElse(viewModel.fechaCreacion.getValue(), ""),
                Objects.requireNonNullElse(viewModel.fechaObjetivo.getValue(), ""),
                Objects.requireNonNullElse(viewModel.progreso.getValue(), 0),
                Objects.requireNonNullElse(viewModel.prioritaria.getValue(), false),
                Objects.requireNonNullElse(viewModel.descripcion.getValue(), "")
        );


        Tareas.listaTareas.add(tarea); // agrega la tarea a la lista general del inicio

        Toast.makeText(this, getString(R.string.guardadaConExito) , Toast.LENGTH_LONG).show(); // mensaje de éxito
        finish(); // cierra la actividad
    }

    // se llama cuando se desea volver al primer fragmento
    @Override
    public void onVolver() {
        getSupportFragmentManager().popBackStack(); // vuelve al fragmento anterior
    }
}