package com.example.trasstarea;

import com.example.trasstarea.Modelo.Tarea;

import java.util.ArrayList;

public class Tareas {
    public static ArrayList<Tarea> listaTareas = new ArrayList<>(); //lista de tareas

    static {
        // Datos de ejemplo
        listaTareas.add(new Tarea("Pagar servicios", "09/12/2025", "13/12/2025", 0, false, "Pagar luz, agua e internet")); //tarea 1
        listaTareas.add(new Tarea("Organizar escritorio", "10/12/2025", "12/12/2025", 40, true, "Ordenar papeles y limpiarlo")); //tarea 2
        listaTareas.add(new Tarea("Practicar inglés", "08/12/2025", "18/12/2025", 20, false, "Ver series y practicar vocabulario")); //tarea 3
        listaTareas.add(new Tarea("Ir al médico", "11/12/2025", "11/12/2025", 0, true, "Pedir receta del medicamento")); //tarea 4
        listaTareas.add(new Tarea("Actualizar CV", "07/12/2025", "15/12/2025", 60, false, "Agregar el proyecto final")); //tarea 5
        listaTareas.add(new Tarea("Limpiar la casa", "10/12/2025", "13/12/2025", 30, true, "Barrer, fregar suelo y limpiar baño")); //tarea 6
        listaTareas.add(new Tarea("Planificar viaje", "09/12/2025", "25/12/2025", 10, false, "Buscar hoteles y actividades")); //tarea 7
        listaTareas.add(new Tarea("Comprar regalo", "08/12/2025", "18/12/2025", 50, false, "Buscar regalo para cumpleaños de Sara")); //tarea 8
    }
}