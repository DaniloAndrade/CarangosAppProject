package br.com.caelum.fj59.carangos.application;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.Publicacao;
import br.com.caelum.fj59.carangos.tasks.RegistraAparelhoTask;

/**
 * Created by android5372 on 10/10/15.
 */
public class CarangosApplication extends Application {

    public static final String REGISTRADO_NO_GCM = "registradoNoGcm";
    public static final String ID_DO_REGISTRO = "idDoRegistro";
    private List<AsyncTask> tasks = new ArrayList<>();
    private List<Publicacao> publicacoes = new ArrayList<>();
    private SharedPreferences preferences;


    @Override
    public void onCreate() {
        super.onCreate();
        preferences = getSharedPreferences("configs", Activity.MODE_PRIVATE);
        registraNoGcm();
        //SharedPreferences gsm = getSharedPreferences("gsm", MODE_PRIVATE);
      //  gsm.
    }

    private void registraNoGcm() {
        if(!usuarioRegistrado()){
            new RegistraAparelhoTask(this).execute();
        }else {
            MyLog.i("Aparelho já cadastrado! Seu id é: " + preferences.getString(ID_DO_REGISTRO,null));
        }

    }

    private boolean usuarioRegistrado() {
        return preferences.getBoolean(REGISTRADO_NO_GCM,false);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i("Application", "Cancelando todas as Tasks");
        for (AsyncTask task : tasks){
            task.cancel(true);
        }
    }


    public void registra(AsyncTask task){
        Log.i("Application", "Registrando Task");
        tasks.add(task);
    }

    public void desregistra(AsyncTask task){
        Log.i("Application","Desregistrando Task" );
        tasks.remove(task);
    }

    public List<Publicacao> getPublicacoes() {
        return publicacoes;
    }

    public void lidaComRespostaDoRegistroNoServidor(String registro) {
        if (registro != null){
            //TODO implementar
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(REGISTRADO_NO_GCM, true);
            editor.putString(ID_DO_REGISTRO,registro);
            editor.commit();
        }
    }
}
