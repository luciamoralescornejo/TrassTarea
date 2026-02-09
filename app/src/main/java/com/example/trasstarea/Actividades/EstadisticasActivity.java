package com.example.trasstarea.Actividades;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trasstarea.Modelo.AppDatabase;
import com.example.trasstarea.Modelo.TareaDao;
import com.example.trasstarea.R;

import java.util.concurrent.Executors;

public class EstadisticasActivity extends AppCompatActivity {

    private TextView tvPromedio, tvCompletadas, tvIncompletas, tvPrioritarias;
    private TareaDao tareaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estadisticas);

        tvPromedio = findViewById(R.id.tv_promedio);
        tvCompletadas = findViewById(R.id.tv_completadas);
        tvIncompletas = findViewById(R.id.tv_incompletas);
        tvPrioritarias = findViewById(R.id.tv_prioritarias);

        tareaDao = AppDatabase.getInstance(this).tareaDao();

        cargarEstadisticas();
    }

    private void cargarEstadisticas() {
        Executors.newSingleThreadExecutor().execute(() -> {

            Double promedio = tareaDao.getPromedioProgreso();
            Integer completadas = tareaDao.getCompletadas();
            Integer incompletas = tareaDao.getIncompletas();
            Integer prioritarias = tareaDao.getPrioritarias();

            runOnUiThread(() -> {
                tvPromedio.setText("Promedio de progreso: " +
                        (promedio == null ? "0" : promedio.intValue() + "%"));

                tvCompletadas.setText("Tareas completadas: " +
                        (completadas == null ? "0" : completadas));

                tvIncompletas.setText("Tareas incompletas: " +
                        (incompletas == null ? "0" : incompletas));

                tvPrioritarias.setText("Tareas prioritarias: " +
                        (prioritarias == null ? "0" : prioritarias));
            });
        });
    }
}
