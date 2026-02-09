package com.example.trasstarea.Modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tareas")
public class Tarea {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "titulo")
    private String titulo;

    // NO usar defaultValue con String para fechas en Room
    @ColumnInfo(name = "fecha_creacion")
    private String fechaCreacion;

    @ColumnInfo(name = "fecha_objetivo")
    private String fechaObjetivo;

    @ColumnInfo(name = "progreso")
    private int progreso;

    @ColumnInfo(name = "prioritaria")
    private boolean prioritaria;

    @ColumnInfo(name = "descripcion")
    private String descripcion;

    @ColumnInfo(name = "url_doc")
    private String documento;

    @ColumnInfo(name = "url_img")
    private String imagen;

    @ColumnInfo(name = "url_aud")
    private String audio;

    @ColumnInfo(name = "url_vid")
    private String video;

    // Constructor vacío requerido por Room
    public Tarea() {
    }

    // Constructor completo
    public Tarea(String titulo, String fechaCreacion, String fechaObjetivo, int progreso,
                 boolean prioritaria, String descripcion,
                 String documento, String imagen, String audio, String video) {

        this.titulo = titulo;
        this.fechaCreacion = fechaCreacion;
        this.fechaObjetivo = fechaObjetivo;
        this.progreso = progreso;
        this.prioritaria = prioritaria;
        this.descripcion = descripcion;
        this.documento = documento;
        this.imagen = imagen;
        this.audio = audio;
        this.video = video;
    }

    // GETTERS y SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaObjetivo() {
        return fechaObjetivo;
    }

    public void setFechaObjetivo(String fechaObjetivo) {
        this.fechaObjetivo = fechaObjetivo;
    }

    public int getProgreso() {
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }

    public boolean isPrioritaria() {
        return prioritaria;
    }

    public void setPrioritaria(boolean prioritaria) {
        this.prioritaria = prioritaria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    // Útil para debugging
    @Override
    public String toString() {
        return "Tarea{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", fechaObjetivo='" + fechaObjetivo + '\'' +
                ", progreso=" + progreso +
                ", prioritaria=" + prioritaria +
                ", descripcion='" + descripcion + '\'' +
                ", documento='" + documento + '\'' +
                ", imagen='" + imagen + '\'' +
                ", audio='" + audio + '\'' +
                ", video='" + video + '\'' +
                '}';
    }
}
