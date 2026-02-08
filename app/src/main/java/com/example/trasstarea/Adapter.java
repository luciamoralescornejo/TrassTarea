package com.example.trasstarea;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trasstarea.Actividades.EditarActivity;
import com.example.trasstarea.Modelo.Tarea;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.TareaViewHolder> {
    private ArrayList<Tarea> datos; // lista de tareas

    public Adapter(ArrayList<Tarea> datosTarea) {
        this.datos = datosTarea; // guardar lista inicial
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.elemento, parent, false); // inflar layout de cada item
        return new TareaViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        holder.bindTarea(datos.get(position)); // asignar datos al item
    }

    @Override
    public int getItemCount() {
        return datos.size(); // devolver cantidad de items
    }

    // actualizar lista y refrescar RecyclerView
    public void actualizarDatos(ArrayList<Tarea> nuevasTareas) {
        this.datos.clear();
        this.datos.addAll(nuevasTareas);
        notifyDataSetChanged();
    }

    public class TareaViewHolder extends RecyclerView.ViewHolder {
        private TextView titulo; // título de la tarea
        private ProgressBar progress; // barra de progreso
        private TextView fecha; // fecha objetivo
        private TextView cantidad; // días restantes

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.txtTitulo);
            progress = itemView.findViewById(R.id.progress);
            fecha = itemView.findViewById(R.id.txtFecha);
            cantidad = itemView.findViewById(R.id.txtDias);
        }

        public void bindTarea(Tarea tarea) {
            titulo.setText(tarea.getTitulo()); // mostrar título
            progress.setProgress(tarea.getProgreso()); // mostrar progreso
            fecha.setText(tarea.getFechaObjetivo()); // mostrar fecha objetivo

            // calcular días restantes
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate fechaObjetivo = LocalDate.parse(tarea.getFechaObjetivo(), formatter);
                long diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), fechaObjetivo);
                cantidad.setText(String.valueOf(diasRestantes));
            } catch (Exception e) { // error en formato de fecha
                fecha.setText("Fecha inválida");
                cantidad.setText("-");
            }

            // clic normal: mostrar descripción en alerta
            itemView.setOnClickListener(v -> {
                new AlertDialog.Builder(itemView.getContext())
                        .setTitle(tarea.getTitulo())
                        .setMessage(tarea.getDescripcion())
                        .setPositiveButton("Cerrar", null)
                        .show();
            });

            // clic largo: mostrar menú contextual (Editar / Borrar)
            itemView.setOnLongClickListener(v -> {
                mostrarMenuContextual(itemView.getContext(), tarea);
                return true;
            });
        }

        // menú contextual para editar o borrar tarea
        private void mostrarMenuContextual(Context context, Tarea tarea) {
            CharSequence opciones[] = new CharSequence[] {"Editar", "Borrar"};
            new AlertDialog.Builder(context)
                    .setTitle(tarea.getTitulo())
                    .setItems(opciones, (dialog, which) -> {
                        if (which == 0) { // editar tarea
                            Intent i = new Intent(context, EditarActivity.class);
                            i.putExtra("tareaIndex", getAdapterPosition());
                            context.startActivity(i);
                        } else if (which == 1) { // borrar tarea
                            new AlertDialog.Builder(context)
                                    .setTitle("Borrar tarea")
                                    .setMessage("¿Seguro que quieres borrar \"" + tarea.getTitulo() + "\"?")
                                    .setPositiveButton("Borrar", (d, w) -> {
                                        int pos = getAdapterPosition();
                                        Tareas.listaTareas.remove(pos); // eliminar de la lista
                                        actualizarDatos(new ArrayList<>(Tareas.listaTareas)); // actualizar RecyclerView
                                        Toast.makeText(context, "Tarea borrada", Toast.LENGTH_SHORT).show();
                                    })
                                    .setNegativeButton("Cancelar", null)
                                    .show();
                        }
                    }).show();
        }
    }
}