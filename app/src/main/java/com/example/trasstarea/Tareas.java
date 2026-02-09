package com.example.trasstarea;

import com.example.trasstarea.Modelo.Tarea;

import java.util.ArrayList;

public class Tareas {

    public static ArrayList<Tarea> listaTareas = new ArrayList<>();

    static {
        // Datos de ejemplo (id = 0 porque Room lo genera automáticamente)
        listaTareas.add(new Tarea("Pagar servicios", "2025-12-09", "2025-12-13", 0, false,
                "Pagar luz, agua e internet",
                null, null, null, null));

        listaTareas.add(new Tarea("Organizar escritorio", "2025-12-10", "2025-12-12", 40, true,
                "Ordenar papeles y limpiarlo",
                null, null, null, null));

        listaTareas.add(new Tarea("Practicar inglés", "2025-12-08", "2025-12-18", 20, false,
                "Ver series y practicar vocabulario",
                null, null, null, null));

        listaTareas.add(new Tarea("Ir al médico", "2025-12-11", "2025-12-11", 0, true,
                "Pedir receta del medicamento",
                null, null, null, null));

        listaTareas.add(new Tarea("Actualizar CV", "2025-12-07", "2025-12-15", 60, false,
                "Agregar el proyecto final",
                null, null, null, null));

        listaTareas.add(new Tarea("Limpiar la casa", "2025-12-10", "2025-12-13", 30, true,
                "Barrer, fregar suelo y limpiar baño",
                null, null, null, null));

        listaTareas.add(new Tarea("Planificar viaje", "2025-12-09", "2025-12-25", 10, false,
                "Buscar hoteles y actividades",
                null, null, null, null));

        listaTareas.add(new Tarea("Comprar regalo", "2025-12-08", "2025-12-18", 50, false,
                "Buscar regalo para cumpleaños de Sara",
                null, null, null, null));
    }
}
