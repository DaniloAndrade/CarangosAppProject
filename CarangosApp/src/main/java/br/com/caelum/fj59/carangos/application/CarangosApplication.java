package br.com.caelum.fj59.carangos.application;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android5372 on 10/10/15.
 */
public class CarangosApplication extends Application {

    private List<AsyncTask> tasks = new ArrayList<>();



    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i("Application", "Cancelando todas as Tasks");
        for (AsyncTask task : tasks){
            task.cancel(true);
        }
    }

    public void registra(AsyncTask task){
        Log.i("Application","Registrando Task" );
        tasks.add(task);
    }

    public void desregistra(AsyncTask task){
        Log.i("Application","Desregistrando Task" );
        tasks.remove(task);
    }
}
