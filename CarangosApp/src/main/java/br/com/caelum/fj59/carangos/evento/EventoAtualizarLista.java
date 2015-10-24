package br.com.caelum.fj59.carangos.evento;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import br.com.caelum.fj59.carangos.delegate.BuscaMaisPublicacoesDelegate;

/**
 * Created by android5372 on 24/10/15.
 */
public class EventoAtualizarLista extends BroadcastReceiver{
    public static final String ATUALIZAR_LISTA = "atualizar lista";
    private BuscaMaisPublicacoesDelegate delegate;

    public EventoAtualizarLista(BuscaMaisPublicacoesDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }

    public static EventoAtualizarLista registraObservador(BuscaMaisPublicacoesDelegate delegate) {
        EventoAtualizarLista receiver = new EventoAtualizarLista(delegate);
        LocalBroadcastManager.getInstance(delegate.getCarangosApplication())
                .registerReceiver(receiver, new IntentFilter(ATUALIZAR_LISTA));
        return receiver;
    }
}
