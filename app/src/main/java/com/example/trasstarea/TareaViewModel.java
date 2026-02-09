package com.example.trasstarea;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TareaViewModel extends ViewModel {

    // URIs como String (para que coincida con el constructor de Tarea)
    public MutableLiveData<String> uriDocumento = new MutableLiveData<>();
    public MutableLiveData<String> uriImagen = new MutableLiveData<>();
    public MutableLiveData<String> uriAudio = new MutableLiveData<>();
    public MutableLiveData<String> uriVideo = new MutableLiveData<>();

    // MutableLiveData se usa para los datos que son cambiantes y automaticamente,
    // una vez se cambian, los cambia tambien en la interfaz
    public MutableLiveData<String> titulo = new MutableLiveData<>();
    public MutableLiveData<String> fechaCreacion = new MutableLiveData<>();
    public MutableLiveData<String> fechaObjetivo = new MutableLiveData<>();
    public MutableLiveData<Integer> progreso = new MutableLiveData<>();
    public MutableLiveData<Boolean> prioritaria = new MutableLiveData<>();
    public MutableLiveData<String> descripcion = new MutableLiveData<>();
}
