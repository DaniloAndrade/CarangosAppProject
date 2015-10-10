package br.com.caelum.fj59.carangos.navegacao;

import android.app.Fragment;
import android.app.FragmentTransaction;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.activity.MainActivity;
import br.com.caelum.fj59.carangos.fragments.ListaDePublicacoesFragment;
import br.com.caelum.fj59.carangos.fragments.ProgressFragment;

/**
 * Created by android5372 on 10/10/15.
 */
public enum EstadoMainActivity {

    INICIO {
        @Override
        public void executa(MainActivity activity) {
            activity.buscaPublicacoes();
            activity.alteraEstadoEExecuta(EstadoMainActivity.AGUARDANDO_PUBLICACOES);
        }
    }, AGUARDANDO_PUBLICACOES {
        @Override
        public void executa(MainActivity activity) {
            ProgressFragment progressFragment = ProgressFragment.comMensagem(R.string.carregando);
            this.colocaFragmentNaTela(activity,progressFragment);
        }
    }, PRIMEIRAS_PUBLICACOES_RECEBIDAS {
        @Override
        public void executa(MainActivity activity) {
            ListaDePublicacoesFragment listaDePublicacoesFragment = new ListaDePublicacoesFragment();
            colocaFragmentNaTela(activity,listaDePublicacoesFragment);
        }
    };


    public abstract void executa(MainActivity activity);

    void colocaFragmentNaTela(MainActivity activity, Fragment fragment){
        FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_principal, fragment);
        fragmentTransaction.commit();
    }
}
