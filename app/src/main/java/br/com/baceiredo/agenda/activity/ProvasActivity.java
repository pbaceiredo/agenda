package br.com.baceiredo.agenda.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import br.com.baceiredo.agenda.R;
import br.com.baceiredo.agenda.activity.fragment.DetalhesProvaFragment;
import br.com.baceiredo.agenda.activity.fragment.ListaProvasFragment;
import br.com.baceiredo.agenda.model.entity.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frame_principal, new ListaProvasFragment());

        if(isLandscapeMode()) {
            fragmentTransaction.replace(R.id.frame_secundario, new DetalhesProvaFragment());
        }

        fragmentTransaction.commit();
    }

    private boolean isLandscapeMode() {
        return getResources().getBoolean(R.bool.modoPaisagem);
    }

    public void selecionaProva(Prova prova) {
        FragmentManager manager = getSupportFragmentManager();
        if(!isLandscapeMode()) {
            FragmentTransaction tx = manager.beginTransaction();

            DetalhesProvaFragment detalhesProvaFragment = new DetalhesProvaFragment();
            Bundle parametros = new Bundle();
            parametros.putSerializable("prova", prova);
            detalhesProvaFragment.setArguments(parametros);

            tx.replace(R.id.frame_principal, detalhesProvaFragment);

            //parametro: id que identifica a transacao
            tx.addToBackStack(null);

            tx.commit();
        } else {
            DetalhesProvaFragment detalhesProvaFragment = (DetalhesProvaFragment) manager.findFragmentById(R.id.frame_secundario);
            detalhesProvaFragment.populaCampos(prova);
        }
    }
}
