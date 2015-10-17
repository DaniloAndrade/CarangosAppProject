package br.com.caelum.fj59.carangos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.Publicacao;

/**
 * Created by erich on 7/16/13.
 */
public class PublicacaoAdapter extends BaseAdapter {
    private Context context;
    private final List<Publicacao> publicacoes;

    public PublicacaoAdapter(Context mContext, List<Publicacao> publicacoes) {
        this.context = mContext;
        this.publicacoes = publicacoes;
    }

    @Override
    public int getCount() {
        return publicacoes.size();
    }

    @Override
    public Object getItem(int i) {
        return publicacoes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        int layout = position % 2 == 0 ? R.layout.publicacao_linha_par :  R.layout.publicacao_linha_impar;

        ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            MyLog.i("Criou uma nova linha");


        }else {
            holder = (ViewHolder) convertView.getTag();
            MyLog.i("Aproveitou a linha");
        }

        Publicacao publicacao = (Publicacao) getItem(position);
        holder.preencher(context,publicacao);
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position%2;
    }

    class ViewHolder {
        ImageView foto;
        ImageView emoticon;
        TextView mensagem;
        TextView nomeAutor;
        ProgressBar progress;

        ViewHolder(View view) {
            this.foto = (ImageView) view.findViewById(R.id.foto);
            this.emoticon = (ImageView) view.findViewById(R.id.emoticon);
            this.mensagem = (TextView) view.findViewById(R.id.mensagem);
            this.nomeAutor = (TextView) view.findViewById(R.id.nome_autor);
            this.progress = (ProgressBar) view.findViewById(R.id.progress);
        }

        public void preencher(Context context, Publicacao publicacao) {
            mensagem.setText(publicacao.getMensagem());
            nomeAutor.setText(publicacao.getAutor().getNome());

            //foto.setImageDrawable(this.context.getResources().getDrawable(R.drawable.ic_car));
            progress.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(publicacao.getFoto())
                    .fit()
                    .placeholder(context.getResources().getDrawable(R.drawable.ic_car))
                    .into(foto,new VerificadorDeRetorno(progress));

            int idImagem = 0;
            switch (publicacao.getEstadoDeHumor()) {
                case ANIMADO: idImagem = R.drawable.ic_muito_feliz; break;
                case INDIFERENTE: idImagem = R.drawable.ic_feliz; break;
                case TRISTE: idImagem = R.drawable.ic_indiferente; break;
            }

            emoticon.setImageDrawable(context.getResources().getDrawable(idImagem));
        }
    }

    private class VerificadorDeRetorno implements Callback {
        private ProgressBar progress;

        public VerificadorDeRetorno(ProgressBar progressBar) {
            this.progress = progressBar;
        }


        @Override
        public void onSuccess() {
           progress.setVisibility(View.GONE);
        }

        @Override
        public void onError() {
            progress.setVisibility(View.GONE);
        }
    }
}
