package br.com.baceiredo.agenda.activity.asynctask;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import br.com.baceiredo.agenda.model.jsonconverter.ContatoConversor;
import br.com.baceiredo.agenda.model.service.WebClient;

/**
 * Created by ur5l on 29/09/2016.
 */

public class CalculaMediaNotasContatos extends AsyncTask<List, Void, String>{

    private Context context;
    private ProgressDialog progressDialog;

    public CalculaMediaNotasContatos(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
       progressDialog = ProgressDialog.show(context, "Aguarde" ,"Calculando média das notas...", true, true);
    }

    @Override
    protected String doInBackground(List... params) {
        ContatoConversor contatoConversor = new ContatoConversor();
        String json = contatoConversor.converterterParaJason(params[0]);

        WebClient client = new WebClient();
        String resposta = client.post(json);

        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        progressDialog.dismiss();
        Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
    }
}
