package br.com.baceiredo.agenda.model.jsonconverter;


import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.baceiredo.agenda.model.entity.Contato;

/**
 * Created by ur5l on 27/09/2016.
 */

public class ContatoConversor {
    public String converterterParaJason(List<Contato> contatos) {

        JSONStringer js = new JSONStringer();

        /**
         * {
         *  "List": [
         *    {
         *     "aluno": [
         *       {
         *        "nome": "Paulo Roberto"
         *        "nota": 4
         *       },
         *       {
         *        "nome": "Marina Azevedo"
         *        "nota": 5
         *       }
         *     ]
         *    }
         *  ]
         * }
         *
         */
        try {
            js.object().key("list").array().object().key("aluno").array();
            for (Contato contato : contatos) {
                js.object();
                js.key("nome").value(contato.getNome());
                js.key("nota").value(contato.getNota());
                js.endObject();
            }
            js.endArray().endObject().endArray().endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return js.toString();
    }
}
