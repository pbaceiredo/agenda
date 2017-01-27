package br.com.baceiredo.agenda.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.baceiredo.agenda.R;
import br.com.baceiredo.agenda.activity.DetalhesProvaActivity;
import br.com.baceiredo.agenda.activity.ProvasActivity;
import br.com.baceiredo.agenda.model.entity.Prova;


/**
 * Created by ur5l on 04/10/2016.
 */

public class ListaProvasFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_provas, container, false);

        Prova provaPortugues = new Prova("Português", "25/05/2016", Arrays.asList("Sujeito", "Objeto direto", "Objeto Indireto"));
        Prova provaMatematica = new Prova("Matemática", "27/05/2016", Arrays.asList("Equações do Segundo grau", "Trigonometria"));

        List<Prova> provas = Arrays.asList(provaPortugues, provaMatematica);

        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(getContext(), android.R.layout.simple_list_item_1, provas);

        ListView listView = (ListView) view.findViewById(R.id.provas_lista);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), "Clicou na prova " + prova, Toast.LENGTH_LONG).show();

                ProvasActivity provasActivity = (ProvasActivity) getActivity();
                provasActivity.selecionaProva(prova);
            }
        });

        return view;
    }
}
