package br.com.caelum.fj59.carangos.tasks;

import android.content.Context;
import android.os.Message;
import android.widget.Toast;

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


    private Context context;
    private CustomHandler handler;
    private Calendar horarioUltimausca;

    public BuscaLeilaoTask(CustomHandler handler, Calendar horarioUltimausca) {
        this.handler = handler;
        this.horarioUltimausca = horarioUltimausca;
    }

    @Override
    public void run() {
        MyLog.i("Efetuando nova busca!");
       // Toast.makeText(context,"Efetuando nova busca!",Toast.LENGTH_LONG).show();

        WebClient client = new WebClient("leilao/leilaoid54635/"+new SimpleDateFormat("ddMMyyyyHHmmss")
                .format(horarioUltimausca.getTime()));

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
