package br.com.caelum.fj59.carangos.tasks;

import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import br.com.caelum.fj59.carangos.application.CarangosApplication;
import br.com.caelum.fj59.carangos.gcm.Constantes;
import br.com.caelum.fj59.carangos.gcm.InformacoesDoUsuario;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.webservice.WebClient;

/**
 * Created by android5372 on 17/10/15.
 */
public class RegistraAparelhoTask extends AsyncTask<Void,Void,String> {

    private CarangosApplication application;

    public RegistraAparelhoTask(CarangosApplication application) {
        this.application = application;
    }

    @Override
    protected String doInBackground(Void... params) {
        String registrationId = null;
        try {
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(application);
            registrationId = gcm.register(Constantes.GCM_SERVER_ID);
            MyLog.i("Aparelho registrado com ID: " + registrationId);
            String email = InformacoesDoUsuario.getEmail(application);
            String url = "device/register/"+email+"/"+registrationId;
            WebClient client = new WebClient(url);
            client.post();
        }catch (Exception e){
            MyLog.e("Problema no registro do aparelho no server!" + e.getMessage());
        }

        return registrationId;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        application.lidaComRespostaDoRegistroNoServidor(result);
    }
}
