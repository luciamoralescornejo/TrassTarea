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

public class ListarActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private Adapter adaptador;
    private boolean prioritaria = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.lista_tareas);

        Toolbar toolbar = findViewById(R.id.toolbarListado);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.rvTareas);
        adaptador = new Adapter(new ArrayList<>(Tareas.listaTareas));
        recyclerView.setAdapter(adaptador);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarLista();
    }

    private void actualizarLista() {
        ArrayList<Tarea> listaAMostrar;
        if (prioritaria) {
            listaAMostrar = new ArrayList<>();
            for (Tarea t : Tareas.listaTareas) {
                if (t.isPrioritaria()) listaAMostrar.add(t);
            }
        } else {
            listaAMostrar = new ArrayList<>(Tareas.listaTareas);
        }
        adaptador.actualizarDatos(listaAMostrar);
    }

    private void abrirCrearTarea() {
        startActivity(new Intent(this, CrearActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.setGroupVisible(R.id.it_group_actividades, true);
        menu.setGroupVisible(R.id.it_group_add, true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.it_anadir) {
            abrirCrearTarea();
        } else if (id == R.id.it_prioritarias) {
            prioritaria = !prioritaria;
            actualizarLista();
            String mensaje = prioritaria ? getString(R.string.prioritarias)
                    : getString(R.string.todas);
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.it_acercade) {
            mostrarAcercaDe();
        } else if (id == R.id.it_estadísticas) {
            // NUEVO: abrir pantalla Estadísticas
            startActivity(new Intent(this, EstadisticasActivity.class));
        } else if (id == R.id.it_salir) {
            finishAffinity();
        } else if (id == R.id.it_preferencias) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void mostrarAcercaDe() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.acercaDe))
                .setPositiveButton(getString(R.string.aceptar), (dialog, which) -> dialog.dismiss())
                .show();
    }
}
