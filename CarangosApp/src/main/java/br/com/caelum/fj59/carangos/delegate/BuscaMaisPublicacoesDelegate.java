package br.com.caelum.fj59.carangos.delegate;

import android.app.Application;

import java.util.List;

import br.com.caelum.fj59.carangos.application.CarangosApplication;
import br.com.caelum.fj59.carangos.modelo.Publicacao;

/**
 * Created by android5372 on 10/10/15.
 */
public interface BuscaMaisPublicacoesDelegate {

    void lidaComRetorno(List<Publicacao> publicacaos);
    void lidaComErro(Exception e);

    CarangosApplication getCarangosApplication();
}
