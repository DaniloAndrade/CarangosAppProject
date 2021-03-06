package br.com.caelum.fj59.carangos.tasks;

import android.os.AsyncTask;

import java.io.Serializable;
import java.util.List;

import br.com.caelum.fj59.carangos.application.CarangosApplication;
import br.com.caelum.fj59.carangos.converter.PublicacaoConverter;
import br.com.caelum.fj59.carangos.evento.EventoPublicacoesRecebidas;
import br.com.caelum.fj59.carangos.modelo.Publicacao;
import br.com.caelum.fj59.carangos.webservice.Pagina;
import br.com.caelum.fj59.carangos.webservice.WebClient;

/**
 * Created by erich on 7/16/13.
 */
public class BuscaMaisPublicacoesTask extends AsyncTask<Pagina, Void, List<Publicacao>> {

    private final CarangosApplication application;
    private Exception erro;
//    private BuscaMaisPublicacoesDelegate delegate;

//    public BuscaMaisPublicacoesTask(BuscaMaisPublicacoesDelegate delegate) {
//        this.delegate = delegate;
//        this.delegate.getCarangosApplication().registra(this);
//    }

    public BuscaMaisPublicacoesTask(CarangosApplication application) {
        this.application = application;
        application.registra(this);
    }

    @Override
    protected List<Publicacao> doInBackground(Pagina... paginas) {
        try {
            Pagina paginaParaBuscar = paginas.length > 1? paginas[0] : new Pagina();
            String jsonDeResposta = new WebClient("post/list?" + paginaParaBuscar, application).get();
            List<Publicacao> publicacoesRecebidas = new PublicacaoConverter().converte(jsonDeResposta);
            return publicacoesRecebidas;
        } catch (Exception e) {
            this.erro = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Publicacao> retorno) {
        if(retorno != null){
            //delegate.lidaComRetorno(retorno);
            EventoPublicacoesRecebidas.notifica(this.application, (Serializable) retorno,true);
        }else {
            //delegate.lidaComErro(erro);
            EventoPublicacoesRecebidas.notifica(this.application, null ,false);
        }
        application.desregistra(this);
    }
}
