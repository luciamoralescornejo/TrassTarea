package com.example.trasstarea.Modelo;

import android.net.Uri;

public class Tarea {

    private String titulo;
    private String fechaCreacion;
    private String fechaObjetivo;
    private int progreso;
    private boolean prioritaria;
    private String descripcion;

    // NUEVOS campos para archivos
    private Uri documento;
    private Uri imagen;
    private Uri audio;
    private Uri video;

    // Constructor original
    public Tarea(String titulo, String fechaCreacion, String fechaObjetivo, int progreso,
                 boolean prioritaria, String descripcion) {
        this.titulo = titulo;
        this.fechaCreacion = fechaCreacion;
        this.fechaObjetivo = fechaObjetivo;
        this.progreso = progreso;
        this.prioritaria = prioritaria;
        this.descripcion = descripcion;
    }

    // Constructor nuevo con archivos
    public Tarea(String titulo, String fechaCreacion, String fechaObjetivo, int progreso,
                 boolean prioritaria, String descripcion,
                 Uri documento, Uri imagen, Uri audio, Uri video) {

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

    // Getters y Setters
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

    public Uri getDocumento() {
        return documento;
    }

    public void setDocumento(Uri documento) {
        this.documento = documento;
    }

    public Uri getImagen() {
        return imagen;
    }

    public void setImagen(Uri imagen) {
        this.imagen = imagen;
    }

    public Uri getAudio() {
        return audio;
    }

    public void setAudio(Uri audio) {
        this.audio = audio;
    }

    public Uri getVideo() {
        return video;
    }

    public void setVideo(Uri video) {
        this.video = video;
    }
}