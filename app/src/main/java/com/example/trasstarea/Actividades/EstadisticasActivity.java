package com.example.trasstarea.Actividades;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.trasstarea.Modelo.AppDatabase;
import com.example.trasstarea.Modelo.Tarea;
import com.example.trasstarea.Modelo.TareaDao;
import com.example.trasstarea.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class EstadisticasActivity extends AppCompatActivity {

    private TextView tvPromedio, tvCompletadas, tvIncompletas, tvPrioritarias, tvFechaPromedio;
    private TareaDao tareaDao;

    // FORMATO AL GUARDAR LA FECHA
    private static final String FORMATO_FECHA = "dd/MM/yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estadisticas);

        // TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbarDescripcion);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // VISTAS
        tvPromedio = findViewById(R.id.tv_promedio);
        tvCompletadas = findViewById(R.id.tv_completadas);
        tvIncompletas = findViewById(R.id.tv_incompletas);
        tvPrioritarias = findViewById(R.id.tv_prioritarias);
        tvFechaPromedio = findViewById(R.id.tv_fecha_promedio);

        // DAO
        tareaDao = AppDatabase.getInstance(this).tareaDao();

        cargarEstadisticas();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void cargarEstadisticas() {
        Executors.newSingleThreadExecutor().execute(() -> {

            // ESTADÍSTICAS NUMÉRICAS
            Double promedio = tareaDao.getPromedioProgreso();
            int completadas = tareaDao.getCompletadas();
            int incompletas = tareaDao.getIncompletas();
            int prioritarias = tareaDao.getPrioritarias();

            // FECHA PROMEDIO CALCULADA EN JAVA Y NO COMO CONSULTA PORQUE ME FALLABA
            List<Tarea> tareas = tareaDao.obtenerTodas();

            long sumaFechas = 0;
            int contador = 0;

            SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA, Locale.getDefault());

            for (Tarea t : tareas) {
                try {
                    String fechaStr = t.getFechaObjetivo();
                    if (fechaStr != null && !fechaStr.isEmpty()) {
                        Date fecha = sdf.parse(fechaStr);
                        if (fecha != null) {
                            sumaFechas += fecha.getTime();
                            contador++;
                        }
                    }
                } catch (Exception ignored) {
                }
            }

            Long fechaMediaMillis = (contador > 0) ? (sumaFechas / contador) : null;

            runOnUiThread(() -> {

                tvPromedio.setText("Promedio de progreso: " +
                        (promedio == null ? "0%" : promedio.intValue() + "%"));

                tvCompletadas.setText("Tareas completadas: " + completadas);
                tvIncompletas.setText("Tareas incompletas: " + incompletas);
                tvPrioritarias.setText("Tareas prioritarias: " + prioritarias);

                if (fechaMediaMillis != null) {
                    Date fechaMedia = new Date(fechaMediaMillis);
                    tvFechaPromedio.setText(
                            "Fecha objetivo promedio: " + sdf.format(fechaMedia)
                    );
                } else {
                    tvFechaPromedio.setText("Fecha objetivo promedio: -");
                }
            });
        });
    }
}
