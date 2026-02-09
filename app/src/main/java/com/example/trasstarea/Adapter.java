package com.example.trasstarea;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trasstarea.Actividades.DescripcionActivity;
import com.example.trasstarea.Actividades.EditarActivity;
import com.example.trasstarea.Actividades.ListarActivity;
import com.example.trasstarea.Modelo.AppDatabase;
import com.example.trasstarea.Modelo.Tarea;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.TareaViewHolder> {

    private ArrayList<Tarea> datos;
    private Context context;
    private OnTareaChangedListener listener;

    public Adapter(ArrayList<Tarea> datosTarea, Context ctx, OnTareaChangedListener listener) {
        this.datos = datosTarea;
        this.context = ctx;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.elemento, parent, false);
        return new TareaViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        holder.bindTarea(datos.get(position));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public void actualizarDatos(ArrayList<Tarea> nuevasTareas) {
        this.datos.clear();
        this.datos.addAll(nuevasTareas);
        notifyDataSetChanged();
    }

    // INTERFAZ para comunicar cambios a la Activity
    public interface OnTareaChangedListener {
        void onTareaBorrada();
    }

    public class TareaViewHolder extends RecyclerView.ViewHolder {
        private TextView titulo;
        private ProgressBar progress;
        private TextView fecha;
        private TextView cantidad;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.txtTitulo);
            progress = itemView.findViewById(R.id.progress);
            fecha = itemView.findViewById(R.id.txtFecha);
            cantidad = itemView.findViewById(R.id.txtDias);
        }

        public void bindTarea(Tarea tarea) {
            titulo.setText(tarea.getTitulo());
            progress.setProgress(tarea.getProgreso());
            fecha.setText(tarea.getFechaObjetivo());

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate fechaObjetivo = LocalDate.parse(tarea.getFechaObjetivo(), formatter);
                long diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), fechaObjetivo);
                cantidad.setText(String.valueOf(diasRestantes));
            } catch (Exception e) {
                fecha.setText("Fecha inválida");
                cantidad.setText("-");
            }

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, DescripcionActivity.class);
                intent.putExtra("tareaId", tarea.getId());
                context.startActivity(intent);
            });

            itemView.setOnLongClickListener(v -> {
                mostrarMenuContextual(context, tarea);
                return true;
            });
        }

        private void mostrarMenuContextual(Context context, Tarea tarea) {
            CharSequence opciones[] = new CharSequence[] {"Editar", "Borrar"};
            new android.app.AlertDialog.Builder(context)
                    .setTitle(tarea.getTitulo())
                    .setItems(opciones, (dialog, which) -> {
                        if (which == 0) {
                            Intent i = new Intent(context, EditarActivity.class);
                            i.putExtra("tareaId", tarea.getId());
                            context.startActivity(i);
                        } else {
                            new android.app.AlertDialog.Builder(context)
                                    .setTitle("Borrar tarea")
                                    .setMessage("¿Seguro que quieres borrar \"" + tarea.getTitulo() + "\"?")
                                    .setPositiveButton("Borrar", (d, w) -> {
                                        new Thread(() -> {
                                            AppDatabase db = AppDatabase.getInstance(context);
                                            db.tareaDao().borrar(tarea);

                                            // avisar a la Activity
                                            if (listener != null) {
                                                ((ListarActivity) context).runOnUiThread(listener::onTareaBorrada);
                                            }
                                        }).start();
                                    })
                                    .setNegativeButton("Cancelar", null)
                                    .show();
                        }
                    }).show();
        }
    }
}
