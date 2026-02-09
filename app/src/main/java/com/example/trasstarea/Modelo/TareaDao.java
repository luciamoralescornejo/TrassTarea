package com.example.trasstarea.Modelo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TareaDao {

    @Insert
    long insertar(Tarea tarea);

    @Update
    void actualizar(Tarea tarea);

    @Delete
    void borrar(Tarea tarea);

    @Query("SELECT * FROM tareas ORDER BY fecha_objetivo ASC")
    List<Tarea> obtenerTodas();

    @Query("SELECT * FROM tareas WHERE id = :id")
    Tarea obtenerPorId(int id);

    @Query("SELECT AVG(progreso) FROM tareas")
    Double getPromedioProgreso();

    @Query("SELECT COUNT(*) FROM tareas WHERE progreso = 100")
    Integer getCompletadas();

    @Query("SELECT COUNT(*) FROM tareas WHERE progreso < 100")
    Integer getIncompletas();

    @Query("SELECT COUNT(*) FROM tareas WHERE prioritaria = 1")
    Integer getPrioritarias();
}
