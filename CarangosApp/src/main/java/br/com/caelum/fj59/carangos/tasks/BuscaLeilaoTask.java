package br.com.caelum.fj59.carangos.tasks;

import android.app.Application;
import android.os.Message;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.webservice.WebClient;

/**
 * Created by android5372 on 24/10/15.
 */
public class BuscaLeilaoTask extends TimerTask {


    private Application application;
    private CustomHandler handler;
    private Calendar horarioUltimausca;

    public BuscaLeilaoTask(CustomHandler handler, Calendar horarioUltimausca, Application application) {
        this.handler = handler;
        this.horarioUltimausca = horarioUltimausca;
        this.application = application;
    }

    @Override
    public void run() {
        MyLog.i("Efetuando nova busca!");
       // Toast.makeText(application,"Efetuando nova busca!",Toast.LENGTH_LONG).show();

        WebClient client = new WebClient("leilao/leilaoid54635/"+new SimpleDateFormat("ddMMyyyyHHmmss")
                .format(horarioUltimausca.getTime()), application);

        String json = client.get();

        MyLog.i("Lances recebidos: "+json);

        Message message = handler.obtainMessage();
        message.obj = json;
        handler.sendMessage(message);


        horarioUltimausca = Calendar.getInstance();


    }

    public void executa(){
        Timer timer = new Timer();
        timer.schedule(this,0,30*1000);
    }
}
