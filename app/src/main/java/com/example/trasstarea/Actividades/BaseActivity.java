package com.example.trasstarea.Actividades;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;
import android.os.Environment;
import java.io.File;

import com.example.trasstarea.R;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {

    Button btnEspanol;
    Button btnIngles;
    protected boolean tema;
    protected String fuente;
    protected String criterio;
    protected boolean orden;
    protected boolean sd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        cargarIdioma(); // Cargar idioma antes de crear la UI

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        tema = prefs.getBoolean("switch_tema", true);
        fuente = prefs.getString("list_fuente", "2");
        criterio = prefs.getString("list_criterio", "2");
        orden = prefs.getBoolean("switch_orden", true);
        sd = prefs.getBoolean("checkbox_sd", false);

        super.onCreate(savedInstanceState);
    }

    //iniciar botones en el idioma
    protected void configurarBotonesIdioma() {
        btnEspanol = findViewById(R.id.btnEspanol);
        btnIngles = findViewById(R.id.btnIngles);

        // Verificamos que los botones existan en el layout actual para evitar errores
        if (btnEspanol != null) {
            btnEspanol.setOnClickListener(v -> establecerIdioma("es"));
        }
        if (btnIngles != null) {
            btnIngles.setOnClickListener(v -> establecerIdioma("en"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (tema) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        if (!prefs.getString("list_fuente", "2").equals(fuente)) {
            recreate();
        }

        tema = prefs.getBoolean("switch_tema", true);
        fuente = prefs.getString("list_fuente", "2");
        criterio = prefs.getString("list_criterio", "2");
        orden = prefs.getBoolean("switch_orden", true);
        sd = prefs.getBoolean("checkbox_sd", false);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences newPrefs = PreferenceManager.getDefaultSharedPreferences(newBase);
        String fontSize = newPrefs.getString("list_fuente", "2");
        float scale;
        switch (fontSize) {
            case "1": scale = 0.85f; break;
            case "3": scale = 1.25f; break;
            default: scale = 1.0f; break;
        }

        Configuration config = new Configuration(newBase.getResources().getConfiguration());
        config.fontScale = scale;
        Context context = newBase.createConfigurationContext(config);
        super.attachBaseContext(context);
    }

    private void establecerIdioma(String codigoIdioma) {
        Locale locale = new Locale(codigoIdioma);
        Locale.setDefault(locale);

        Resources recursos = getResources();
        Configuration configuracion = recursos.getConfiguration();
        configuracion.setLocale(locale);
        recursos.updateConfiguration(configuracion, recursos.getDisplayMetrics());

        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("My_Lang", codigoIdioma);
        editor.apply();

        recreate();
    }

    private void cargarIdioma() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String idiomaGuardado = prefs.getString("My_Lang", "");

        if (idiomaGuardado.isEmpty()) {
            idiomaGuardado = Locale.getDefault().getLanguage();
        }

        Locale locale = new Locale(idiomaGuardado);
        Locale.setDefault(locale);

        Resources recursos = getResources();
        Configuration configuracion = recursos.getConfiguration();
        configuracion.setLocale(locale);
        recursos.updateConfiguration(configuracion, recursos.getDisplayMetrics());
    }

    protected File guardarArchivo(Uri uri, String nombreArchivo) {

        File baseDir;
        if (sd && Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            baseDir = getExternalFilesDir(null);
        } else {
            baseDir = getFilesDir();
        }

        File carpetaAdjuntos = new File(baseDir, "adjuntos");
        if (!carpetaAdjuntos.exists()) {
            carpetaAdjuntos.mkdirs();
        }

        File destino = new File(carpetaAdjuntos, nombreArchivo);

        try (InputStream in = getContentResolver().openInputStream(uri);
             OutputStream out = new FileOutputStream(destino)) {

            byte[] buffer = new byte[1024];
            int leidos;
            while ((leidos = in.read(buffer)) != -1) {
                out.write(buffer, 0, leidos);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Log.d("ARCHIVOS", "Guardado en: " + destino.getAbsolutePath());
        return destino;
    }
}