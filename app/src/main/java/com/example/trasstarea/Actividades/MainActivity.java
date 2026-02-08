package com.example.trasstarea.Actividades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.trasstarea.R;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button boton1; // boton para empezar
    Button btnEspanol; // boton para cambiar a español
    Button btnIngles; // boton para cambiar a inglés
    private TextView texto; // texto de bienvenida

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cargarIdioma(); // cargar idioma guardado antes de mostrar UI

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // pantalla completa
        setContentView(R.layout.activity_main); // cargar layout principal

        // ajustar padding de la vista principal según barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // enlazar elementos de UI
        texto = findViewById(R.id.texto);
        boton1 = findViewById(R.id.bt_empezar);
        btnEspanol = findViewById(R.id.btnEspanol);
        btnIngles = findViewById(R.id.btnIngles);

        boton1.setOnClickListener(this::irAlListado); // abrir pantalla de lista al pulsar
        btnEspanol.setOnClickListener(v -> establecerIdioma("es")); // cambiar idioma a español
        btnIngles.setOnClickListener(v -> establecerIdioma("en")); // cambiar idioma a inglés
    }

    public void irAlListado(View view) {
        Intent intent = new Intent(this, ListarActivity.class); // crear intent a ListarActivity
        startActivity(intent); // iniciar actividad
    }

    private void establecerIdioma(String codigoIdioma) {
        Locale locale = new Locale(codigoIdioma); // crear Locale con el idioma elegido
        Locale.setDefault(locale);

        Resources recursos = getResources();
        Configuration configuracion = recursos.getConfiguration();
        configuracion.setLocale(locale); // aplicar idioma a la configuración
        recursos.updateConfiguration(configuracion, recursos.getDisplayMetrics());

        // guardar idioma en SharedPreferences
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("My_Lang", codigoIdioma);
        editor.apply();

        recreate(); // reiniciar actividad para aplicar cambios
    }

    private void cargarIdioma() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String idiomaGuardado = prefs.getString("My_Lang", ""); // obtener idioma guardado

        if (idiomaGuardado.isEmpty()) {
            idiomaGuardado = Locale.getDefault().getLanguage(); // usar idioma del sistema si no hay guardado
        }

        Locale locale = new Locale(idiomaGuardado);
        Locale.setDefault(locale);

        Resources recursos = getResources();
        Configuration configuracion = recursos.getConfiguration();
        configuracion.setLocale(locale); // aplicar idioma
        recursos.updateConfiguration(configuracion, recursos.getDisplayMetrics());
    }
}