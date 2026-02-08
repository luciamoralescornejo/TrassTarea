package com.example.trasstarea;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TareaViewModel extends ViewModel {
    public MutableLiveData<Uri> uriDocumento = new MutableLiveData<>();
    public MutableLiveData<Uri> uriImagen = new MutableLiveData<>();
    public MutableLiveData<Uri> uriAudio = new MutableLiveData<>();
    public MutableLiveData<Uri> uriVideo = new MutableLiveData<>();

    // MutableLiveData se usa para los datos que son cambiantes y automaticamente, una vez se cambian, los cambia tambien en la interfaz
    public MutableLiveData<String> titulo = new MutableLiveData<>();
    public MutableLiveData<String> fechaCreacion = new MutableLiveData<>();
    public MutableLiveData<String> fechaObjetivo = new MutableLiveData<>();
    public MutableLiveData<Integer> progreso = new MutableLiveData<>();
    public MutableLiveData<Boolean> prioritaria = new MutableLiveData<>();
    public MutableLiveData<String> descripcion = new MutableLiveData<>();
}
