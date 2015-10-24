package br.com.caelum.fj59.carangos.gcm;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.activity.LeilaoActivity;
import br.com.caelum.fj59.carangos.activity.MainActivity;
import br.com.caelum.fj59.carangos.application.CarangosApplication;
import br.com.caelum.fj59.carangos.infra.MyLog;

/**
 * Created by danilo on 18/10/15.
 */
public class GCMBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        MyLog.i("Chegou a mensagem do GCM!");


        if (!appEstaRodando(context)){

            String message = (String) intent.getExtras().getSerializable("message");
            //Intent irParaLeilao = new Intent(context, MainActivity.class);
            Intent irParaLeilao = new Intent(context, LeilaoActivity.class);
            PendingIntent acaoPendente = PendingIntent
                    .getActivity(context, 0, irParaLeilao, PendingIntent.FLAG_CANCEL_CURRENT);
            irParaLeilao.putExtra("idDaNotificacao", Constantes.ID_NOTIFICACAO);
            Notification notification = new Notification.Builder(context).setContentTitle("Um novo leilão começou")
                    .setContentText("Leitao: " + message)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentIntent(acaoPendente)
                    .build();
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(Constantes.ID_NOTIFICACAO,notification);
        }else {
            Toast.makeText(context,"Um novo leilão começou",Toast.LENGTH_LONG).show();
            //ShowDialog.notificar();

        }


    }

    private boolean appEstaRodando(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(1);
        if (!runningTasks.isEmpty()){
            ComponentName topActivity = runningTasks.get(0).topActivity;
            if(!topActivity.getPackageName().equals(context.getPackageName())){
                return false;
            }
        }
        return true;

    }
}
