package com.example.trasstarea.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.trasstarea.R;

public class MainActivity extends BaseActivity {

    Button boton1;
    private TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); // 1. Se carga el layout primero

        // llamamos al metodo de la clase padre para configurar los botones de idioma
        // que están definidos en activity_main.xml
        configurarBotonesIdioma();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        texto = findViewById(R.id.texto);
        boton1 = findViewById(R.id.bt_empezar);

        if (boton1 != null) {
            boton1.setOnClickListener(this::irAlListado);
        }
}

    public void irAlListado(View view) {
        Intent intent = new Intent(this, ListarActivity.class);
        startActivity(intent);
    }
}