package com.example.trasstarea.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trasstarea.Adapter;
import com.example.trasstarea.Modelo.Tarea;
import com.example.trasstarea.R;
import com.example.trasstarea.Tareas;

import java.util.ArrayList;

public class ListarActivity extends AppCompatActivity {

    private RecyclerView recyclerView; // lista visual de tareas
    private Adapter adaptador; // adaptador para mostrar las tareas
    private boolean prioritaria = false; // flag para filtrar tareas prioritarias

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // habilita pantalla completa
        setContentView(R.layout.lista_tareas); // carga el layout de la lista

        Toolbar toolbar = findViewById(R.id.toolbarListado); // barra superior
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.rvTareas); // referencia al RecyclerView

        adaptador = new Adapter(new ArrayList<>(Tareas.listaTareas)); // crea adaptador con datos
        recyclerView.setAdapter(adaptador); // conecta adaptador con RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // layout tipo lista vertical

        // habilita la flecha atrás en la toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // cierra la actividad al presionar flecha atrás
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarLista(); // actualiza la lista cada vez que vuelve la pantalla
    }

    // actualiza los datos del RecyclerView según el filtro
    private void actualizarLista() {
        ArrayList<Tarea> listaAMostrar;
        if (prioritaria) { // filtra solo tareas prioritarias
            listaAMostrar = new ArrayList<>();
            for (Tarea t : Tareas.listaTareas) {
                if (t.isPrioritaria()) listaAMostrar.add(t);
            }
        } else {
            listaAMostrar = new ArrayList<>(Tareas.listaTareas); // todas las tareas
        }

        adaptador.actualizarDatos(listaAMostrar); // actualiza la vista del adaptador
    }

    private void abrirCrearTarea() {
        startActivity(new Intent(this, CrearActivity.class)); // abre actividad para crear tarea
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu); // infla menú desde XML
        menu.setGroupVisible(R.id.it_group_actividades, true); // muestra grupo de actividades
        menu.setGroupVisible(R.id.it_group_add, true); // muestra grupo de añadir
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.it_anadir) { // opción de crear tarea
            abrirCrearTarea();
        } else if (id == R.id.it_prioritarias) { // opción de filtrar tareas prioritarias
            prioritaria = !prioritaria; // cambia filtro
            actualizarLista(); // actualiza lista
            String mensaje = prioritaria ? getString(R.string.prioritarias)
                    : getString(R.string.todas);
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show(); // mensaje al usuario
        } else if (id == R.id.it_acercade) { // opción "acerca de"
            mostrarAcercaDe();
        } else if (id == R.id.it_salir) { // opción salir de la app
            finishAffinity(); // cierra todas las actividades
        }else if (id == R.id.it_preferencias) { // opcion de preferencias
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void mostrarAcercaDe() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name)) // título del diálogo
                .setMessage(getString(R.string.acercaDe)) // mensaje del diálogo
                .setPositiveButton(getString(R.string.aceptar), (dialog, which) -> dialog.dismiss()) // botón aceptar
                .show();
    }
}