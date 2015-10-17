package br.com.caelum.fj59.carangos.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.adapter.PublicacaoAdapter;
import br.com.caelum.fj59.carangos.application.CarangosApplication;
import br.com.caelum.fj59.carangos.delegate.BuscaMaisPublicacoesDelegate;
import br.com.caelum.fj59.carangos.evento.EventoPublicacoesRecebidas;
import br.com.caelum.fj59.carangos.fragments.ListaDePublicacoesFragment;
import br.com.caelum.fj59.carangos.fragments.ProgressFragment;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.Publicacao;
import br.com.caelum.fj59.carangos.navegacao.EstadoMainActivity;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPublicacoesTask;

public class MainActivity extends ActionBarActivity implements BuscaMaisPublicacoesDelegate{
    public static final String ESTADO = "estado";
    private ListView listView;
    //private List<Publicacao> publicacoes;
    private PublicacaoAdapter adapter;
    private EstadoMainActivity estado;
    private EventoPublicacoesRecebidas receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //this.publicacoes = new ArrayList<Publicacao>();
        this.estado = EstadoMainActivity.INICIO;
        //this.estado.executa(this);
        receiver = EventoPublicacoesRecebidas.registraObservador(this);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MyLog.i("Salvando Estado!");
        outState.putSerializable(ESTADO,estado);
    }

    public void buscaPublicacoes() {
        new BuscaMaisPublicacoesTask(getCarangosApplication()).execute();
    }

    //public List<Publicacao> getPublicacoes() {
    //    return this.publicacoes;
    //}

    @Override
    public void lidaComRetorno(List<Publicacao> publicacaos) {
        CarangosApplication carangosApplication = getCarangosApplication();
        List<Publicacao> publicacoes = carangosApplication.getPublicacoes();
        publicacoes.clear();
        publicacoes.addAll(publicacaos);
        this.estado = EstadoMainActivity.PRIMEIRAS_PUBLICACOES_RECEBIDAS;
        this.estado.executa(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        receiver.desregistra(getCarangosApplication());
    }

    @Override
    public void lidaComErro(Exception e) {
        e.printStackTrace();
        Toast.makeText(this, "Erro na busca dos dados", Toast.LENGTH_SHORT).show();
    }

    @Override
    public CarangosApplication getCarangosApplication() {
        return (CarangosApplication) getApplication();
    }

    public void alteraEstadoEExecuta(EstadoMainActivity estadoMainActivity) {
        this.estado = estadoMainActivity;
        this.estado.executa(this);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);


        MyLog.i("RESTAURANDO ESTADO!" );
        estado = (EstadoMainActivity) savedInstanceState.getSerializable(ESTADO);
    }


    @Override
    protected void onResume() {
        super.onResume();

         MyLog.i("EXECUTANDO ESTADO: " + estado);
        estado.executa(this);
    }
}

