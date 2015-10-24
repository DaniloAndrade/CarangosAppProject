package br.com.caelum.fj59.carangos.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.modelo.Lance;
import br.com.caelum.fj59.carangos.tasks.BuscaLeilaoTask;
import br.com.caelum.fj59.carangos.tasks.CustomHandler;

/**
 * Created by android5372 on 24/10/15.
 */
public class LeilaoActivity extends ActionBarActivity {

    private List<Lance> lancesAteMomento = new ArrayList<>();
    private Calendar horarioUltimausca = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leilao);

        ListView lancesList = (ListView) findViewById(R.id.lances_list);

        ArrayAdapter<Lance> adapter = new ArrayAdapter<Lance>(
                LeilaoActivity.this,android.R.layout.simple_list_item_1,
                lancesAteMomento
        );

        lancesList.setAdapter(adapter);

        CustomHandler handler = new CustomHandler(adapter,lancesAteMomento);

        new BuscaLeilaoTask(handler,horarioUltimausca).executa();
    }
}
